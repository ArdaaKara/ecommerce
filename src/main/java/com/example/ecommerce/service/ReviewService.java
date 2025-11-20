package com.example.ecommerce.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Book;
import com.example.ecommerce.model.Review;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.ReviewRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookService bookService;
    private final UserService userService;

    // Constructor (Spring otomatik doldurur, sen bir şey yapma)
    public ReviewService(ReviewRepository reviewRepository,
            BookService bookService,
            UserService userService) {
        this.reviewRepository = reviewRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    // Kitaba ait yorumları getir
    public List<Review> getReviewsByBookId(Integer bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    // Ortalama puanı getir
    public double getAverageRating(Integer bookId) {
        return reviewRepository.getAverageRatingByBookId(bookId);
    }

    // Bu kullanıcı bu kitaba yorum yapmış mı?
    public boolean alreadyReviewed(Integer bookId, Long userId) {
        return reviewRepository.existsByBookIdAndUserId(bookId, userId);
    }

    // Yorum ekle (en çok kullandığımız yer)
    public void saveReview(Integer bookId, String username, Byte rating, String comment) {
        User user = userService.getUserByName(username); // senin UserService'inde böyle bir metot olsun
        Book book = bookService.findById(bookId);

        // Zaten yorum yaptıysa ekleme
        if (alreadyReviewed(bookId, user.getId())) {
            return; // sessizce geç (hata vermez)
        }

        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment != null ? comment : "");

        reviewRepository.save(review);
    }

    public List<Review> findByBookId(Integer bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    public double getAverageRatingByBookId(Integer bookId) {
        return reviewRepository.getAverageRatingByBookId(bookId);
    }

    public boolean hasUserReviewedBook(Integer bookId, Long userId) {
        return reviewRepository.existsByBookIdAndUserId(bookId, userId);
    }

    public User getCurrentUser(Principal principal) {
        return userService.getLoggedInUser(principal);
    }

    public List<Review> findByUserId(Integer userId) {
        return reviewRepository.findByUserId(userId);
    }
}