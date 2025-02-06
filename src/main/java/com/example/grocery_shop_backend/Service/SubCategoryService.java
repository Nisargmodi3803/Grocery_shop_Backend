package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.SubcategoryDTO;
import com.example.grocery_shop_backend.Entities.Category;
import com.example.grocery_shop_backend.Entities.SubCategory;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CategoryRepository;
import com.example.grocery_shop_backend.Repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class SubCategoryService
{
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Find All Subcategories Service
    public List<SubCategory> getAllSubCategories()
    {
        return subCategoryRepository.findAllSubCategories();
    }

    // Find Subcategory by ID
    public SubCategory getSubCategoryById(int id)
    {
        SubCategory subCategory = subCategoryRepository.findSubCategoryById(id);
        if(subCategory == null)
        {
            throw new objectNotFoundException("SubCategory with Id "+id+" not found");
        }
        return subCategory;
    }

    // Find Subcategory by Slug title Service
    public SubCategory getSubCategoryBySlugTitle(String slugTitle)
    {
        SubCategory subCategory = subCategoryRepository.findSubCategoryBySlugTitle(slugTitle);
        if(subCategory == null)
        {
            throw new objectNotFoundException("SubCategory with title "+slugTitle+" not found");
        }
        return subCategory;
    }

    // Find Subcategories by Category ID Service
    public List<SubCategory> getSubCategoryByCategoryId(int categoryId)
    {
        List<SubCategory> subCategories = subCategoryRepository.findSubCategoryByCategoryID(categoryId);
        if(subCategories.isEmpty())
        {
            throw new objectNotFoundException("Category with Id "+categoryId+" not found");
        }
        return subCategories;
    }

    // Find Subcategories by Category Slug tile Service
    public List<SubCategory> getSubCategoryByCategorySlugTitle(String categorySlugTitle)
    {
        List<SubCategory> subCategories = subCategoryRepository.findSubCategoryByCategorySlugTitle(categorySlugTitle);
        if(subCategories.isEmpty())
        {
            throw new objectNotFoundException("Category with Id "+categorySlugTitle+" not found");
        }
        return subCategories;
    }

    // Add New Subcategory Service
    public void addSubcategory(SubcategoryDTO subcategoryDTO)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        Category category = categoryRepository.findCategoryById(subcategoryDTO.getCategoryId());

        if(category!=null)
        {
            SubCategory subCategory = new SubCategory();
            subCategory.setCategory(category);
            subCategory.setName(subcategoryDTO.getName());
            subCategory.setDescription(subcategoryDTO.getDescription());
            subCategory.setImage_url(subcategoryDTO.getImage());
            subCategory.setPriority(subcategoryDTO.getPriority());
            subCategory.setSlug_title(subcategoryDTO.getSlugTitle());
            subCategory.setIs_deleted(1);
            subCategory.setC_date(cDate);
            subCategoryRepository.save(subCategory);
        }
        else
            throw new objectNotFoundException("Category with id "+subcategoryDTO.getCategoryId()+" not found");
    }

    // Update Subcategory
    public SubCategory updateSubcategory(int subcategoryId, SubcategoryDTO subcategoryDTO)
    {
        SubCategory subCategory = subCategoryRepository.findSubCategoryById(subcategoryId);

        if(subCategory!=null)
        {
            if(subcategoryDTO.getName()!=null)
                subCategory.setName(subcategoryDTO.getName());
            if(subcategoryDTO.getDescription()!=null)
                subCategory.setDescription(subcategoryDTO.getDescription());
            if (subcategoryDTO.getImage()!=null)
                subCategory.setImage_url(subcategoryDTO.getImage());
            if(subcategoryDTO.getPriority()!=0)
                subCategory.setPriority(subcategoryDTO.getPriority());
            if(subcategoryDTO.getSlugTitle()!=null)
                subCategory.setSlug_title(subcategoryDTO.getSlugTitle());
            if(subcategoryDTO.getCategoryId()!=0)
            {
                Category category = categoryRepository.findCategoryById(subcategoryDTO.getCategoryId());
                if (category != null)
                    subCategory.setCategory(category);
                else
                    throw new objectNotFoundException("Category with id "+subcategoryDTO.getCategoryId()+" not found");
            }
        }
        else
            throw new objectNotFoundException("Subcategory with id "+subcategoryId+" not found");

        return subCategoryRepository.save(subCategory);
    }

    // Delete Subcategory Service
    public void deleteSubcategory(int subcategoryId)
    {
        SubCategory subCategory = subCategoryRepository.findSubCategoryById(subcategoryId);

        if(subCategory!=null)
        {
            subCategory.setIs_deleted(2);
            subCategoryRepository.save(subCategory);
        }
        else
            throw new objectNotFoundException("Subcategory with id "+subcategoryId+" not found");
    }

    // Retrieve Subcategory Service
    public boolean retrieveSubcategory(int subcategoryId)
    {
        SubCategory subCategory = subCategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new objectNotFoundException("Subcategory with id "+subcategoryId+" not found"));

        if(subCategory.getIs_deleted()==1)
            return false;
        else
        {
            subCategory.setIs_deleted(1);
            subCategoryRepository.save(subCategory);
            return true;
        }
    }
}
