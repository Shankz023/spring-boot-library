package com.luv2code.springbootlibrary.requestmodels;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
public class ReviewRequest {

    @NotNull
    private Double rating;
    @NotNull
    private Long bookId;
    private String reviewDescription;
}
