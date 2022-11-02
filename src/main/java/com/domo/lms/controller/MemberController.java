package com.domo.lms.controller;

import com.domo.lms.entity.Member;
import com.domo.lms.model.MemberInput;
import com.domo.lms.repository.MemberRepository;
import com.domo.lms.service.MemberService;
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
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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

}
