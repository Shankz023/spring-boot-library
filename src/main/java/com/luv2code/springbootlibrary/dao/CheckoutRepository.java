package com.luv2code.springbootlibrary.dao;

import com.luv2code.springbootlibrary.dao.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

    @Query("SELECT c FROM Checkout c WHERE c.userEmail = :userEmail")
    List<Checkout> findBooksByUserEmail(String userEmail);

    @Query("SELECT 1 FROM Checkout c WHERE c.userEmail = :userEmail AND c.book.id = :bookId")
    Integer isPresentByUserEmailAndBookId(@Param("userEmail") String userEmail,@Param("bookId") Long bookId);

    @Query("SELECT COUNT(c.id) FROM Checkout c WHERE c.userEmail = :userEmail")
    Integer countBooksRentedByUserEmail(@Param("userEmail") String userEmail);
}
