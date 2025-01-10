package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.SubCategory;
import com.example.grocery_shop_backend.Service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubCategoryController
{
    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping("/subcategory")
    public List<SubCategory> getAllSubCategory()
    {
        return subCategoryService.getAllSubCategories();
    }

    @GetMapping("/subcategory/title/{slugTitle}")
    public SubCategory getSubCategoryBySlugTitle(@PathVariable String slugTitle)
    {
        return subCategoryService.getSubCategoryBySlugTitle(slugTitle);
    }

    @GetMapping("/subcategory/{id}")
    public SubCategory getSubCategoryById(@PathVariable int id)
    {
        return subCategoryService.getSubCategoryById(id);
    }

    @GetMapping("/subcategory/category/{id}")
    public List<SubCategory> getSubCategoryByCategoryId(@PathVariable int id)
    {
        return subCategoryService.getSubCategoryByCategoryId(id);
    }

    @GetMapping("/subcategory/category/title/{slugTitle}")
    public List<SubCategory> getSubCategoryByCategorySlugTitle(@PathVariable String slugTitle)
    {
        return subCategoryService.getSubCategoryByCategorySlugTitle(slugTitle);
    }

}
