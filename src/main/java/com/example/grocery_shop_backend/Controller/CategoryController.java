package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.CategoryDTO;
import com.example.grocery_shop_backend.Entities.Category;
import com.example.grocery_shop_backend.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CategoryController
{
    @Autowired
    CategoryService categoryService;

    // GET API {Find all Categories}
    @GetMapping("/categories")
    public List<Category> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    // GET API {Find Category by Slug Title}
    @GetMapping("/category-slug/{slugTitle}")
    public Category getCategoryBySlugTitle(@PathVariable String slugTitle)
    {
        return categoryService.getCategoryBySlugTitle(slugTitle);
    }

    // GET API {Find Category By ID}
    @GetMapping("/category-id/{id}")
    public Category getCategoryById(@PathVariable int id)
    {
        return categoryService.getCategoryById(id);
    }

    // POST API {Add Category}
    @PostMapping("/add-category")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDTO categoryDTO)
    {
        try {
            categoryService.addCategory(categoryDTO);
            return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // PATCH API {Update Category}
    @PatchMapping("/update-category/{categoryId}")
    public Category updateCategory(@PathVariable int categoryId, @RequestBody CategoryDTO categoryDTO)
    {
        return categoryService.updateCategory(categoryId, categoryDTO);
    }

    // PATCH API {Delete Category}
    @PatchMapping("/delete-category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable int categoryId)
    {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }

    // PATCH API {Retrieve Category}
    @PatchMapping("/retrieve-category/{categoryId}")
    public ResponseEntity<String> retrieveCategory(@PathVariable int categoryId)
    {
        boolean success = categoryService.retrieveCategory(categoryId);

        if (success)
            return new ResponseEntity<>("Category retrieved successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Category retrieved failed", HttpStatus.BAD_REQUEST);
    }
}
