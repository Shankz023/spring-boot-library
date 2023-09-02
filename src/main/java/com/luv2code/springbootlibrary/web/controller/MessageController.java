package com.luv2code.springbootlibrary.web.controller;

import com.luv2code.springbootlibrary.dao.entity.Message;
import com.luv2code.springbootlibrary.exceptions.ServiceException;
import com.luv2code.springbootlibrary.requestmodels.AdminQuestionRequest;
import com.luv2code.springbootlibrary.service.MessagesService;
import com.luv2code.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private MessagesService messagesService;

    @Autowired
    public MessageController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("/secure/add/message")
    public void PostMessage(@RequestHeader(value = "Authorization") String token, @RequestBody Message messageRequest) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        messagesService.postMessage(messageRequest, userEmail);
    }

    @PutMapping("/secure/admin/message")
    @ResponseStatus(HttpStatus.OK)
    public void putMessage(@RequestHeader(value = "Authorization") String token, @NotNull @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        String userType = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (userType == null || !userType.equalsIgnoreCase("admin")) {
            throw new ServiceException("Only Admin can respond to a message");
        } else {
            messagesService.putMessage(adminQuestionRequest, userEmail);
        }
    }
}
