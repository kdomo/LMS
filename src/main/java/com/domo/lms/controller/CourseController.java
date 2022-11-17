package com.domo.lms.controller;

import com.domo.lms.model.CategoryDto;
import com.domo.lms.model.CourseDto;
import com.domo.lms.model.CourseParam;
import com.domo.lms.service.CategoryService;
import com.domo.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController extends BaseController {
    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping
    public String course(Model model, CourseParam parameter) {
        List<CourseDto> list = courseService.frontList(parameter);
        model.addAttribute("list", list);

        List<CategoryDto> categoryList = categoryService.frontList(CategoryDto.builder().build());

        int courseTotalCount = 0;
        if (categoryList != null) {
            for (CategoryDto x : categoryList) {
                courseTotalCount += x.getCourseCount();
            }
        }

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("courseTotalCount", courseTotalCount);

        return "course/index";
    }
}
