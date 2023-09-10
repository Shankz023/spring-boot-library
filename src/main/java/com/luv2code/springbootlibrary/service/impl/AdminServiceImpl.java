package com.luv2code.springbootlibrary.service.impl;

import com.luv2code.springbootlibrary.dao.BookRepository;
import com.luv2code.springbootlibrary.dao.ReviewRepository;
import com.luv2code.springbootlibrary.dao.entity.Book;
import com.luv2code.springbootlibrary.exceptions.ServiceException;
import com.luv2code.springbootlibrary.requestmodels.AddBookRequest;
import com.luv2code.springbootlibrary.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    public AdminServiceImpl(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public void addBook(AddBookRequest addBookRequest) {
        Book book = Book.builder()
                .img(addBookRequest.getImg())
                .title(addBookRequest.getTitle())
                .author(addBookRequest.getAuthor())
                .category(addBookRequest.getCategory())
                .copies(addBookRequest.getCopies())
                .copiesAvailable(addBookRequest.getCopies())
                .description(addBookRequest.getDescription())
                .build();

        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void increaseBookQuantity(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new ServiceException("Book not found");
        }
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
        book.get().setCopies(book.get().getCopies() + 1);
    }

    @Override
    @Transactional
    public void decreaseBookQuantity(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty() || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0) {
            throw new ServiceException("Book not found or quantity locked");
        }
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        book.get().setCopies(book.get().getCopies() - 1);
    }

    @Transactional
    @Override
    public void deleteBook(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new ServiceException("Book not found");
        }
        log.info("Book Title: "+book.get().getTitle());
        reviewRepository.deleteAllByBookId(bookId);
        bookRepository.delete(book.get());
    }

}
