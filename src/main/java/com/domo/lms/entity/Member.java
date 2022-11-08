package com.domo.lms.entity;

import com.domo.lms.type.ROLE;
import com.domo.lms.type.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
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

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}
