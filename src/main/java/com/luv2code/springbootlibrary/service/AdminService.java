package com.luv2code.springbootlibrary.service;

import com.luv2code.springbootlibrary.requestmodels.AddBookRequest;

public interface AdminService {
    void addBook(AddBookRequest addBookRequest);

    void increaseBookQuantity(Long bookId) throws Exception;
    void decreaseBookQuantity(Long bookId) throws Exception;
    void deleteBook(Long bookId) throws Exception;
}
