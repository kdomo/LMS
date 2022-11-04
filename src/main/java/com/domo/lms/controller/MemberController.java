package com.domo.lms.controller;

import com.domo.lms.entity.Member;
import com.domo.lms.model.MemberInput;
import com.domo.lms.model.ResetPasswordInput;
import com.domo.lms.repository.MemberRepository;
import com.domo.lms.service.MemberService;
import com.domo.lms.util.MailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
    public String memberInfo() {
        return "member/info";
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

}
