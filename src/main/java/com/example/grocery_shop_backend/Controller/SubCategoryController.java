package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.SubcategoryDTO;
import com.example.grocery_shop_backend.Entities.SubCategory;
import com.example.grocery_shop_backend.Service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubCategoryController
{
    @Autowired
    private SubCategoryService subCategoryService;

    // GET API {Find all Subcategories}
    @GetMapping("/subcategories")
    public List<SubCategory> getAllSubCategory()
    {
        return subCategoryService.getAllSubCategories();
    }

    // GET API {Find Subcategory by Slug title}
    @GetMapping("/subcategory-title/{slugTitle}")
    public SubCategory getSubCategoryBySlugTitle(@PathVariable String slugTitle)
    {
        return subCategoryService.getSubCategoryBySlugTitle(slugTitle);
    }

    // GET API {Find Subcategory by ID}
    @GetMapping("/subcategory/{id}")
    public SubCategory getSubCategoryById(@PathVariable int id)
    {
        return subCategoryService.getSubCategoryById(id);
    }

    // GET API {Find Subcategories by Category ID}
    @GetMapping("/subcategories-category/{id}")
    public List<SubCategory> getSubCategoryByCategoryId(@PathVariable int id)
    {
        return subCategoryService.getSubCategoryByCategoryId(id);
    }

    // GET API {Find Subcategories by Category Slug title}
    @GetMapping("/subcategories-category-title/{slugTitle}")
    public List<SubCategory> getSubCategoryByCategorySlugTitle(@PathVariable String slugTitle)
    {
        return subCategoryService.getSubCategoryByCategorySlugTitle(slugTitle);
    }

    // POST API {Add New Subcategory}
    @PostMapping("/add-subcategory")
    public ResponseEntity<String> addSubcategory(@RequestBody SubcategoryDTO subcategoryDTO)
    {
        try {
            subCategoryService.addSubcategory(subcategoryDTO);
            return new ResponseEntity<>("Subcategory added successfully", HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // PATCH API {Update Subcategory}
    @PatchMapping("/update-subcategory/{subcategoryId}")
    public SubCategory updateSubcategory(@PathVariable int subcategoryId, @RequestBody SubcategoryDTO subcategoryDTO)
    {
        return subCategoryService.updateSubcategory(subcategoryId, subcategoryDTO);
    }

    // PATCH API {Delete Subcategory}
    @PatchMapping("/delete-subcategory/{subcategoryId}")
    public ResponseEntity<String> deleteSubcategory(@PathVariable int subcategoryId)
    {
        subCategoryService.deleteSubcategory(subcategoryId);
        return new ResponseEntity<>("Subcategory deleted successfully", HttpStatus.OK);
    }

    // PATCH API {Retrieve Subcategory}
    @PatchMapping("/retrieve-subcategory/{subcategoryId}")
    public ResponseEntity<String> retrieveSubcategory(@PathVariable int subcategoryId)
    {
        boolean success = subCategoryService.retrieveSubcategory(subcategoryId);

        if(success)
            return new ResponseEntity<>("Subcategory retrieved successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Subcategory Already Present", HttpStatus.BAD_REQUEST);
    }
}
