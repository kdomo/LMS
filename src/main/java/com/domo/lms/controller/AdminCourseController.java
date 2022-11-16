package com.domo.lms.controller;

import com.domo.lms.model.CourseDto;
import com.domo.lms.model.CourseInput;
import com.domo.lms.model.CourseParam;
import com.domo.lms.model.MemberDto;
import com.domo.lms.service.CourseService;
import com.domo.lms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/course")
@RequiredArgsConstructor
public class AdminCourseController extends BaseController {
    private final CourseService courseService;

    @GetMapping("/list")
    public String list(Model model, CourseParam parameter) {
        parameter.init();

        List<CourseDto> list = courseService.list(parameter);

        long totalCount = 0;
        if (CollectionUtils.isEmpty(list)) {
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();

        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);


        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/course/list";
    }

    @GetMapping({"/add", "/edit"})
    public String add(Model model, HttpServletRequest request, CourseInput parameter) {
        boolean editMode = request.getRequestURI().contains("/edit");
        CourseDto detail = new CourseDto();

        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                // error 처리
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existCourse;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "admin/course/add";
    }

    @PostMapping({"/add", "/edit"})
    public String addSubmit(Model model, CourseInput parameter, HttpServletRequest request) {

        boolean editMode = request.getRequestURI().contains("/edit");
        CourseDto detail = new CourseDto();

        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                // error 처리
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }

            boolean result = courseService.set(parameter);

        } else {
            boolean result = courseService.add(parameter);
        }

        return "redirect:/admin/course/list";
    }
}
