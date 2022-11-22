package com.domo.lms.service;

import com.domo.lms.entity.MemberLog;
import com.domo.lms.model.MemberLogDto;
import com.domo.lms.repository.MemberLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberLogServiceImpl implements MemberLogService{
    private final MemberLogRepository memberLogRepository;

    public List<MemberLogDto> findAllByUserId(String userId) {
        List<MemberLog> memberLogList = memberLogRepository.findAllByUserIdOrderByAccessDateDesc(userId);
        List<MemberLogDto> memberLogDtoList = MemberLogDto.of(memberLogList);
        return memberLogDtoList;
    }
}
