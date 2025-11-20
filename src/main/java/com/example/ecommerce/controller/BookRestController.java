package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecommerce.model.Book;
import com.example.ecommerce.repository.BookRepository;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.UserRepository;

import java.util.List;

@RestController
public class BookRestController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/api/category")
    public List<com.example.ecommerce.model.Category> getCategorys() {
        return categoryRepository.findAll();
    }

    @GetMapping("/api/users")
    public List<com.example.ecommerce.model.User> getallUsers() {
        return userRepository.findAll();
    }

}
