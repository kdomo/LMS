package com.domo.lms.controller;

import com.domo.lms.entity.Member;
import com.domo.lms.model.MemberDto;
import com.domo.lms.model.MemberParam;
import com.domo.lms.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class AdminMemberController {
    private final MemberService memberService;
    @GetMapping("/list")
    public String list(Model model, MemberParam parameter) {
        List<MemberDto> list = memberService.list(parameter);
        model.addAttribute("list", list);
        return "admin/member/list";
    }

    @GetMapping("/detail")
    public String detail(Model model, String userId) {
        MemberDto member = memberService.detail(userId);
        model.addAttribute("member", member);
        return "admin/member/detail";
    }
}
