package com.example.ecommerce.controller;

import com.example.ecommerce.model.Book;
import com.example.ecommerce.service.BookService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    // Tüm kitapları listele
    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books-list"; 
    }

    // Tek kitap detay sayfası
    // Tek bir kitabın detayı
    @GetMapping("/books/{id}")
    public String bookDetail(@PathVariable Long id, Model model) {
        Optional<Book> optional = Optional.of(bookService.findById(id));
        if (optional.isEmpty()) {
            return "error/404";
        }
        model.addAttribute("book", optional.get());
        return "books";
    }

}