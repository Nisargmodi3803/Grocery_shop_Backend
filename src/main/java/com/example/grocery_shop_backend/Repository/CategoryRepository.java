package com.example.grocery_shop_backend.Repository;

// Category Repository

import com.example.grocery_shop_backend.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>
{
    @Query("SELECT cat FROM Category cat WHERE cat.id = :id")
    Category findCategoryById(int id);

    @Query("SELECT cat FROM Category cat WHERE cat.slug_title = :slugTitle")
    Category findCategoryBySlugTitle(String slugTitle);
}
