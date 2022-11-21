package com.domo.lms.controller;

import com.domo.lms.model.ServiceResult;
import com.domo.lms.model.TakeCourseDto;
import com.domo.lms.model.TakeCourseParam;
import com.domo.lms.service.CategoryService;
import com.domo.lms.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/takecourse")
@RequiredArgsConstructor
public class AdminTakeCourseController extends BaseController {
    private final TakeCourseService takeCourseService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model, TakeCourseParam parameter) {
        parameter.init();

        List<TakeCourseDto> list = takeCourseService.list(parameter);

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(list)) {
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();

        String pager = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);


        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pager);

        return "admin/takecourse/list";
    }

    @PostMapping("/status")
    public String status(Model model, TakeCourseParam parameter){
        ServiceResult result = takeCourseService.updateStatus(parameter.getId(), parameter.getStatus());
        if (!result.isResult()) {
            model.addAttribute("msg", result.getMessage());
            return "common/error";
        }
        return "redirect:/admin/takecourse/list";
    }


}
