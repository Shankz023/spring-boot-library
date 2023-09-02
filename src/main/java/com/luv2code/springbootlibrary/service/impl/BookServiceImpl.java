package com.luv2code.springbootlibrary.service.impl;

import com.luv2code.springbootlibrary.dao.BookRepository;
import com.luv2code.springbootlibrary.dao.CheckoutRepository;
import com.luv2code.springbootlibrary.dao.HistoryRepository;
import com.luv2code.springbootlibrary.dao.entity.Book;
import com.luv2code.springbootlibrary.dao.entity.Checkout;
import com.luv2code.springbootlibrary.dao.entity.History;
import com.luv2code.springbootlibrary.exceptions.ServiceException;
import com.luv2code.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;
import com.luv2code.springbootlibrary.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class BookServiceImpl implements BookService {

    private static Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;
    private final CheckoutRepository checkoutRepository;

    private final HistoryRepository historyRepository;

    BookServiceImpl(BookRepository bookRepository, CheckoutRepository checkoutRepository, HistoryRepository historyRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
    }

    @Transactional
    public Book checkoutBook(String userEmail, Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out by the user");
        }
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
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
        var isPresent = checkoutRepository.isPresentByUserEmailAndBookId(userEmail, bookId);
        if (isPresent == null) {
            return false;
        }
        return isPresent == 1;
    }

    @Override
    public int currentLoansCount(String userEmail) {
        return checkoutRepository.countBooksRentedByUserEmail(userEmail);
    }

    @Override
    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {

        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

        List<Checkout> checkoutList = checkoutRepository.fndBooksByUserEmail(userEmail);
        List<Long> bookIdList = new ArrayList<>();

        for (Checkout i : checkoutList) {
            bookIdList.add(i.getBook().getId());
        }

        List<Book> books = bookRepository.findBookByBookIds(bookIdList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Book book : books) {
            Optional<Checkout> checkout = checkoutList.stream().filter(x -> x.getBook().getId() == book.getId()).findFirst();
            if (checkout.isPresent()) {

                Date d1 = sdf.parse(checkout.get().getReturnDate());
                logger.info("Return Date: " + d1);

                Date d2 = sdf.parse(LocalDate.now().toString());
                logger.info("Today: " + d2.getTime());

                long difference_In_Time = TimeUnit.MILLISECONDS.toDays(d1.getTime() - d2.getTime());

                logger.info("difference in time: " + difference_In_Time);
                shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book, (int) difference_In_Time));
                logger.info("book Id: " + book.getId());
            }
        }

        return shelfCurrentLoansResponses;
    }

    @Override
    public void returnBook(String userEmail, Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(book.isEmpty() || validateCheckout==null){
            throw new ServiceException("Book doesn't exist or checked out by the user");
        }
        book.get().setCopiesAvailable(book.get().getCopiesAvailable()+1);

        bookRepository.save(book.get());
        checkoutRepository.deleteById(validateCheckout.getId());

        History history = new History(userEmail,validateCheckout.getCheckoutDate(),LocalDate.now().toString(),book.get().getTitle(),book.get().getAuthor(),book.get().getDescription(),book.get().getImg());
        historyRepository.save(history);
    }

    @Override
    public void renewLoan(String userEmail, Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(book.isEmpty() || validateCheckout==null){
            throw new ServiceException("Book doesn't exist or checked out by the user");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date d1= sdf.parse(validateCheckout.getReturnDate());
        Date d2 = sdf.parse(LocalDate.now().toString());

        if(d1.compareTo(d2)>0 || d1.compareTo(d2) == 0){
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            logger.info("After renew new checkout: "+validateCheckout);
            checkoutRepository.save(validateCheckout);
        }
    }

}
