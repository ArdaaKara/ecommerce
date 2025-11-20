package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ecommerce.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByBookId(Integer bookId);

    boolean existsByBookIdAndUserId(Integer bookId, Integer userId);

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.book.id = :bookId")
    double getAverageRatingByBookId(Integer bookId);

    List<Review> findByUserId(int userId);

    boolean existsByBookIdAndUserId(Integer bookId, Long userId);

    
}