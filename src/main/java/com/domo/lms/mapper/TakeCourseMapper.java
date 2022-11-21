package com.domo.lms.mapper;

import com.domo.lms.model.TakeCourseDto;
import com.domo.lms.model.TakeCourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TakeCourseMapper {
    List<TakeCourseDto> selectList(TakeCourseParam parameter);
    long selectListCount(TakeCourseParam parameter);

    List<TakeCourseDto> selectListMyCourse(TakeCourseParam parameter);

}
