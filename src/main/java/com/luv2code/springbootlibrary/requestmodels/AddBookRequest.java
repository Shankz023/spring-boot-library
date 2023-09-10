package com.luv2code.springbootlibrary.requestmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class AddBookRequest {

    private String title;
    private String author;
    private String description;
    private int copies;
    private int copiesAvailable;
    private String category;
    private String img;

    public void setCopiesAvailable() {
        this.copiesAvailable = copies;
    }
}
