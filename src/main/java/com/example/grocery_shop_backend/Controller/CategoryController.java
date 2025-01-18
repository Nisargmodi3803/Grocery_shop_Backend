package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.Category;
import com.example.grocery_shop_backend.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CategoryController
{
    @Autowired
    CategoryService categoryService;

    @GetMapping("/category")
    public List<Category> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/category-slug/{slugTitle}")
    public Category getCategoryBySlugTitle(@PathVariable String slugTitle)
    {
        return categoryService.getCategoryBySlugTitle(slugTitle);
    }

    @GetMapping("/category-id/{id}")
    public Category getCategoryById(@PathVariable int id)
    {
        return categoryService.getCategoryById(id);
    }
}
