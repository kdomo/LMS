package com.domo.lms.mapper;

import com.domo.lms.model.MemberDto;
import com.domo.lms.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberDto> selectList(MemberParam parameter);
}
