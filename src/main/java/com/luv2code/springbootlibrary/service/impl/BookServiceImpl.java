package com.luv2code.springbootlibrary.service.impl;

import com.luv2code.springbootlibrary.dao.BookRepository;
import com.luv2code.springbootlibrary.dao.CheckoutRepository;
import com.luv2code.springbootlibrary.entity.Book;
import com.luv2code.springbootlibrary.entity.Checkout;
import com.luv2code.springbootlibrary.service.BookService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CheckoutRepository checkoutRepository;

    BookServiceImpl(BookRepository bookRepository, CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }

    @Transactional
    public Book checkoutBook(String userEmail, Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(!book.isPresent() || validateCheckout !=null || book.get().getCopiesAvailable() <=0){
            throw new Exception("Book doesn't exist or already checked out by the user");
        }
        book.get().setCopiesAvailable(book.get().getCopiesAvailable()-1);
        bookRepository.save(book.get());

        Checkout checkedOutBook = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get()
        );

        checkoutRepository.save(checkedOutBook);

        return book.get();
    }

    @Override
    public Boolean checkedoutByTheUser(String userEmail, Long bookId) {
        var isPresent= checkoutRepository.isPresentByUserEmailAndBookId(userEmail,bookId);
        if(isPresent==null){
            return false;
        }
        return isPresent == 1;
    }

    @Override
    public int currentLoansCount(String userEmail) {
        var count = checkoutRepository.countBooksRentedByUserEmail(userEmail);
        return count;
    }

}
