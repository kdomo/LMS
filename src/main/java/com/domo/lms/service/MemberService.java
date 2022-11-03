package com.domo.lms.service;

import com.domo.lms.model.MemberInput;

public interface MemberService {
    boolean register(MemberInput parameter);
    boolean emailAuth(String uuid);
}
