package com.example.ecommerce.controller;

import com.example.ecommerce.model.Book;
import com.example.ecommerce.service.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/bookoperations")
@PreAuthorize("hasRole('ADMIN')")
public class BookViewController {
    @Autowired
    private final BookService bookService = null;
    @Autowired
    private final CategoryService categoryService = null;

    @GetMapping("/books")
    public String books() {

        return "books";
    }

    @GetMapping("/{id}")
    public String bookDetail(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        if (book == null) {
            return "redirect:/books"; 
        }
        model.addAttribute("book", book);
        return "books"; 
    }

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        List<com.example.ecommerce.model.Category> categories = categoryService.getAllCategories();

        model.addAttribute("books", books);
        model.addAttribute("categories", categories);

        return "/bookoperations";
    }

    @GetMapping("/bookoperations")
    @PreAuthorize("hasRole('ADMIN')")
    public String bookOperations(HttpSession session, Model model) {
        model.addAttribute("bookoperations", bookService.getAllBooks());

        return "/bookoperations";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/bookoperations";
    }

    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book) {
        bookService.updateBook(book.getId(), book);
        return "redirect:/bookoperations";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
        return "redirect:/bookoperations";
    }

}