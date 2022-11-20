package com.domo.lms.service;

import com.domo.lms.model.*;

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

    /*
    * 강좌 내용 삭제
    * */
    boolean delete(String idList);

    /*
    * 프론트 - 강좌 목록 조회
    * */
    List<CourseDto> frontList(CourseParam parameter);

    /**
     * 프론트 - 강좌 상세 조회
     */
    CourseDto frontDetail(long id);

    /**
     * 프론트 - 수강신청
     *
     */
    ServiceReslut req(TakeCourseInput parameter);
}
