package com.domo.lms.model;

import com.domo.lms.entity.Member;
import com.domo.lms.type.ROLE;
import com.domo.lms.type.UserStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private LocalDateTime uptDt;


    private boolean emailAuthYn;

    private LocalDateTime emailAuthDt;

    private String emailAuthKey;

    private String resetPasswordKey;

    private LocalDateTime resetPasswordLimitDt;

    @Enumerated(EnumType.STRING)
    private ROLE role;

    private UserStatus userStatus;

    private String zipCode;
    private String addr;
    private String addrDetail;

    private LocalDateTime lastAccessDate;

    long totalCount;

    long seq;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phone(member.getPhone())
                .regDt(member.getRegDt())
                .uptDt(member.getUptDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .role(member.getRole())
                .userStatus(member.getUserStatus())
                .zipCode(member.getZipCode())
                .addr(member.getAddr())
                .addrDetail(member.getAddrDetail())
                .build();
    }

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return regDt != null ? regDt.format(formatter) : "";
    }

    public String getUptDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return uptDt != null ? uptDt.format(formatter) : "";
    }


    public String getLastAccessDateText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return lastAccessDate != null ? lastAccessDate.format(formatter) : "";
    }
}
