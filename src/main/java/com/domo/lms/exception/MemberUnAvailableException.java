package com.domo.lms.exception;

public class MemberUnAvailableException extends RuntimeException {
    public MemberUnAvailableException(String error) {
        super(error);
    }
}
