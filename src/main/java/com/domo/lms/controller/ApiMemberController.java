package com.domo.lms.controller;

import com.domo.lms.model.*;
import com.domo.lms.model.common.ResponseReslut;
import com.domo.lms.service.MemberService;
import com.domo.lms.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiMemberController {
    private final TakeCourseService takeCourseService;

    @PostMapping("/api/member/course/cancel.api")
    public ResponseEntity<?> cancelCourse(Model model,
                                       @RequestBody TakeCourseInput courseInput,
                                       Principal principal) {
        String userId = principal.getName();

        TakeCourseDto detail = takeCourseService.detail(courseInput.getTakeCourseId());
        if (detail == null) {
            ResponseReslut reslut = new ResponseReslut(false, "수강 신청 정보가 존재하지 않습니다.");
            return ResponseEntity.ok().body(reslut);
        }

        if (userId == null || !userId.equals(detail.getUserId())) {
            ResponseReslut reslut = new ResponseReslut(false, "수강 신청 정보가 존재하지 않습니다.");
            return ResponseEntity.ok().body(reslut);
        }
        ServiceResult result = takeCourseService.cancel(courseInput.getTakeCourseId());

        if (!result.isResult()) {
            ResponseReslut reslut = new ResponseReslut(false, result.getMessage());
            return ResponseEntity.ok().body(reslut);
        }
        ResponseReslut reslut = new ResponseReslut(true);
        return ResponseEntity.ok().body(reslut);
    }
}
