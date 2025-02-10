package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Product;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>
{
    @Query("SELECT product FROM Product product WHERE product.id = :id AND product.is_deleted=1")
    Product findProductById(int id);

    @Query("SELECT product FROM Product product WHERE product.slug_title = :slugTitle AND product.is_deleted=1")
    Product findProductBySlugTitle(String slugTitle);

    @Query("SELECT product FROM Product product WHERE product.cat.id = :categoryId AND product.is_deleted=1")
    List<Product> findProductsByCategoryId(int categoryId);

    @Query("SELECT product FROM Product product WHERE product.is_deleted=1 AND (product.cat.slug_title = :categorySlugTitle AND product.cat.is_deleted=1)")
    List<Product> findProductsByCategorySlugTitle(String categorySlugTitle);

    @Query("SELECT product FROM Product product WHERE product.is_deleted=1 AND (product.subcat.id = :subCategoryId AND product.subcat.is_deleted=1)")
    List<Product> findProductsBySubCategoryId(int subCategoryId);

    @Query("SELECT product FROM Product product WHERE product.is_deleted=1 AND (product.subcat.slug_title = :subCategorySlugTitle AND product.is_deleted=1)")
    List<Product> findProductsBySubCategorySlugTitle(String subCategorySlugTitle);

    @Query("SELECT product FROM Product product WHERE product.is_deleted=1 AND (product.brand.id = :brandId AND product.brand.is_deleted=1)")
    List<Product> findProductsByBrandId(int brandId);

    @Query("SELECT product FROM Product product WHERE product.is_deleted=1 AND (product.brand.slug_title = :brandSlugTitle AND product.is_deleted=1)")
    List<Product> findProductsByBrandSlugTitle(String brandSlugTitle);

    @Query("SELECT p1 FROM Product p1 WHERE p1.referenceCode IN " +
            "(SELECT p2.referenceCode FROM Product p2 WHERE p2.name = :name)")
    List<Product> findVariantsByProductName(@Param("name") String name);

}
