package com.luv2code.springbootlibrary.service.impl;

import com.luv2code.springbootlibrary.dao.ReviewRepository;
import com.luv2code.springbootlibrary.entity.Review;
import com.luv2code.springbootlibrary.exceptions.ServiceException;
import com.luv2code.springbootlibrary.requestmodels.ReviewRequest;
import com.luv2code.springbootlibrary.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;


@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Review postReview(String userEmail, ReviewRequest reviewRequest) throws ServiceException {
        Review validateReview = reviewRepository.findByBookIdAndUserEmail(reviewRequest.getBookId(), userEmail).orElse(null);
        if (validateReview != null) {
            throw new ServiceException("Review is already created");
        }

        Review review = Review.builder()
                .bookId(reviewRequest.getBookId())
                .userEmail(userEmail)
                .reviewDescription(reviewRequest.getReviewDescription())
                .date(Date.valueOf(LocalDate.now()))
                .rating(reviewRequest.getRating())
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public Review getReview(Long bookId) {
        return null;
        //return reviewRepository.findByBookId(bookId);
    }

    @Override
    public Boolean userReviewListed(String userEmail, Long bookId) {
        Review validateReview = reviewRepository.findByBookIdAndUserEmail(bookId, userEmail).orElse(null);
        return validateReview != null;
    }
}
