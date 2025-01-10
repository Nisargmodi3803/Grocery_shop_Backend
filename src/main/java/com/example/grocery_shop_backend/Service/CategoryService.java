package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Entities.Category;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id)
    {
        Category category = categoryRepository.findCategoryById(id);
        if(category == null)
        {
            throw  new objectNotFoundException("Category with Id "+id+" not found");
        }
        return category;
    }

    public Category getCategoryBySlugTitle(String slugTitle)
    {
        Category category = categoryRepository.findCategoryBySlugTitle(slugTitle);
        if(category == null)
        {
            throw  new objectNotFoundException("Category with title "+slugTitle+" not found");
        }
        return category;
    }
}
