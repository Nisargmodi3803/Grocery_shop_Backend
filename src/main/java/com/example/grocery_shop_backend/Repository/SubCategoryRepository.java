package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Integer>
{
    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.id = :id AND subCate.is_deleted=1")
    public SubCategory findSubCategoryById(int id);

    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.slug_title = :slugTitle AND subCate.is_deleted=1")
    public SubCategory findSubCategoryBySlugTitle(String slugTitle);

    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.is_deleted=1 AND (subCate.category.id = :cateID AND subCate.category.is_deleted=1)")
    public List<SubCategory> findSubCategoryByCategoryID(int cateID);

    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.is_deleted=1 AND (subCate.category.slug_title = :cateSlugTitle AND subCate.category.is_deleted=1)")
    public List<SubCategory> findSubCategoryByCategorySlugTitle(String cateSlugTitle);

    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.is_deleted=1")
    public List<SubCategory> findAllSubCategories();

    // Search
    public List<SubCategory> findByNameContainingIgnoreCase(String name);

    @Query("SELECT subcategory.slug_title FROM SubCategory subcategory WHERE subcategory.is_deleted=1 AND subcategory.slug_title = :slugTitle")
    public String checkSlugTitles(String slugTitle);
}
