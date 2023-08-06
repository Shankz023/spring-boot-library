package com.luv2code.springbootlibrary.dao;

import com.luv2code.springbootlibrary.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByBookId(@RequestParam("book_id") Long bookId, Pageable pageable);
    Review findByBookId(@RequestParam("book_id") Long bookId);

    @Query("SELECT r FROM Review r WHERE r.bookId = :bookId AND r.userEmail= :userEmail")
    Review findByUserEmailAndBookId(long bookId,String userEmail);
}
