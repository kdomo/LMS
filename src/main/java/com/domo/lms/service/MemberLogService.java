package com.domo.lms.service;

import com.domo.lms.model.MemberLogDto;

import java.util.List;

public interface MemberLogService {
    List<MemberLogDto> findAllByUserId(String userId);
}
