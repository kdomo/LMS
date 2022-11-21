package com.domo.lms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResult {
    private boolean result;
    private String message;

    public ServiceResult(boolean result) {
        this.result = result;
    }
}
