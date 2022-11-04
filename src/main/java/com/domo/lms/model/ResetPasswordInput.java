package com.domo.lms.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResetPasswordInput {
    private String userId;
    private String userName;
    private String uuid;
    private String password;
}
