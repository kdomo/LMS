package com.domo.lms.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberInput {
    private String userId;
    private String userName;
    private String password;
    private String phone;

    private String newPassword;
    private String zipCode;
    private String addr;
    private String addrDetail;
}
