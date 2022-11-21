package com.domo.lms.service;

import com.domo.lms.model.TakeCourseDto;
import com.domo.lms.model.*;

import java.util.List;

public interface TakeCourseService {

    /*
    * 수강 목록
    * */
    List<TakeCourseDto> list(TakeCourseParam parameter);


    /**
     * 수강 상태 변경
     */
    ServiceResult updateStatus(long id, String status);

    /**
     * 내 수강 내역
     */
    List<TakeCourseDto> myCourse(String userId);

    /**
     * 수강 상세 정보
     */
    TakeCourseDto detail(long id);


    /**
     * 수강 신청 취소
     */
    ServiceResult cancel(long id);


}
