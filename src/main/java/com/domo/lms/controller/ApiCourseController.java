package com.domo.lms.controller;

import com.domo.lms.model.ServiceResult;
import com.domo.lms.model.TakeCourseInput;
import com.domo.lms.model.common.ResponseReslut;
import com.domo.lms.service.CategoryService;
import com.domo.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class ApiCourseController extends BaseController {
    private final CourseService courseService;
    private final CategoryService categoryService;

    @PostMapping("req.api")
    public ResponseEntity<ResponseReslut> courseReq(@RequestBody TakeCourseInput parameter,
                                       Principal principal) {
        parameter.setUserId(principal.getName());
        ServiceResult result = courseService.req(parameter);
        if (!result.isResult()) {
            ResponseReslut responseReslut = new ResponseReslut(false, result.getMessage());
            return ResponseEntity.ok().body(responseReslut);
        }
        ResponseReslut responseReslut = new ResponseReslut(true);
        return ResponseEntity.ok().body(responseReslut);
    }

}
