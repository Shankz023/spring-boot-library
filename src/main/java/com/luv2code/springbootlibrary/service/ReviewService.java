package com.luv2code.springbootlibrary.service;

import com.luv2code.springbootlibrary.entity.Review;
import com.luv2code.springbootlibrary.exceptions.ServiceException;
import com.luv2code.springbootlibrary.requestmodels.ReviewRequest;

public interface ReviewService {
    public Review postReview(String userEmail, ReviewRequest reviewRequest) throws ServiceException;

    public Review getReview(Long bookId);

    public Boolean userReviewListed(String userEmail, Long BookId);
}
