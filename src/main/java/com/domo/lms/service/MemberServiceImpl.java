package com.domo.lms.service;

import com.domo.lms.exception.MemberNotEmailAuthException;
import com.domo.lms.entity.Member;
import com.domo.lms.mapper.MemberMapper;
import com.domo.lms.model.MemberDto;
import com.domo.lms.model.MemberInput;
import com.domo.lms.model.MemberParam;
import com.domo.lms.model.ResetPasswordInput;
import com.domo.lms.repository.MemberRepository;
import com.domo.lms.type.ROLE;
import com.domo.lms.util.MailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.domo.lms.type.ROLE.*;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MailUtils mailUtils;
    private final MemberMapper memberMapper;

    @Override
    public boolean register(MemberInput parameter) {
        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if (optionalMember.isPresent()) {
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
        String uuid = UUID.randomUUID().toString();
        memberRepository.save(Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .password(encPassword)
                .phone(parameter.getPhone())
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .role(USER)
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
        if (!optionalMember.isPresent()) {
            return false;
        }
        Member member = optionalMember.get();

        if (member.isEmailAuthYn()) {
            return false;
        }

        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {
        Member member = memberRepository
                .findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        String uuid = UUID.randomUUID().toString();
        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = "LMS 사이트 비밀번호 초기화 메일";
        String text = "<p>LMS 사이트 비밀번호 초기화 메일 입니다.</p>" +
                "<p>아래 링크를 클릭하여 비밀번호 초기화를 진행 해 주세요.</p>" +
                "<div><a href='http://localhost:8080/member/reset/password?uuid=" + uuid + "'>비밀번호 초기화 링크</a><div>";
        mailUtils.sendMail(email, subject, text);
        return false;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {
        Member member = memberRepository.findByResetPasswordKey(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        if (member.getResetPasswordLimitDt() == null ||
                member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("비밀번호 변경 유효 기간이 끝났습니다.");
        }
        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member> member = memberRepository.findByResetPasswordKey(uuid);
        if (!member.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public List<MemberDto> list(MemberParam parameter) {
        List<MemberDto> list = memberMapper.selectList(parameter);
        return list;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        if (!member.isEmailAuthYn()) {
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (member.getRole() == ADMIN) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

            return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
