package com.domo.lms.service;

import com.domo.lms.entity.Member;
import com.domo.lms.model.MemberInput;
import com.domo.lms.repository.MemberRepository;
import com.domo.lms.util.MailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MailUtils mailUtils;

    @Override
    public boolean register(MemberInput parameter) {
        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if (optionalMember.isPresent()) {
            return false;
        }

        String uuid = UUID.randomUUID().toString();
        memberRepository.save(Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .password(parameter.getPassword())
                .phone(parameter.getPhone())
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build());

        String email = parameter.getUserId();
        String subject = "LMS 사이트 가입을 축하합니다.";
        String text = "<p>LMS 사이트 가입을 축하합니다.</p>" +
                "<p>아래 링크를 클릭하여 가입을 완료하세요.</p>" +
                "<div><a href='http://localhost:8080/member/email-auth?uuid=" + uuid + "'>링크</a><div>";
        mailUtils.sendMail(email, subject, text);
        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if(!optionalMember.isPresent()){
            return false;
        }
        Member member = optionalMember.get();
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);
        return true;
    }
}
