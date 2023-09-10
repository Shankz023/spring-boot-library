package com.luv2code.springbootlibrary.web.controller;

import com.luv2code.springbootlibrary.exceptions.ServiceException;
import com.luv2code.springbootlibrary.requestmodels.AddBookRequest;
import com.luv2code.springbootlibrary.service.AdminService;
import com.luv2code.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/secure/add/book")
    @ResponseStatus(HttpStatus.OK)
    public void postBook(@RequestHeader(value = "Authorization") String token, @RequestBody @NotNull AddBookRequest addBookRequest) {
        String userType = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (userType == null || !userType.equalsIgnoreCase("admin")) {
            throw new ServiceException("Admin is only allowed");
        } else {
            adminService.addBook(addBookRequest);
        }
    }

    @PutMapping("/secure/increase/book/quantity")
    @ResponseStatus(HttpStatus.OK)
    public void increaseBookQuantity(@RequestHeader(value = "Authorization") String token,@RequestParam @NotNull Long bookId) throws Exception {
        String userType = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (userType == null || !userType.equalsIgnoreCase("admin")) {
            throw new ServiceException("Admin is only allowed");
        } else {
            adminService.increaseBookQuantity(bookId);
        }
    }

    @PutMapping("/secure/decrease/book/quantity")
    @ResponseStatus(HttpStatus.OK)
    public void decreaseBookQuantity(@RequestHeader(value = "Authorization") String token,@RequestParam @NotNull Long bookId) throws Exception {
        String userType = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (userType == null || !userType.equalsIgnoreCase("admin")) {
            throw new ServiceException("Admin is only allowed");
        } else {
            adminService.decreaseBookQuantity(bookId);
        }
    }

    @DeleteMapping("/secure/delete/book")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@RequestHeader(value = "Authorization") String token,@RequestParam @NotNull Long bookId) throws Exception {
        String userType = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (userType == null || !userType.equalsIgnoreCase("admin")) {
            throw new ServiceException("Admin is only allowed");
        } else {
            adminService.deleteBook(bookId);
        }
    }
}
