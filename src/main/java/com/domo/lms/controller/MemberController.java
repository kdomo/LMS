package com.domo.lms.controller;

import com.domo.lms.entity.Member;
import com.domo.lms.model.*;
import com.domo.lms.repository.MemberRepository;
import com.domo.lms.service.MemberService;
import com.domo.lms.service.TakeCourseService;
import com.domo.lms.util.MailUtils;
import com.domo.lms.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TakeCourseService takeCourseService;

    @RequestMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/find/password")
    public String findPassword() {
        return "/member/find_password";
    }

    @PostMapping("/find/password")
    public String findPasswordSubmit(Model model, ResetPasswordInput parameter) {
        boolean result = memberService.sendResetPassword(parameter);
        model.addAttribute("result", result);
        return "member/find_password_result";
    }

    @GetMapping("/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/register")
    public String registerSubmit(Model model, MemberInput memberInput) {
        boolean result = memberService.register(memberInput);
        model.addAttribute("result", result);
        return "member/register_complete";
    }

    @GetMapping("/email-auth")
    public String emailAuth(Model model, String uuid) {
        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);
        return "member/email_auth";
    }

    @GetMapping("/info")
    public String memberInfo(Model model, Principal principal) {
        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);
        model.addAttribute("detail", detail);
        return "member/info";
    }

    @PostMapping("/info")
    public String memberInfoSubmit(Model model, MemberInput parameter, Principal principal) {
        String userId = principal.getName();
        parameter.setUserId(userId);
        ServiceResult result = memberService.updateMember(parameter);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }
        return "redirect:/member/info";
    }

    @GetMapping("/password")
    public String memberPassword(Model model, Principal principal) {
        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);
        model.addAttribute("detail", detail);
        return "member/password";
    }

    @PostMapping("/password")
    public String memberPasswordSubmit(Model model, Principal principal, MemberInput parameter) {
        String userId = principal.getName();
        parameter.setUserId(userId);
        ServiceResult result = memberService.updateMemberPassword(parameter);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }
        return "redirect:/member/info";
    }

    @GetMapping("/takecourse")
    public String memberTakeCourse(Model model, Principal principal) {
        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        List<TakeCourseDto> list = takeCourseService.myCourse(userId);
        model.addAttribute("detail", detail);
        model.addAttribute("list", list);
        return "member/takecourse";
    }

    @GetMapping("/reset/password")
    public String resetPassword(Model model, HttpServletRequest request) {
        String uuid = request.getParameter("uuid");
        boolean result = memberService.checkResetPassword(uuid);
        model.addAttribute("result", result);
        return "member/reset_password";
    }

    @PostMapping("/reset/password")
    public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {
        boolean result = false;
        try {
            result = memberService.resetPassword(parameter.getUuid(), parameter.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("result", result);
        return "member/reset_password_result";
    }

    @GetMapping("/withdraw")
    public String memberWithdraw() {
        return "member/withdraw";
    }

    @PostMapping("/withdraw")
    public String memberWithdraw(Model model, Principal principal, MemberInput memberInput) {
        String userId = principal.getName();

        ServiceResult result = memberService.withdraw(userId, memberInput.getPassword());
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/logout";
    }

}
