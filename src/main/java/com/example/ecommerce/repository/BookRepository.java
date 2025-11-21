package com.example.ecommerce.repository;

import com.example.ecommerce.model.Book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    Optional<Book> findById(Integer id);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByCategoryId(Integer categoryId);

    List<Book> findByStockGreaterThan(int stock);

    List<Book> findTop8ByOrderByCreatedAtDesc();

    @Query("SELECT b FROM Book b LEFT JOIN b.reviews r GROUP BY b ORDER BY AVG(r.rating) DESC, b.id DESC")
    List<Book> findTopRatedBooks();

    @Query("SELECT b FROM Book b LEFT JOIN b.reviews r GROUP BY b ORDER BY COALESCE(AVG(r.rating), 0) DESC, b.createdAt DESC")
    List<Book> findTop8ByAverageRating();
}