package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecommerce.model.Book;
import com.example.ecommerce.repository.BookRepository;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/api/booksPAGE")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "title") String sortBy) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Book> bookPage = bookRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("books", bookPage.getContent());
        response.put("currentPage", bookPage.getNumber());
        response.put("totalItems", bookPage.getTotalElements());
        response.put("totalPages", bookPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

}
