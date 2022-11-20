package com.domo.lms.model.common;

import lombok.Data;

@Data
public class ResponseReslutHeader {
    private boolean result;
    private String message;

    public ResponseReslutHeader(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResponseReslutHeader(boolean result) {
        this.result = result;
    }
}
