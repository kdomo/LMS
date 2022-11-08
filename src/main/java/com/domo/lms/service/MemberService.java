package com.domo.lms.service;

import com.domo.lms.entity.Member;
import com.domo.lms.model.MemberDto;
import com.domo.lms.model.MemberInput;
import com.domo.lms.model.MemberParam;
import com.domo.lms.model.ResetPasswordInput;
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
}
