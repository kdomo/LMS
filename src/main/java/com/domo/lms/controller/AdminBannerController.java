package com.domo.lms.controller;

import com.domo.lms.model.*;
import com.domo.lms.service.BannerService;
import com.domo.lms.service.CategoryService;
import com.domo.lms.service.CourseService;
import com.domo.lms.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/banner")
@RequiredArgsConstructor
public class AdminBannerController extends BaseController {
    private final CourseService courseService;
    private final BannerService bannerService;
    private final S3Uploader s3Uploader;

    @GetMapping("/list")
    public String list(Model model, BannerParam parameter) {
        parameter.init();

        List<BannerDto> list = bannerService.list(parameter);

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(list)) {
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();

        String pager = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);


        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pager);

        return "admin/banner/list";
    }

    @GetMapping({"/add", "/edit"})
    public String add(Model model,
                      HttpServletRequest request,
                      BannerInput parameter) {
        boolean editMode = request.getRequestURI().contains("/edit");
        BannerDto detail = new BannerDto();

        if (editMode) {
            long id = parameter.getId();
            BannerDto bannerDto = bannerService.getById(id);
            if (bannerDto == null) {
                // error 처리
                model.addAttribute("message", "배너 내용이 존재하지 않습니다.");
                return "common/error";
            }
            detail = bannerDto;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "admin/banner/add";
    }

    @PostMapping({"/add", "/edit"})
    public String addSubmit(Model model,
                            BannerInput parameter,
                            HttpServletRequest request,
                            MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String uploadPath = s3Uploader.upload(file, "banner").replace("https://", "http://");
            parameter.setFileImgUrl(uploadPath);
        }


        boolean editMode = request.getRequestURI().contains("/edit");

        if (editMode) {
            long id = parameter.getId();
            BannerDto bannerDto = bannerService.getById(id);
            if (bannerDto == null) {
                // error 처리
                model.addAttribute("message", "배너 내용이 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = bannerService.set(parameter);
        } else {
            boolean result = bannerService.add(parameter);
        }

        return "redirect:/admin/banner/list";
    }

    @PostMapping("/delete")
    public String delete(Model model, CourseInput parameter) {
        boolean result = courseService.delete(parameter.getIdList());
        return "redirect:/admin/course/list";
    }
}
