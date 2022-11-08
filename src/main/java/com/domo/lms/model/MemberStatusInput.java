package com.domo.lms.model;

import com.domo.lms.type.UserStatus;
import lombok.Data;

@Data
public class MemberStatusInput {
    String userId;
    UserStatus userStatus;
}
