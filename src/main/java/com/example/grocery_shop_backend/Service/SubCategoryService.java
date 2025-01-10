package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Entities.SubCategory;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService
{
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public List<SubCategory> getAllSubCategories()
    {
        return subCategoryRepository.findAll();
    }

    public SubCategory getSubCategoryById(int id)
    {
        SubCategory subCategory = subCategoryRepository.findSubCategoryById(id);
        if(subCategory == null)
        {
            throw new objectNotFoundException("SubCategory with Id "+id+" not found");
        }
        return subCategory;
    }

    public SubCategory getSubCategoryBySlugTitle(String slugTitle)
    {
        SubCategory subCategory = subCategoryRepository.findSubCategoryBySlugTitle(slugTitle);
        if(subCategory == null)
        {
            throw new objectNotFoundException("SubCategory with title "+slugTitle+" not found");
        }
        return subCategory;
    }

    public List<SubCategory> getSubCategoryByCategoryId(int categoryId)
    {
        List<SubCategory> subCategories = subCategoryRepository.findSubCategoryByCategoryID(categoryId);
        if(subCategories.isEmpty())
        {
            throw new objectNotFoundException("Category with Id "+categoryId+" not found");
        }
        return subCategories;
    }

    public List<SubCategory> getSubCategoryByCategorySlugTitle(String categorySlugTitle)
    {
        List<SubCategory> subCategories = subCategoryRepository.findSubCategoryByCategorySlugTitle(categorySlugTitle);
        if(subCategories.isEmpty())
        {
            throw new objectNotFoundException("Category with Id "+categorySlugTitle+" not found");
        }
        return subCategories;
    }
}
