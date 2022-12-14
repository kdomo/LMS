package com.domo.lms.service;

import com.domo.lms.entity.Category;
import com.domo.lms.exception.CategoryNoyFoundException;
import com.domo.lms.mapper.CategoryMapper;
import com.domo.lms.model.CategoryDto;
import com.domo.lms.model.CategoryInput;
import com.domo.lms.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "sortValue");
        List<Category> list = categoryRepository.findAll(sort);
        return CategoryDto.of(list);
    }

    @Override
    public boolean add(String categoryName) {
        Category category = Category.builder()
                .categoryName(categoryName)
                .sortValue(0)
                .usingYn(true)
                .build();
        categoryRepository.save(category);
        return false;
    }


    @Override
    public boolean delete(long id) {
        categoryRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean update(CategoryInput categoryInput) {
        Category category = categoryRepository.findById(categoryInput.getId())
                .orElseThrow(() -> new CategoryNoyFoundException("카테고리를 찾지 못하였습니다."));
        category.setCategoryName(categoryInput.getCategoryName());
        category.setSortValue(categoryInput.getSortValue());
        category.setUsingYn(categoryInput.isUsingYn());
        categoryRepository.save(category);
        return true;
    }

    @Override
    public List<CategoryDto> frontList(CategoryDto parameter) {
        return categoryMapper.select(parameter);
    }
}
