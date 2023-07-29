package com.luv2code.springbootlibrary.controller;

import com.luv2code.springbootlibrary.entity.Book;
import com.luv2code.springbootlibrary.service.BookService;
import com.luv2code.springbootlibrary.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookServiceImpl bookService){
        this.bookService=bookService;
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestParam Long bookId) throws Exception{
        String userEmail = "shankarshaw023@gmail.com";
        return bookService.checkoutBook(userEmail, bookId);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestParam Long bookId) throws Exception{
        String userEmail = "shankarshaw023@gmail.com";
        return bookService.checkedoutByTheUser(userEmail, bookId);
    }

    @GetMapping("/secure/currentloans/count")
    public Integer currentLoansCount() throws Exception{
        String userEmail = "shankarshaw023@gmail.com";
        return bookService.currentLoansCount(userEmail);
    }
}
