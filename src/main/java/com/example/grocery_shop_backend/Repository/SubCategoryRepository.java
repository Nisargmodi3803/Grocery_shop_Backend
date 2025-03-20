package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Integer>
{
    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.id = :id AND subCate.isDeleted=1")
    public SubCategory findSubCategoryById(int id);

    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.slug_title = :slugTitle AND subCate.isDeleted=1")
    public SubCategory findSubCategoryBySlugTitle(String slugTitle);

    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.isDeleted=1 AND (subCate.category.id = :cateID AND subCate.category.isDeleted=1)")
    public List<SubCategory> findSubCategoryByCategoryID(int cateID);

    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.isDeleted=1 AND (subCate.category.slug_title = :cateSlugTitle AND subCate.category.isDeleted=1)")
    public List<SubCategory> findSubCategoryByCategorySlugTitle(String cateSlugTitle);

    @Query("SELECT subCate FROM SubCategory subCate WHERE subCate.isDeleted=1")
    public List<SubCategory> findAllSubCategories();

    // Search
    public List<SubCategory> findByNameContainingIgnoreCaseAndIsDeleted(String name,int isDeleted);

    @Query("SELECT subcategory.slug_title FROM SubCategory subcategory WHERE subcategory.isDeleted=1 AND subcategory.slug_title = :slugTitle")
    public String checkSlugTitles(String slugTitle);
}
