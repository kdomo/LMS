package com.domo.lms.service;

import com.domo.lms.entity.Member;
import com.domo.lms.model.MemberInput;
import com.domo.lms.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public boolean register(MemberInput parameter) {
        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if(optionalMember.isPresent()){
            return false;
        }

        memberRepository.save(Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .password(parameter.getPassword())
                .phone(parameter.getPhone())
                .regDt(LocalDateTime.now())
                .build());
        return true;
    }
}
