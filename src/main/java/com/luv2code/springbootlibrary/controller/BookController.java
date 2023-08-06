package com.luv2code.springbootlibrary.controller;

import com.luv2code.springbootlibrary.service.BookService;
import com.luv2code.springbootlibrary.service.impl.BookServiceImpl;
import com.luv2code.springbootlibrary.utils.ExtractJWT;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return toApi(bookService.checkoutBook(userEmail, bookId));
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkedoutByTheUser(userEmail, bookId);
    }

    @GetMapping("/secure/currentloans/count")
    public Integer currentLoansCount(@RequestHeader(value = "Authorization") String token) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.currentLoansCount(userEmail);
    }

    private Book toApi(com.luv2code.springbootlibrary.entity.Book book) {
        return Book.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .copies(book.getCopies())
                .copiesAvailable(book.getCopiesAvailable())
                .category(book.getCategory())
                .img(book.getImg())
                .build();
    }
}

@Data
@Builder(toBuilder = true)
class Book {
    private Long id;
    private String title;
    private String author;
    private String description;
    private Integer copies;
    private Integer copiesAvailable;
    private String category;
    private String img;
}