package com.example.ecommerce.service;

import com.example.ecommerce.model.Book;
import com.example.ecommerce.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book findById(Integer bookId) {
        Optional<Book> optionalBook = repository.findById(bookId);
        return optionalBook.orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book getBookById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kitap bulunamadı ID: " + id));
    }

    public Book saveBook(Book book) {
        return repository.save(book);
    }

    public Book updateBook(Long id, Book newBook) {
        Book book = getBookById(id);
        book.setTitle(newBook.getTitle());
        book.setAuthor(newBook.getAuthor());
        book.setPrice(newBook.getPrice());
        book.setStock(newBook.getStock());
        book.setCategoryId(newBook.getCategoryId());
        book.setPicture(newBook.getPicture());
        return repository.save(book);
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }

}
