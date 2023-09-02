package com.luv2code.springbootlibrary.service;

import com.luv2code.springbootlibrary.dao.entity.Book;
import com.luv2code.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;

import java.util.List;

public interface BookService {
    public Book checkoutBook(String userEmail, Long bookId) throws Exception;

    public Boolean checkedoutByTheUser(String userEmail, Long bookId);

    public int currentLoansCount(String userEmail);

    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception;

    public void returnBook(String userEmail, Long bookId) throws Exception;

    public void renewLoan(String userEmail, Long bookId) throws  Exception;
}
