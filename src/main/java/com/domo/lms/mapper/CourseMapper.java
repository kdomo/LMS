package com.domo.lms.mapper;

import com.domo.lms.model.CourseDto;
import com.domo.lms.model.CourseParam;
import com.domo.lms.model.MemberDto;
import com.domo.lms.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<CourseDto> selectList(CourseParam parameter);
    long selectListCount(CourseParam parameter);

}
