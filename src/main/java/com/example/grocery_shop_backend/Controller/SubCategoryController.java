package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.SubcategoryDTO;
import com.example.grocery_shop_backend.Entities.Category;
import com.example.grocery_shop_backend.Entities.SubCategory;
import com.example.grocery_shop_backend.Service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class SubCategoryController
{
    @Autowired
    private SubCategoryService subCategoryService;

    // GET API {Find all Subcategories}
    @GetMapping("/subcategories")
    public ResponseEntity<List<SubCategory>> getAllSubCategory()
    {
        try {
            return ResponseEntity.ok(subCategoryService.getAllSubCategories());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // GET API {Find Subcategory by Slug title}
    @GetMapping("/subcategory-title/{slugTitle}")
    public ResponseEntity<SubCategory> getSubCategoryBySlugTitle(@PathVariable String slugTitle)
    {
        try {
            return ResponseEntity.ok(subCategoryService.getSubCategoryBySlugTitle(slugTitle));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // GET API {Find Subcategory by ID}
    @GetMapping("/subcategory/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable int id)
    {
        try{
            return ResponseEntity.ok(subCategoryService.getSubCategoryById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // GET API {Find Subcategories by Category ID}
    @GetMapping("/subcategories-category/{id}")
    public ResponseEntity<List<SubCategory>> getSubCategoryByCategoryId(@PathVariable int id)
    {
       try {
           return ResponseEntity.ok(subCategoryService.getSubCategoryByCategoryId(id));
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       }
    }

    // GET API {Find Subcategories by Category Slug title}
    @GetMapping("/subcategories-category-title/{slugTitle}")
    public ResponseEntity<List<SubCategory>> getSubCategoryByCategorySlugTitle(@PathVariable String slugTitle)
    {
        try{
            return ResponseEntity.ok(subCategoryService.getSubCategoryByCategorySlugTitle(slugTitle));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // POST API {Add New Subcategory}
    @PostMapping("/add-subcategory")
    public ResponseEntity<String> addSubcategory(@ModelAttribute SubcategoryDTO subcategoryDTO)
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
    public ResponseEntity<String> updateSubcategory(@PathVariable int subcategoryId, @ModelAttribute SubcategoryDTO subcategoryDTO)
    {
        try {
            subCategoryService.updateSubcategory(subcategoryId, subcategoryDTO);
            return new ResponseEntity<>("Subcategory updated successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // PATCH API {Delete Subcategory}
    @PatchMapping("/delete-subcategory/{subcategoryId}")
    public ResponseEntity<String> deleteSubcategory(@PathVariable int subcategoryId)
    {
        try {
            subCategoryService.deleteSubcategory(subcategoryId);
            return new ResponseEntity<>("Subcategory deleted successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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

    // GET API {Search Category}
    @GetMapping("/search-subcategory")
    public ResponseEntity<List<SubCategory>> searchCategory(@RequestParam String keyword){
        try {
            return ResponseEntity.ok(subCategoryService.searchSubcategory(keyword));
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Check Slug Title}
    @GetMapping("/check-subcategory-slug-title")
    public ResponseEntity<Boolean> checkSlugTitle(@RequestParam String slugTitle){
        return ResponseEntity.ok(subCategoryService.checkSlugTitles(slugTitle));
    }
}
