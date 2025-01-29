package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.ProductDTO;
import com.example.grocery_shop_backend.Entities.Product;
import com.example.grocery_shop_backend.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController
{
    @Autowired
    ProductService productService;

    // GET API {Find All Products}
    @GetMapping("/products")
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }

    // GET API {Find Product by ID}
    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable int id)
    {
        return productService.getProductById(id);
    }

    // GET API {Find Product by Slug title}
    @GetMapping("/product-title/{slugTitle}")
    public Product getProductBySlugTitle(@PathVariable String slugTitle)
    {
        return productService.getProductBySlugTitle(slugTitle);
    }

    // GET API {Find All Products by Category ID}
    @GetMapping("/products-category/{categoryId}")
    public List<Product> getProductByCategoryId(@PathVariable int categoryId)
    {
        return productService.getProductByCatgeoryId(categoryId);
    }

    // GET API {Find All Products by Category Slug title}
    @GetMapping("/products-category-title/{categorySlugTitle}")
    public List<Product> getProductByCategorySlugTitle(@PathVariable String categorySlugTitle)
    {
        return productService.getProductByCategorySlugTitle(categorySlugTitle);
    }

    // GET API {Find All Products by Subcategory ID}
    @GetMapping("/products-subcategory/{subCategoryId}")
    public List<Product> getProductBySubCategoryId(@PathVariable("subCategoryId") int subCategoryId) {
        return productService.getProductBySubCategoryId(subCategoryId);
    }

    // GET API {Find All Products by Subcategory Slug title}
    @GetMapping("/products-subcategory-title/{subCategorySlugTitle}")
    public List<Product> getProductBySubCategorySlugTitle(@PathVariable("subCategorySlugTitle") String subCategorySlugTitle) {
        return productService.getProductBySubCategorySlugTitle(subCategorySlugTitle);
    }

    // GET API {Find All Products by Brand ID}
    @GetMapping("/products-brand/{brandId}")
    public List<Product> getProductByBrandId(@PathVariable int brandId)
    {
        return productService.getProductByBrandId(brandId);
    }

    // GET API {Find All Products by Brand Slug title}
    @GetMapping("/products-brand-title/{brandSlugTitle}")
    public List<Product> getProductByBrandSlugTitle(@PathVariable String brandSlugTitle)
    {
        return productService.getProductByBrandSlugTitle(brandSlugTitle);
    }

    // POST API {Add New Product}
    @PostMapping("/add-product")
    public ResponseEntity<String> addNewProduct(@RequestBody  ProductDTO productDTO)
    {
        try {
            productService.addNewProduct(productDTO);
            return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    // PATCH API {Update Product}
    @PatchMapping("/update-product/{productId}")
    public Product updateProduct(@PathVariable int productId, @RequestBody ProductDTO productDTO)
    {
        return productService.updateProduct(productId, productDTO);
    }

    // PATCH API {Delete Product}
    @PatchMapping("/delete-product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId)
    {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }

    // PATCH API {Retrieve Product}
    @PatchMapping("/retrieve-product/{productId}")
    public ResponseEntity<String> retrieveProduct(@PathVariable int productId)
    {
        boolean success = productService.retrieveProduct(productId);

        if(success)
            return new ResponseEntity<>("Product retrieved successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Product Already Present", HttpStatus.BAD_REQUEST);
    }
}
