package com.luv2code.springbootlibrary.dao;

import com.luv2code.springbootlibrary.dao.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByBookId(@RequestParam("book_id") Long bookId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.bookId = :bookId AND r.userEmail = :userEmail")
    Optional<Review> findByBookIdAndUserEmail(long bookId, String userEmail);

    @Modifying
    @Query("DELETE FROM Review WHERE book_id in :bookId")
    void deleteAllByBookId(Long bookId);
}
