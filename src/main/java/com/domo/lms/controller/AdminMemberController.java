package com.domo.lms.controller;

import com.domo.lms.entity.Member;
import com.domo.lms.model.MemberDto;
import com.domo.lms.model.MemberParam;
import com.domo.lms.model.MemberStatusInput;
import com.domo.lms.service.MemberService;
import com.domo.lms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class AdminMemberController extends BaseController {
    private final MemberService memberService;
    @GetMapping("/list")
    public String list(Model model, MemberParam parameter) {
        parameter.init();

        List<MemberDto> list = memberService.list(parameter);
        model.addAttribute("list", list);

        long totalCount = 0;
        if (list != null && list.size() > 0) {
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();

        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);;

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);
        return "admin/member/list";
    }

    @GetMapping("/detail")
    public String detail(Model model, String userId) {
        MemberDto member = memberService.detail(userId);
        model.addAttribute("member", member);
        return "admin/member/detail";
    }

    @PostMapping("/status")
    public String status(Model model, MemberStatusInput parameter) {
        boolean result = memberService.updateStatus(parameter.getUserId(), parameter.getUserStatus());

        return "redirect:/admin/member/detail?userId=" + parameter.getUserId();
    }

    @PostMapping("/password")
    public String password(Model model, String userId, String password) {
        boolean result = memberService.updatePassword(userId, password);

        return "redirect:/admin/member/detail?userId=" ;
    }
}
