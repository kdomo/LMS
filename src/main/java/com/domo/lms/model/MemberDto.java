package com.domo.lms.model;

import com.domo.lms.type.ROLE;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto {
    private String userId;

    private String userName;

    private String phone;

    private String password;

    private LocalDateTime regDt;


    private boolean emailAuthYn;

    private LocalDateTime emailAuthDt;

    private String emailAuthKey;

    private String resetPasswordKey;

    private LocalDateTime resetPasswordLimitDt;

    @Enumerated(EnumType.STRING)
    private ROLE role;
}
