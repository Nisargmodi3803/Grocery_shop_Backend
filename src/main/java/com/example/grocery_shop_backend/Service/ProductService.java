package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Entities.Product;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    public Product getProductById(int productId)
    {
        Product product = productRepository.findProductById(productId);
        if(product == null)
        {
            throw new RuntimeException("Product with id " + productId + " not found");
        }
        return product;
    }

    public Product getProductBySlugTitle(String slugTitle)
    {
        Product product = productRepository.findProductBySlugTitle(slugTitle);
        if(product == null)
        {
            throw new objectNotFoundException("Product with title " + slugTitle + " not found");
        }
        return product;
    }

    public List<Product> getProductByCatgeoryId(int categoryId)
    {
        List<Product> products = productRepository.findProductsByCategoryId(categoryId);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with category id " + categoryId + " not found");
        }
        return products;
    }

    public List<Product> getProductByCategorySlugTitle(String categorySlugTitle)
    {
        List<Product> products = productRepository.findProductsByCategorySlugTitle(categorySlugTitle);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with category slug title " + categorySlugTitle + " not found");
        }
        return products;
    }

    public List<Product> getProductByBrandId(int brandId)
    {
        List<Product> products = productRepository.findProductsByBrandId(brandId);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with brand id " + brandId + " not found");
        }
        return products;
    }

    public List<Product> getProductByBrandSlugTitle(String brandSlugTitle)
    {
        List<Product> products = productRepository.findProductsByBrandSlugTitle(brandSlugTitle);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with brand slug title " + brandSlugTitle + " not found");
        }
        return products;
    }

    public List<Product> getProductBySubCategorySlugTitle(String subCategorySlugTitle)
    {
        List<Product> products = productRepository.findProductsBySubCategorySlugTitle(subCategorySlugTitle);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with sub category slug title " + subCategorySlugTitle + " not found");
        }
        return products;
    }

    public List<Product> getProductBySubCategoryId(int subCategoryId)
    {
        List<Product> products = productRepository.findProductsBySubCategoryId(subCategoryId);
        if(products.isEmpty())
        {
            throw new objectNotFoundException("Product with sub category id " + subCategoryId + " not found");
        }
        return products;
    }
}
