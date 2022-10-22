package com.fiec.lpiiiback.utils;

import org.springframework.web.server.ResponseStatusException;

public class CustomException extends ResponseStatusException {

    public CustomException (ResultCodesException errorCode) {
        super(errorCode.getId(), errorCode.getFrontCode() + "" + errorCode.getMessage());

    }

    public CustomException (ResultCodesException errorCode, Exception ex) {
        super(errorCode.getId(),errorCode.getFrontCode() + "" + errorCode.getMessage(),ex);
    }
}