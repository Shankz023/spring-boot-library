package com.luv2code.springbootlibrary.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "History")
@Data
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "checkout_date")
    private String checkOutDate;

    @Column(name = "returned_date")
    private String returnDate;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "img")
    private String img;

    public History( String userEmail, String checkOutDate, String returnDate, String title, String author, String description,String img){
        this.userEmail=userEmail;
        this.checkOutDate=checkOutDate;
        this.returnDate=returnDate;
        this.title=title;
        this.author=author;
        this.description=description;
        this.img=img;
    }

}
