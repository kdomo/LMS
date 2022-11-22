package com.domo.lms.mapper;

import com.domo.lms.model.BannerDto;
import com.domo.lms.model.BannerParam;
import com.domo.lms.model.CourseDto;
import com.domo.lms.model.CourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {
    List<BannerDto> selectList(BannerParam parameter);
    long selectListCount(BannerParam parameter);

}
