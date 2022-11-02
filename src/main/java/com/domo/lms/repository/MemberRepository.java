package com.domo.lms.repository;

import com.domo.lms.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository  extends JpaRepository<Member, String> {

}
