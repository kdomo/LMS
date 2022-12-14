package com.domo.lms.service;

import com.domo.lms.model.*;
import com.domo.lms.type.UserStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {
    boolean register(MemberInput parameter);
    boolean emailAuth(String uuid);
    boolean sendResetPassword(ResetPasswordInput parameter);

    boolean resetPassword(String uuid, String password);

    boolean checkResetPassword(String uuid);

    List<MemberDto>list(MemberParam parameter);

    MemberDto detail(String userId);

    boolean updateStatus(String userId, UserStatus userStatus);

    boolean updatePassword(String userId, String password);

    ServiceResult updateMemberPassword(MemberInput parameter);

    /**
     * 회원 정보 수정
     */
    ServiceResult updateMember(MemberInput parameter);

    /**
     * 회원 탈퇴
     */
    ServiceResult withdraw(String userId, String password);
}
