package com.domo.lms.repository;

import com.domo.lms.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, String> {
    Optional<Member> findByEmailAuthKey(String emailAuthKey);
    Optional<Member> findByUserIdAndUserName(String userId, String userName);

    Optional<Member> findByResetPasswordKey(String resetPasswordKey);

}
