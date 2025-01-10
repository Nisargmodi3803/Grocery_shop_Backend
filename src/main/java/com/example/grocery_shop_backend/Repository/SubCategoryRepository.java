package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Integer>
{
    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.id = :id")
    public SubCategory findSubCategoryById(int id);

    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.slug_title = :slugTitle")
    public SubCategory findSubCategoryBySlugTitle(String slugTitle);

    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.category.id = :cateID")
    public List<SubCategory> findSubCategoryByCategoryID(int cateID);

    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.category.slug_title = :cateSlugTitle")
    public List<SubCategory> findSubCategoryByCategorySlugTitle(String cateSlugTitle);
}
