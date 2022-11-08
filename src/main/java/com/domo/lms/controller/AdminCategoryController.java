package com.domo.lms.controller;

import com.domo.lms.model.CategoryDto;
import com.domo.lms.model.CategoryInput;
import com.domo.lms.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model) {
        List<CategoryDto> list = categoryService.list();
        model.addAttribute("list", list);
        return "admin/category/list";
    }

    @PostMapping("/add")
    public String add(Model model, CategoryInput parameter) {
        boolean result = categoryService.add(parameter.getCategoryName());
        return "redirect:/admin/category/list";
    }

    @PostMapping("/delete")
    public String delete(Model model, CategoryInput parameter) {
        boolean result = categoryService.delete(parameter.getId());
        return "redirect:/admin/category/list";
    }
}
