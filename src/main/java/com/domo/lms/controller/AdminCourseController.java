package com.domo.lms.controller;

import com.domo.lms.model.CourseDto;
import com.domo.lms.model.CourseInput;
import com.domo.lms.model.CourseParam;
import com.domo.lms.model.MemberDto;
import com.domo.lms.service.CategoryService;
import com.domo.lms.service.CourseService;
import com.domo.lms.util.PageUtil;
import com.domo.lms.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/course")
@RequiredArgsConstructor
public class AdminCourseController extends BaseController {
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final S3Uploader s3Uploader;

    @GetMapping("/list")
    public String list(Model model, CourseParam parameter) {
        parameter.init();

        List<CourseDto> list = courseService.list(parameter);

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(list)) {
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();

        String pager = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);


        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pager);

        return "admin/course/list";
    }

    @GetMapping({"/add", "/edit"})
    public String add(Model model, HttpServletRequest request, CourseInput parameter) {
        model.addAttribute("categoryList", categoryService.list());


        boolean editMode = request.getRequestURI().contains("/edit");
        CourseDto detail = new CourseDto();

        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                // error ??????
                model.addAttribute("message", "?????? ????????? ???????????? ????????????.");
                return "common/error";
            }
            detail = existCourse;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "admin/course/add";
    }

    @PostMapping({"/add", "/edit"})
    public String addSubmit(Model model,
                            CourseInput parameter,
                            HttpServletRequest request,
                            MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String uploadPath = s3Uploader.upload(file, "files").replace("https://", "http://");
            parameter.setFileUrl(uploadPath);
        }

        boolean editMode = request.getRequestURI().contains("/edit");
        CourseDto detail = new CourseDto();


        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                // error ??????
                model.addAttribute("message", "?????? ????????? ???????????? ????????????.");
                return "common/error";
            }

            boolean result = courseService.set(parameter);

        } else {
            boolean result = courseService.add(parameter);
        }

        return "redirect:/admin/course/list";
    }

    @PostMapping("/delete")
    public String delete(Model model, CourseInput parameter, HttpServletRequest request) {
        boolean result = courseService.delete(parameter.getIdList());
        return "redirect:/admin/course/list";
    }
}
