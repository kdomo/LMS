package com.domo.lms.service;

import com.domo.lms.model.CategoryDto;
import com.domo.lms.model.CategoryInput;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> list();

    /*
     * 카테고리 신규 추가
     * */
    boolean add(String categoryName);

    /*
     * 카테고리 삭제
     * */
    boolean delete(long id);

    /*
     * 카테고리 수정
     * */
    boolean update(CategoryInput categoryInput);

    /*
    * 프론트 카테고리 정보
    * */
    List<CategoryDto> frontList(CategoryDto parameter);
}
