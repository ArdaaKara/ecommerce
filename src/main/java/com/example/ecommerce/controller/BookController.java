package com.example.ecommerce.controller;

import com.example.ecommerce.model.Book;
import com.example.ecommerce.model.Review;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.ReviewRepository;
import com.example.ecommerce.service.BookService;
import com.example.ecommerce.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    

    @GetMapping("/books/{id}")
    public String bookDetail(@PathVariable Integer id, Model model, Principal principal) {
        Book book = bookService.findById(id);
        if (book == null) {
            return "error/404";
        }

        List<Review> reviews = reviewRepository.findByBookId(id);
        double avgRating = reviewRepository.getAverageRatingByBookId(id);

        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);
        model.addAttribute("avgRating", String.format("%.1f", avgRating));

        if (principal != null) {
            User currentUser = userService.getLoggedInUser(principal);
            boolean alreadyReviewed = reviewRepository.existsByBookIdAndUserId(id, currentUser.getId());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("alreadyReviewed", alreadyReviewed);
        }

        return "books";
    }

    @PostMapping("/books/{bookId}/review")
    public String addReview(@PathVariable Integer bookId,
            @RequestParam Byte rating,
            @RequestParam(required = false) String comment,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (principal == null)
            return "redirect:/login";

        User user = userService.getLoggedInUser(principal);
        Book book = bookService.findById(bookId);

        if (book == null) {
            redirectAttributes.addFlashAttribute("error", "Kitap bulunamadı!");
            return "redirect:/books";
        }

        if (reviewRepository.existsByBookIdAndUserId(bookId, user.getId())) {
            redirectAttributes.addFlashAttribute("error", "Bu kitaba zaten yorum yaptınız!");
        } else {
            Review review = new Review();
            review.setBook(book);
            review.setUser(user);
            review.setRating(rating != null ? rating : 5);
            review.setComment(comment != null ? comment : "");
            reviewRepository.save(review);

            redirectAttributes.addFlashAttribute("success", "Yorumun eklendi, teşekkürler!");
        }

        return "redirect:/books/" + bookId;
    }
}