package com.luv2code.springbootlibrary.service;

import com.luv2code.springbootlibrary.entity.Book;

public interface BookService {
    public Book checkoutBook(String userEmail, Long bookId) throws Exception;

    public Boolean checkedoutByTheUser(String userEmail, Long bookId);

    public int currentLoansCount(String userEmail);
}
