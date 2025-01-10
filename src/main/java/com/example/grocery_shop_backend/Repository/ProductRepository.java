package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Product;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer>
{
    @Query("SELECT product FROM Product product WHERE product.id = :id")
    Product findProductById(int id);

    @Query("SELECT product FROM Product product WHERE product.slug_title = :slugTitle")
    Product findProductBySlugTitle(String slugTitle);

    @Query("SELECT product FROM Product product WHERE product.cat.id = :categoryId")
    List<Product> findProductsByCategoryId(int categoryId);

    @Query("SELECT product FROM Product product WHERE product.cat.slug_title = :categorySlugTitle")
    List<Product> findProductsByCategorySlugTitle(String categorySlugTitle);

    @Query("SELECT product FROM Product product WHERE product.subcat.id = :subCategoryId")
    List<Product> findProductsBySubCategoryId(int subCategoryId);

    @Query("SELECT product FROM Product product WHERE product.subcat.slug_title = :subCategorySlugTitle")
    List<Product> findProductsBySubCategorySlugTitle(String subCategorySlugTitle);

    @Query("SELECT product FROM Product product WHERE product.brand.id = :brandId")
    List<Product> findProductsByBrandId(int brandId);

    @Query("SELECT product FROM Product product WHERE product.brand.slug_title = :brandSlugTitle")
    List<Product> findProductsByBrandSlugTitle(String brandSlugTitle);
}
