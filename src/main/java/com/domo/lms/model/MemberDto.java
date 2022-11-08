package com.domo.lms.model;

import com.domo.lms.entity.Member;
import com.domo.lms.type.ROLE;
import com.domo.lms.type.UserStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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

    private UserStatus userStatus;

    long totalCount;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phone(member.getPhone())
                .regDt(member.getRegDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .role(member.getRole())
                .userStatus(member.getUserStatus())
                .build();
    }
}
