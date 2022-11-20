package com.domo.lms.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResponseReslut {
    private ResponseReslutHeader header;
    private Object body;

    public ResponseReslut(boolean result, String message) {
        header = new ResponseReslutHeader(result, message);
    }

    public ResponseReslut(boolean result) {
        header = new ResponseReslutHeader(result);
    }
}
