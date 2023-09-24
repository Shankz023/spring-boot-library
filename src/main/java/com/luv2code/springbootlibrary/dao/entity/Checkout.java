package com.luv2code.springbootlibrary.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "checkout")
@Data
@NoArgsConstructor
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "checkout_date")
    private String checkoutDate;

    @Column(name = "return_date")
    private String returnDate;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id")
    private Book book;

    public Checkout(String userEmail, String checkoutDate, String returnDate, Book book) {
        this.userEmail = userEmail;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
        this.book = book;
    }
}
