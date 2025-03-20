package com.example.grocery_shop_backend.Repository;

// Category Repository

import com.example.grocery_shop_backend.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>
{
    @Query("SELECT cat FROM Category cat WHERE cat.id = :id AND cat.isDeleted=1")
    Category findCategoryById(int id);

    @Query("SELECT cat FROM Category cat WHERE cat.slug_title = :slugTitle AND cat.isDeleted=1")
    Category findCategoryBySlugTitle(String slugTitle);

    @Query("SELECT MAX(category.priority) FROM Category category")
    int findMaxPriority();

    @Query("SELECT category FROM Category category WHERE category.isDeleted=1")
    List<Category> findAllCategories();

    // Search Category
    List<Category> findByNameContainingIgnoreCaseAndIsDeleted(String name,int isDeleted);

    @Query("SELECT category.slug_title FROM Category category WHERE category.isDeleted=1 AND category.slug_title = :slugTitle")
    public String checkSlugTitles(String slugTitle);
}
