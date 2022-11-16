package com.domo.lms.service;

import com.domo.lms.model.CourseDto;
import com.domo.lms.model.CourseInput;
import com.domo.lms.model.CourseParam;

import java.util.List;

public interface CourseService {

    /*
    * 강좌 추가
    * */
    boolean add(CourseInput courseInput);

    /*
    * 강좌 목록
    * */
    List<CourseDto> list(CourseParam parameter);

    /*
    * 상세 정보
    * */
    CourseDto getById(long id);

    /**
     * 강좌 정보 수정
     */

    boolean set(CourseInput parameter);
}
