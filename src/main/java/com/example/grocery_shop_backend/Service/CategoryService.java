package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.CategoryDTO;
import com.example.grocery_shop_backend.Entities.Category;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    // Find All Categories  Service
    public List<Category> getAllCategories()
    {
        return categoryRepository.findAllCategories();
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
    public void addCategory(CategoryDTO categoryDTO) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setSlug_title(categoryDTO.getSlugTitle());
        category.setPriority(categoryDTO.getPriority());
        category.setIs_deleted(1);
        category.setC_date(cDate);

        MultipartFile imageFile = categoryDTO.getImageFile();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Delete old image if present
        String oldImageName = category.getImage_url();
        if (oldImageName != null && !oldImageName.isEmpty()) {
            Path oldImagePath = uploadPath.resolve(oldImageName);
            if (Files.exists(oldImagePath)) {
                Files.delete(oldImagePath);
            }
        }

        // Save new image with brand name (force jpg if needed)
        String fileName = categoryDTO.getName();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        category.setImage_url(categoryDTO.getName());

        categoryRepository.save(category);
    }

    // Update Category Service
    public Category updateCategory(int categoryId, CategoryDTO categoryDTO) throws IOException {
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
            if(categoryDTO.getPriority()!=0){
                category.setPriority(categoryDTO.getPriority());
            }

            MultipartFile imageFile = categoryDTO.getImageFile();
            if (imageFile != null && !imageFile.isEmpty()) {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Delete old image if present
                String oldImageName = category.getImage_url();
                if (oldImageName != null && !oldImageName.isEmpty()) {
                    Path oldImagePath = uploadPath.resolve(oldImageName);
                    if (Files.exists(oldImagePath)) {
                        Files.delete(oldImagePath);
                    }
                }

                // Save new image with brand name (force jpg if needed)
                String fileName = categoryDTO.getName();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                category.setImage_url(categoryDTO.getName());
            }
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

    // Search Category Service
    public List<Category> searchCategory(String keyword){
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(keyword);

        if(categories == null){
            throw new objectNotFoundException("Category with name "+keyword+" not found");
        }
        return categories;
    }

    // Check Slug Title Service
    public boolean checkSlugTitles(String slugTitle){
        String slug_title = categoryRepository.checkSlugTitles(slugTitle);
        if(slug_title != null && !slug_title.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
