package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.ProductDTO;
import com.example.grocery_shop_backend.Entities.Brand;
import com.example.grocery_shop_backend.Entities.Category;
import com.example.grocery_shop_backend.Entities.Product;
import com.example.grocery_shop_backend.Entities.SubCategory;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.BrandRepository;
import com.example.grocery_shop_backend.Repository.CategoryRepository;
import com.example.grocery_shop_backend.Repository.ProductRepository;
import com.example.grocery_shop_backend.Repository.SubCategoryRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    // Find All Products Service
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    // Find Product by ID Service
    public Product getProductById(int productId)
    {
        Product product = productRepository.findProductById(productId);
        if(product == null)
        {
            throw new RuntimeException("Product with id " + productId + " not found");
        }
        return product;
    }

    // Find Product by Slug title Service
    public Product getProductBySlugTitle(String slugTitle)
    {
        Product product = productRepository.findProductBySlugTitle(slugTitle);
        if(product == null)
        {
            throw new objectNotFoundException("Product with title " + slugTitle + " not found");
        }
        return product;
    }

    // Find All products by Category ID Service
    public List<Product> getProductByCatgeoryId(int categoryId)
    {
        List<Product> products = productRepository.findProductsByCategoryId(categoryId);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with category id " + categoryId + " not found");
        }
        return products;
    }

    // Find All Products by Category Slug title Service
    public List<Product> getProductByCategorySlugTitle(String categorySlugTitle)
    {
        List<Product> products = productRepository.findProductsByCategorySlugTitle(categorySlugTitle);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with category slug title " + categorySlugTitle + " not found");
        }
        return products;
    }

    // Find All Products by Brand ID Service
    public List<Product> getProductByBrandId(int brandId)
    {
        List<Product> products = productRepository.findProductsByBrandId(brandId);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with brand id " + brandId + " not found");
        }
        return products;
    }

    // Find All Products by Brand Slug title Service
    public List<Product> getProductByBrandSlugTitle(String brandSlugTitle)
    {
        List<Product> products = productRepository.findProductsByBrandSlugTitle(brandSlugTitle);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with brand slug title " + brandSlugTitle + " not found");
        }
        return products;
    }

    // Find All Products by Subcategory Slug title Service
    public List<Product> getProductBySubCategorySlugTitle(String subCategorySlugTitle)
    {
        List<Product> products = productRepository.findProductsBySubCategorySlugTitle(subCategorySlugTitle);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with sub category slug title " + subCategorySlugTitle + " not found");
        }
        return products;
    }

    // Find All Products by Subcategory ID Service
    public List<Product> getProductBySubCategoryId(int subCategoryId)
    {
        List<Product> products = productRepository.findProductsBySubCategoryId(subCategoryId);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with sub category id " + subCategoryId + " not found");
        }
        return products;
    }

    // Add New Product Service
    public void addNewProduct(ProductDTO productDTO)
    {
        Category category = categoryRepository.findCategoryById(productDTO.getCategoryId());
        SubCategory subCategory = subCategoryRepository.findSubCategoryById(productDTO.getSubcategoryId());
        Brand brand = brandRepository.findBrandById(productDTO.getBrandId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        if(category!=null && subCategory!=null && brand!=null)
        {
            Product product = new Product();
            product.setCat(category);
            product.setSubcat(subCategory);
            product.setBrand(brand);
            product.setName(productDTO.getName());
            product.setVariantName(productDTO.getVariantName());
            product.setDescription(productDTO.getDescription());
            product.setImage_url(productDTO.getImageUrl());
            product.setBasePrice(productDTO.getBasePrice());
            product.setIsInclusiveTax(productDTO.getIsInclusiveTax());
            product.setCgst(productDTO.getCgst());
            product.setSgst(productDTO.getSgst());
            product.setIgst(productDTO.getIgst());
            product.setMrp(productDTO.getMrp());
            product.setDiscount_amt(productDTO.getDiscountPrice());
            product.setWholesaler_amt(productDTO.getWholesalePrice());
            product.setIs_main(productDTO.getIsMain());
            product.setSlug_title(productDTO.getSlugTitle());
            product.setIs_deleted(1);
            product.setC_date(cDate);
            productRepository.save(product);
        }
        else
            throw new objectNotFoundException("Any of Category or SubCategory or Brand or All not Found");
    }

    // Update Product Service
    public Product updateProduct(int productId, ProductDTO productDTO)
    {
        Product product = productRepository.findProductById(productId);

        if(product!=null)
        {
            if(productDTO.getName()!=null)
                product.setName(productDTO.getName());
            if (productDTO.getVariantName()!=null)
                product.setVariantName(productDTO.getVariantName());
            if(productDTO.getDescription()!=null)
                product.setDescription(productDTO.getDescription());
            if(productDTO.getImageUrl()!=null)
                product.setImage_url(productDTO.getImageUrl());
            if(productDTO.getBasePrice()!=0)
                product.setBasePrice(productDTO.getBasePrice());
            if(productDTO.getIsInclusiveTax()!=0)
                product.setIsInclusiveTax(productDTO.getIsInclusiveTax());
            if(productDTO.getCgst()!=0)
                product.setCgst(productDTO.getCgst());
            if(productDTO.getSgst()!=0)
                product.setSgst(productDTO.getSgst());
            if(productDTO.getIgst()!=0)
                product.setIgst(productDTO.getIgst());
            if(productDTO.getMrp()!=0)
                product.setMrp(productDTO.getMrp());
            if(productDTO.getDiscountPrice()!=0)
                product.setDiscount_amt(productDTO.getDiscountPrice());
            if(productDTO.getWholesalePrice()!=0)
                product.setWholesaler_amt(productDTO.getWholesalePrice());
            if(productDTO.getIsMain()!=0)
                product.setIs_main(productDTO.getIsMain());
            if(productDTO.getSlugTitle()!=null)
                product.setSlug_title(productDTO.getSlugTitle());
            if(productDTO.getCategoryId()!=0)
            {
                Category category = categoryRepository.findCategoryById(productDTO.getCategoryId());
                if(category!=null)
                    product.setCat(category);
                else
                    throw new objectNotFoundException("Category with id " + productDTO.getCategoryId() + " not found");
            }
            if(productDTO.getBrandId()!=0)
            {
                Brand brand = brandRepository.findBrandById(productDTO.getBrandId());

                if(brand!=null)
                    product.setBrand(brand);
                else
                    throw new objectNotFoundException("Brand with id " + productDTO.getBrandId() + " not found");
            }
            if(productDTO.getSubcategoryId()!=0)
            {
                SubCategory subCategory = subCategoryRepository.findSubCategoryById(productDTO.getSubcategoryId());

                if(subCategory!=null)
                    product.setSubcat(subCategory);
                else
                    throw new objectNotFoundException("Subcategory with id " + productDTO.getSubcategoryId() + " not found");
            }

            return productRepository.save(product);
        }
        else
            throw new objectNotFoundException("Product with id " + productId + " not found");
    }

    // Delete product Service
    public void deleteProduct(int productId)
    {
        Product product = productRepository.findProductById(productId);

        if(product!=null)
        {
            product.setIs_deleted(2);
            productRepository.save(product);
        }
        else
            throw new objectNotFoundException("Product with id " + productId + " not found");
    }

    // Retrieve Product Service
    public boolean retrieveProduct(int productId)
    {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new objectNotFoundException("Product with id " + productId + " not found"));

        if(product.getIs_deleted()==2)
        {
            product.setIs_deleted(1);
            productRepository.save(product);
            return true;
        }
        else
            return false;
    }

    // Find Product's Variants
    public List<Product> getProductVariants(String productName) {
//        System.out.println("Name "+productName);
        return productRepository.findVariantsByProductName(productName);
    }

    // Find All Products by MRP Ascending Order
    public List<Product> findAllProductsByAscendingMRP(String slugTitle)
    {
        List<Product> products = productRepository.findAllProductsByAscendingMRP(slugTitle);

        if(products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");
        return products;
    }

    // Find All Products by MRP Descending Order
    public List<Product> findAllProductsByDescendingMRP(String slugTitle)
    {
        List<Product> products = productRepository.findAllProductsByDescendingMRP(slugTitle);

        if(products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");
        return products;
    }

    // Find All Products by Name Ascending Order
    public List<Product> findAllProductsAscendingName(String slugTitle)
    {
        List<Product> products = productRepository.findAllProductsByAscendingName(slugTitle);

        if(products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");
        return products;
    }

    // Find All Products by Name Descending Order
    public List<Product> findAllProductsDescendingName(String slugTitle)
    {
        List<Product> products = productRepository.findAllProductsByDescendingName(slugTitle);

        if(products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");
        return products;
    }

    // Find All Products by Discount Ascending Order
    public List<Product> findAllProductsAscendingDiscount(String slugTitle)
    {
        List<Product> products = productRepository.findAllProductsByAscendingDiscount(slugTitle);

        if(products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");
        return products;
    }

    // Find All Products by Discount Descending Order
    public List<Product> findAllProductsDescendingDiscount(String slugTitle)
    {
        List<Product> products = productRepository.findAllProductsByDescendingDiscount(slugTitle);

        if(products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");
        return products;
    }

    // Find All Products by MRP Ascending Order (Brand)
    public List<Product> findAllProductsByBrandAscendingMRP(String slugTitle) {
        List<Product> products = productRepository.findAllProductsByBrandAscendingMRP(slugTitle);

        if (products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");

        return products;
    }

    // Find All Products by MRP Descending Order (Brand)
    public List<Product> findAllProductsByBrandDescendingMRP(String slugTitle) {
        List<Product> products = productRepository.findAllProductsByBrandDescendingMRP(slugTitle);

        if (products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");

        return products;
    }

    // Find All Products by Name Ascending Order (Brand)
    public List<Product> findAllProductsByBrandAscendingName(String slugTitle) {
        List<Product> products = productRepository.findAllProductsByBrandAscendingName(slugTitle);

        if (products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");

        return products;
    }

    // Find All Products by Name Descending Order (Brand)
    public List<Product> findAllProductsByBrandDescendingName(String slugTitle) {
        List<Product> products = productRepository.findAllProductsByBrandDescendingName(slugTitle);

        if (products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");

        return products;
    }

    // Find All Products by Discount Ascending Order (Brand)
    public List<Product> findAllProductsByBrandAscendingDiscount(String slugTitle) {
        List<Product> products = productRepository.findAllProductsByBrandAscendingDiscount(slugTitle);

        if (products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");

        return products;
    }

    // Find All Products by Discount Descending Order (Brand)
    public List<Product> findAllProductsByBrandDescendingDiscount(String slugTitle) {
        List<Product> products = productRepository.findAllProductsByBrandDescendingDiscount(slugTitle);

        if (products.isEmpty())
            throw new objectNotFoundException("Product with name " + slugTitle + " not found");

        return products;
    }
}