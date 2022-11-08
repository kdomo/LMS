package com.domo.lms.service;

import com.domo.lms.model.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> list();

    boolean add(String categoryName);
    boolean update(CategoryDto categoryDto);
    boolean delete(long id);
}
