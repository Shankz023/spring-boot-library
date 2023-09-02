package com.luv2code.springbootlibrary.service;

import com.luv2code.springbootlibrary.dao.entity.Message;
import com.luv2code.springbootlibrary.requestmodels.AdminQuestionRequest;

public interface MessagesService {

    void postMessage(Message messageRequest, String userEmail);

    void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception;
}
