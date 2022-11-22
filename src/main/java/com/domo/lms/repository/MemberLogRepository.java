package com.domo.lms.repository;

import com.domo.lms.entity.MemberLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberLogRepository extends JpaRepository<MemberLog, Long> {
    List<MemberLog> findAllByUserIdOrderByAccessDateDesc(String userId);
}
