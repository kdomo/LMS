package com.domo.lms.repository;

import com.domo.lms.entity.MemberLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLogRepository extends JpaRepository<MemberLog, Long> {
}
