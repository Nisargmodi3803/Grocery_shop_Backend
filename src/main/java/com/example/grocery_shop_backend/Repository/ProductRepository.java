package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.CouponCode;
import com.example.grocery_shop_backend.Entities.Product;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("SELECT product FROM Product product WHERE product.id = :id AND product.isDeleted=1")
    Product findProductById(int id);

    @Query("SELECT product FROM Product product WHERE product.slug_title = :slugTitle AND product.isDeleted=1")
    Product findProductBySlugTitle(String slugTitle);

    @Query("SELECT product FROM Product product WHERE product.cat.id = :categoryId AND product.isDeleted=1")
    List<Product> findProductsByCategoryId(int categoryId);

    @Query("SELECT product FROM Product product WHERE product.isDeleted=1 AND (product.cat.slug_title = :categorySlugTitle AND product.cat.isDeleted=1)")
    List<Product> findProductsByCategorySlugTitle(String categorySlugTitle);

    @Query("SELECT product FROM Product product WHERE product.isDeleted=1 AND (product.subcat.id = :subCategoryId AND product.subcat.isDeleted=1)")
    List<Product> findProductsBySubCategoryId(int subCategoryId);

    @Query("SELECT product FROM Product product WHERE product.isDeleted=1 AND (product.subcat.slug_title = :subCategorySlugTitle AND product.isDeleted=1)")
    List<Product> findProductsBySubCategorySlugTitle(String subCategorySlugTitle);

    @Query("SELECT product FROM Product product WHERE product.isDeleted=1 AND (product.brand.id = :brandId AND product.brand.isDeleted=1)")
    List<Product> findProductsByBrandId(int brandId);

    @Query("SELECT product FROM Product product WHERE product.isDeleted=1 AND (product.brand.slug_title = :brandSlugTitle AND product.isDeleted=1)")
    List<Product> findProductsByBrandSlugTitle(String brandSlugTitle);

    @Query("SELECT p1 FROM Product p1 WHERE p1.referenceCode IN " +
            "(SELECT p2.referenceCode FROM Product p2 WHERE p2.name = :name)")
    List<Product> findVariantsByProductName(@Param("name") String name);

    @Query("SELECT product FROM Product product WHERE product.subcat.slug_title = :slugTitle AND product.isDeleted=1 ORDER BY product.mrp ASC")
    List<Product> findAllProductsByAscendingMRP(@Param("slugTitle") String slugTitle);

    @Query("SELECT product FROM Product product WHERE product.subcat.slug_title = :slugTitle AND product.isDeleted=1 ORDER BY product.mrp DESC ")
    List<Product> findAllProductsByDescendingMRP(@Param("slugTitle") String slugTitle);

    @Query("SELECT product FROM Product product WHERE product.subcat.slug_title = :slugTitle AND product.isDeleted=1 ORDER BY product.name ASC")
    List<Product> findAllProductsByAscendingName(@Param("slugTitle") String slugTitle);

    @Query("SELECT product FROM Product product WHERE product.subcat.slug_title = :slugTitle AND product.isDeleted=1 ORDER BY product.name DESC")
    List<Product> findAllProductsByDescendingName(@Param("slugTitle") String slugTitle);

    @Query("SELECT product FROM Product product WHERE product.subcat.slug_title = :slugTitle AND product.isDeleted=1 ORDER BY ((product.mrp - product.discount_amt)/product.mrp) ASC")
    List<Product> findAllProductsByAscendingDiscount(@Param("slugTile") String slugTitle);

    @Query("SELECT product FROM Product product WHERE product.subcat.slug_title = :slugTitle AND product.isDeleted=1 ORDER BY ((product.mrp - product.discount_amt)/product.mrp) DESC")
    List<Product> findAllProductsByDescendingDiscount(@Param("slugTitle") String slugTitle);

    // Sort by MRP (Low to High)
    @Query("SELECT product FROM Product product WHERE product.brand.slug_title = :slugTitle AND product.isDeleted = 1  ORDER BY product.mrp ASC")
    List<Product> findAllProductsByBrandAscendingMRP(@Param("slugTitle") String slugTitle);

    // Sort by MRP (High to Low)
    @Query("SELECT product FROM Product product WHERE product.brand.slug_title = :slugTitle AND product.isDeleted = 1 ORDER BY product.mrp DESC")
    List<Product> findAllProductsByBrandDescendingMRP(@Param("slugTitle") String slugTitle);

    // Sort by Name (A to Z)
    @Query("SELECT product FROM Product product WHERE product.brand.slug_title = :slugTitle AND product.isDeleted = 1 ORDER BY product.name ASC")
    List<Product> findAllProductsByBrandAscendingName(@Param("slugTitle") String slugTitle);

    // Sort by Name (Z to A)
    @Query("SELECT product FROM Product product WHERE product.brand.slug_title = :slugTitle AND product.isDeleted = 1 ORDER BY product.name DESC")
    List<Product> findAllProductsByBrandDescendingName(@Param("slugTitle") String slugTitle);

    // Sort by Discount (Low to High)
    @Query("SELECT product FROM Product product WHERE product.brand.slug_title = :slugTitle AND product.isDeleted = 1 ORDER BY ((product.mrp - product.discount_amt) / product.mrp) ASC")
    List<Product> findAllProductsByBrandAscendingDiscount(@Param("slugTitle") String slugTitle);

    // Sort by Discount (High to Low)
    @Query("SELECT product FROM Product product WHERE product.brand.slug_title = :slugTitle AND product.isDeleted = 1 ORDER BY ((product.mrp - product.discount_amt) / product.mrp) DESC")
    List<Product> findAllProductsByBrandDescendingDiscount(@Param("slugTitle") String slugTitle);

    // Search Products
    List<Product> findByNameContainingIgnoreCaseAndIsDeleted(String name, int isDeleted);

    @Query("SELECT product FROM Product product WHERE product.isDeleted=1 ORDER BY product.id DESC ")
    List<Product> findAllProducts();

    @Query("SELECT c FROM Product c " +
            "WHERE c.isDeleted = 1 AND (" +
            "LOWER(c.referenceCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.variantName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.brand.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.cat.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.subcat.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(c.basePrice AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "CAST(c.mrp AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "CAST(c.discount_amt AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "CAST(c.wholesaler_amt AS string) LIKE CONCAT('%', :keyword, '%')" +
            ")")
    List<Product> searchProductByKeyword(@Param("keyword") String keyword);

    @Query("SELECT product.slug_title FROM Product product WHERE product.slug_title= :slugTitle AND product.isDeleted=1")
    String checkSlugTitle(@Param("slugTitle") String slugTitle);

    @Query("SELECT product.referenceCode FROM Product product WHERE product.referenceCode= :referenceCode AND product.isDeleted=1")
    String getReferenceCode(String referenceCode);

    @Query("SELECT COUNT(product) FROM Product product WHERE product.isDeleted=1")
    int getProductCount();
}
