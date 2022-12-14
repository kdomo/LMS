package com.domo.lms.service;

import com.domo.lms.exception.MemberNotEmailAuthException;
import com.domo.lms.entity.Member;
import com.domo.lms.exception.MemberUnAvailableException;
import com.domo.lms.mapper.MemberMapper;
import com.domo.lms.model.*;
import com.domo.lms.repository.MemberRepository;
import com.domo.lms.type.ROLE;
import com.domo.lms.type.UserStatus;
import com.domo.lms.util.MailUtils;
import com.domo.lms.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.domo.lms.type.ROLE.*;
import static com.domo.lms.type.UserStatus.*;

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
                .userStatus(REQUESTING)
                .build());

        String email = parameter.getUserId();
        String subject = "LMS ????????? ????????? ???????????????.";
        String text = "<p>LMS ????????? ????????? ???????????????.</p>" +
                "<p>?????? ????????? ???????????? ????????? ???????????????.</p>" +
                "<div><a href='http://localhost:8080/member/email-auth?uuid=" + uuid + "'>??????</a><div>";
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
        member.setUserStatus(AVAILABLE);
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {
        Member member = memberRepository
                .findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("?????? ????????? ???????????? ????????????."));

        String uuid = UUID.randomUUID().toString();
        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = "LMS ????????? ???????????? ????????? ??????";
        String text = "<p>LMS ????????? ???????????? ????????? ?????? ?????????.</p>" +
                "<p>?????? ????????? ???????????? ???????????? ???????????? ?????? ??? ?????????.</p>" +
                "<div><a href='http://localhost:8080/member/reset/password?uuid=" + uuid + "'>???????????? ????????? ??????</a><div>";
        mailUtils.sendMail(email, subject, text);
        return false;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {
        Member member = memberRepository.findByResetPasswordKey(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("?????? ????????? ???????????? ????????????."));

        if (member.getResetPasswordLimitDt() == null ||
                member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("???????????? ?????? ?????? ????????? ???????????????.");
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
        long totalCount = memberMapper.selectListCount(parameter);
        List<MemberDto> list = memberMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (MemberDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public MemberDto detail(String userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("?????? ?????? ???????????? ??????"));

        return MemberDto.of(member);
    }


    public boolean updateStatus(String userId, UserStatus userStatus) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("?????? ?????? ???????????? ??????"));
        member.setUserStatus(userStatus);
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean updatePassword(String userId, String password) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("?????? ?????? ???????????? ??????"));

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        memberRepository.save(member);
        return true;
    }

    @Override
    public ServiceResult updateMemberPassword(MemberInput parameter) {
        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "?????? ????????? ???????????? ????????????.");
        }
        Member member = optionalMember.get();

        if (PasswordUtil.equals(parameter.getPassword(), member.getPassword())) {
            return new ServiceResult(false, "??????????????? ???????????? ????????????.");
        }
        String encPassword = PasswordUtil.encPassword(parameter.getNewPassword());
        member.setPassword(encPassword);
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult updateMember(MemberInput parameter) {
        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "?????? ????????? ???????????? ????????????.");
        }
        Member member = optionalMember.get();
        member.setPhone(parameter.getPhone());
        member.setZipCode(parameter.getZipCode());
        member.setAddr(parameter.getAddr());
        member.setAddrDetail(parameter.getAddrDetail());
        member.setUptDt(LocalDateTime.now());
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult withdraw(String userId, String password) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "?????? ????????? ???????????? ????????????.");
        }
        Member member = optionalMember.get();

        if (PasswordUtil.equals(password, member.getPassword())) {
            return new ServiceResult(false, "??????????????? ???????????? ????????????.");
        }

        member.setUserName("????????????");
        member.setPhone("");
        member.setPassword("");
        member.setRegDt(null);
        member.setUptDt(null);
        member.setEmailAuthYn(false);
        member.setEmailAuthDt(null);
        member.setEmailAuthKey("");
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        member.setUserStatus(WITHDRAW);
        member.setZipCode("");
        member.setAddr("");
        member.setAddrDetail("");
        memberRepository.save(member);
        return new ServiceResult(true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("?????? ????????? ???????????? ????????????."));

        if (member.getUserStatus() == REQUESTING) {
            throw new MemberNotEmailAuthException("????????? ????????? ????????? ???????????? ????????????.");
        }
        if (member.getUserStatus() == UNAVAILABLE) {
            throw new MemberUnAvailableException("????????? ?????? ?????????.");
        }
        if (member.getUserStatus() == WITHDRAW) {
            throw new MemberUnAvailableException("????????? ?????? ?????????.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (member.getRole() == ADMIN) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

            return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
