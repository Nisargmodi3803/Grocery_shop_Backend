package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.CategoryDTO;
import com.example.grocery_shop_backend.Entities.Category;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

    // Find All Categories  Service
    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    // Find Category by ID Service
    public Category getCategoryById(int id)
    {
        Category category = categoryRepository.findCategoryById(id);
        if(category == null)
        {
            throw  new objectNotFoundException("Category with Id "+id+" not found");
        }
        return category;
    }

    // Find Category by Slug Title
    public Category getCategoryBySlugTitle(String slugTitle)
    {
        Category category = categoryRepository.findCategoryBySlugTitle(slugTitle);
        if(category == null)
        {
            throw  new objectNotFoundException("Category with title "+slugTitle+" not found");
        }
        return category;
    }

    // Add Category Service
    public void addCategory(CategoryDTO categoryDTO)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setImage_url(categoryDTO.getImage());
        category.setSlug_title(categoryDTO.getSlugTitle());
        category.setPriority(categoryRepository.findMaxPriority() + 1);
        category.setIs_deleted(1);
        category.setC_date(cDate);
        categoryRepository.save(category);
    }

    // Update Category Service
    public Category updateCategory(int categoryId, CategoryDTO categoryDTO)
    {
        Category category = categoryRepository.findCategoryById(categoryId);

        if(category!=null)
        {
            if(categoryDTO.getName() != null)
                category.setName(categoryDTO.getName());
            if(categoryDTO.getDescription() != null)
                category.setDescription(categoryDTO.getDescription());
            if(categoryDTO.getImage() != null)
                category.setImage_url(categoryDTO.getImage());
            if(categoryDTO.getSlugTitle() != null)
                category.setSlug_title(categoryDTO.getSlugTitle());
        }
        else
            throw new objectNotFoundException("Category with id "+categoryId+" not found");
        return categoryRepository.save(category);
    }

    // Delete Category Service
    public void deleteCategory(int categoryId)
    {
        Category category = categoryRepository.findCategoryById(categoryId);

        if (category != null)
        {
            category.setIs_deleted(2);
            categoryRepository.save(category);
        }
        else
            throw new objectNotFoundException("Category with id "+categoryId+" not found");
    }

    // Retrieve Category Service
    public boolean retrieveCategory(int categoryId)
    {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new objectNotFoundException("Category with id "+categoryId+" not found"));

        if(category.getIs_deleted() == 1)
            return false;
        else
        {
            category.setIs_deleted(1);
            categoryRepository.save(category);
            return true;
        }
    }
}
