package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.Product;
import com.example.grocery_shop_backend.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController
{
    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable int id)
    {
        return productService.getProductById(id);
    }

    @GetMapping("/product/title/{slugTitle}")
    public Product getProductBySlugTitle(@PathVariable String slugTitle)
    {
        return productService.getProductBySlugTitle(slugTitle);
    }

    @GetMapping("/products/category/{categoryId}")
    public List<Product> getProductByCategoryId(@PathVariable int categoryId)
    {
        return productService.getProductByCatgeoryId(categoryId);
    }

    @GetMapping("/products/category/title/{categorySlugTitle}")
    public List<Product> getProductByCategorySlugTitle(@PathVariable String categorySlugTitle)
    {
        return productService.getProductByCategorySlugTitle(categorySlugTitle);
    }

    @GetMapping("/products/subcategory/{subCategoryId}")
    public List<Product> getProductBySubCategoryId(@PathVariable("subCategoryId") int subCategoryId) {
        return productService.getProductBySubCategoryId(subCategoryId);
    }

    @GetMapping("/products/subcategory/title/{subCategorySlugTitle}")
    public List<Product> getProductBySubCategorySlugTitle(@PathVariable("subCategorySlugTitle") String subCategorySlugTitle) {
        return productService.getProductBySubCategorySlugTitle(subCategorySlugTitle);
    }


    @GetMapping("/products/brand/{brandId}")
    public List<Product> getProductByBrandId(@PathVariable int brandId)
    {
        return productService.getProductByBrandId(brandId);
    }

    @GetMapping("/products/brand/title/{brandSlugTitle}")
    public List<Product> getProductByBrandSlugTitle(@PathVariable String brandSlugTitle)
    {
        return productService.getProductByBrandSlugTitle(brandSlugTitle);
    }

}
