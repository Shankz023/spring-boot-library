package com.luv2code.springbootlibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springbootlibrary.entity.Review;
import com.luv2code.springbootlibrary.exceptions.ServiceException;
import com.luv2code.springbootlibrary.requestmodels.ReviewRequest;
import com.luv2code.springbootlibrary.service.ReviewService;
import com.luv2code.springbootlibrary.service.impl.ReviewServiceImpl;
import com.luv2code.springbootlibrary.utils.ExtractJWT;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {


    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/secure")
    public ResponseEntity<Review> postReview(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody ReviewRequest reviewRequest) throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        if(userEmail== null){
            throw new ServiceException("User email is missing");
        }
        return ResponseEntity.ok(reviewService.postReview(userEmail,reviewRequest));
    }
    @GetMapping("/secure")
    public  Review getReview(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId){
        //ObjectMapper objectMapper = new ObjectMapper();

        //return reviewService.getReview(objectMapper.convertValue(bookId, Long.class));
        return reviewService.getReview(bookId);
    }
}
