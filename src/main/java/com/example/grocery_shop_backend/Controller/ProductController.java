package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.ProductDTO;
import com.example.grocery_shop_backend.Entities.Product;
import com.example.grocery_shop_backend.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
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
    public ResponseEntity<Product> getProductById(@PathVariable int id)
    {
        try{
            return ResponseEntity.ok(productService.getProductById(id));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find Product by Slug title}
    @GetMapping("/product-title/{slugTitle}")
    public ResponseEntity<Product> getProductBySlugTitle(@PathVariable String slugTitle)
    {
        try{
            return ResponseEntity.ok(productService.getProductBySlugTitle(slugTitle));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Category ID}
    @GetMapping("/products-category/{categoryId}")
    public ResponseEntity<List<Product>> getProductByCategoryId(@PathVariable int categoryId)
    {
        try{
            return ResponseEntity.ok(productService.getProductByCatgeoryId(categoryId));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Category Slug title}
    @GetMapping("/products-category-title/{categorySlugTitle}")
    public ResponseEntity<List<Product>> getProductByCategorySlugTitle(@PathVariable String categorySlugTitle)
    {
        try{
            return ResponseEntity.ok(productService.getProductByCategorySlugTitle(categorySlugTitle));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Subcategory ID}
    @GetMapping("/products-subcategory/{subCategoryId}")
    public ResponseEntity<List<Product>> getProductBySubCategoryId(@PathVariable("subCategoryId") int subCategoryId) {
        try{
            return ResponseEntity.ok(productService.getProductBySubCategoryId(subCategoryId));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Subcategory Slug title}
    @GetMapping("/products-subcategory-title/{subCategorySlugTitle}")
    public ResponseEntity<List<Product>> getProductBySubCategorySlugTitle(@PathVariable("subCategorySlugTitle") String subCategorySlugTitle) {
        try{
            return ResponseEntity.ok(productService.getProductBySubCategorySlugTitle(subCategorySlugTitle));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Brand ID}
    @GetMapping("/products-brand/{brandId}")
    public ResponseEntity<List<Product>> getProductByBrandId(@PathVariable int brandId)
    {
        try{
            return ResponseEntity.ok(productService.getProductByBrandId(brandId));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Brand Slug title}
    @GetMapping("/products-brand-title/{brandSlugTitle}")
    public ResponseEntity<List<Product>> getProductByBrandSlugTitle(@PathVariable String brandSlugTitle)
    {
        try{
            return ResponseEntity.ok(productService.getProductByBrandSlugTitle(brandSlugTitle));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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


    //GET API {Find Product's Variants}
    @GetMapping("/product-variants")
    public ResponseEntity<List<Product>> getProductVariants(@RequestParam String name) {
        try{
            return ResponseEntity.ok(productService.getProductVariants(name));
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by MRP Ascending Order - Subcategory}
    @GetMapping("/product-ascending-sub-mrp/{slugTitle}")
    public ResponseEntity<List<Product>> getProductAscendingMrp(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsByAscendingMRP(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by MRP Descending Order - Subcategory}
    @GetMapping("/product-descending-sub-mrp/{slugTitle}")
    public ResponseEntity<List<Product>> getProductDescendingMrp(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsByDescendingMRP(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Name Ascending Order - Subcategory}
    @GetMapping("/product-ascending-sub-name/{slugTitle}")
    public ResponseEntity<List<Product>> getProductAscendingName(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsAscendingName(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Name Descending Order - Subcategory}
    @GetMapping("/product-descending-sub-name/{slugTitle}")
    public ResponseEntity<List<Product>> getProductDescendingName(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsDescendingName(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Discount Ascending Order - Subcategory}
    @GetMapping("/product-ascending-sub-discount/{slugTitle}")
    public ResponseEntity<List<Product>> getProductAscendingDiscount(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsAscendingDiscount(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Discount Descending Order - Subcategory}
    @GetMapping("/product-descending-sub-discount/{slugTitle}")
    public ResponseEntity<List<Product>> getProductDescendingDiscount(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsDescendingDiscount(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ======================= SORTING BY BRAND =======================

    // GET API {Find All Products by MRP Ascending Order - Brand}
    @GetMapping("/product-ascending-brand-mrp/{slugTitle}")
    public ResponseEntity<List<Product>> getProductByBrandAscendingMrp(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsByBrandAscendingMRP(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by MRP Descending Order - Brand}
    @GetMapping("/product-descending-brand-mrp/{slugTitle}")
    public ResponseEntity<List<Product>> getProductByBrandDescendingMrp(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsByBrandDescendingMRP(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Name Ascending Order - Brand}
    @GetMapping("/product-ascending-brand-name/{slugTitle}")
    public ResponseEntity<List<Product>> getProductByBrandAscendingName(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsByBrandAscendingName(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Name Descending Order - Brand}
    @GetMapping("/product-descending-brand-name/{slugTitle}")
    public ResponseEntity<List<Product>> getProductByBrandDescendingName(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsByBrandDescendingName(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Discount Ascending Order - Brand}
    @GetMapping("/product-ascending-brand-discount/{slugTitle}")
    public ResponseEntity<List<Product>> getProductByBrandAscendingDiscount(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsByBrandAscendingDiscount(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Products by Discount Descending Order - Brand}
    @GetMapping("/product-descending-brand-discount/{slugTitle}")
    public ResponseEntity<List<Product>> getProductByBrandDescendingDiscount(@PathVariable String slugTitle) {
        try {
            return ResponseEntity.ok(productService.findAllProductsByBrandDescendingDiscount(slugTitle));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Search Products} [Only names]
//    @GetMapping("/search")
//    public ResponseEntity<List<String>> searchProducts(@RequestParam String query) {
//        List<Product> products = productService.searchProducts(query);
//        List<String> names = products.stream()
//                .map(Product::getName)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(names);
//    }

    // GET API {Search Products}
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        try{
            List<Product> products = productService.searchProducts(query);
            return ResponseEntity.ok(products);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
