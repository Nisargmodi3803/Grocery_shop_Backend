package com.example.grocery_shop_backend.Repository;

// Category Repository

import com.example.grocery_shop_backend.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>
{
    @Query("SELECT cat FROM Category cat WHERE cat.id = :id AND cat.is_deleted=1")
    Category findCategoryById(int id);

    @Query("SELECT cat FROM Category cat WHERE cat.slug_title = :slugTitle AND cat.is_deleted=1")
    Category findCategoryBySlugTitle(String slugTitle);

    @Query("SELECT MAX(category.priority) FROM Category category")
    int findMaxPriority();
}
