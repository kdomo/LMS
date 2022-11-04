package com.domo.lms.service;

import com.domo.lms.model.MemberInput;
import com.domo.lms.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    boolean register(MemberInput parameter);
    boolean emailAuth(String uuid);
    boolean sendResetPassword(ResetPasswordInput parameter);

    boolean resetPassword(String uuid, String password);

    boolean checkResetPassword(String uuid);
}
