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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @Value("${upload.dir}")
    private String uploadDir;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
    private static final SecureRandom random = new SecureRandom();

    public String generateUniqueReferenceCode(){
        String code;

        do{
            code = generateReferenceCode();
        }while (productRepository.getReferenceCode(code) == code);

        return code;
    }

    public String generateReferenceCode(){
        StringBuilder referenceCode = new StringBuilder(8);
        for(int i = 0; i < 20; i++){
            referenceCode.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return referenceCode.toString();
    }


    // Find All Products Service
    public List<Product> getAllProducts()
    {
        List<Product> products = productRepository.findAllProducts();
        if(products.isEmpty())
            throw new objectNotFoundException("No products found");
        return products;
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
    public void addNewProduct(ProductDTO productDTO) throws IOException {
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
            product.setLong_description(productDTO.getLongDescription());
            product.setBasePrice(productDTO.getBasePrice());
            product.setIsInclusiveTax(2);
            product.setCgst(productDTO.getCgst());
            product.setSgst(productDTO.getSgst());
            product.setIgst(productDTO.getIgst());
            product.setMrp(productDTO.getMrp());
            product.setDiscount_amt(productDTO.getDiscountPrice());
            product.setWholesaler_amt(productDTO.getWholesalePrice());
            product.setIs_main(productDTO.getIsMain());
            product.setSlug_title(productDTO.getSlugTitle());
            product.setProductIsActive(productDTO.getStatus());
            product.setIs_deleted(1);
            product.setC_date(cDate);

            String referenceCode = generateUniqueReferenceCode();
            product.setReferenceCode(referenceCode);

            MultipartFile imageFile = productDTO.getImageFile();
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Delete old image if present
            String oldImageName = product.getImage_url();
            if (oldImageName != null && !oldImageName.isEmpty()) {
                Path oldImagePath = uploadPath.resolve(oldImageName);
                if (Files.exists(oldImagePath)) {
                    Files.delete(oldImagePath);
                }
            }

            // Save new image with brand name (force jpg if needed)
            String fileName = productDTO.getName();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            product.setImage_url(product.getName());
            productRepository.save(product);
        }
        else
            throw new objectNotFoundException("Any of Category or SubCategory or Brand or All not Found");
    }

    // Duplicate Product Service
    public void duplicateProduct(int productID,ProductDTO productDTO) throws IOException {
        Product existingProduct = productRepository.findProductById(productID);
        System.out.println("Product Id: " + productID);
        if(existingProduct==null)
            throw new objectNotFoundException("Product with id " + productID + " not found");

        System.out.println("Product Name: " + existingProduct.getName());
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
            product.setLong_description(productDTO.getLongDescription());
            product.setBasePrice(productDTO.getBasePrice());
            product.setIsInclusiveTax(2);
            product.setCgst(productDTO.getCgst());
            product.setSgst(productDTO.getSgst());
            product.setIgst(productDTO.getIgst());
            product.setMrp(productDTO.getMrp());
            product.setDiscount_amt(productDTO.getDiscountPrice());
            product.setWholesaler_amt(productDTO.getWholesalePrice());
            product.setIs_main(productDTO.getIsMain());
            product.setSlug_title(productDTO.getSlugTitle());
            product.setProductIsActive(productDTO.getStatus());
            product.setIs_deleted(1);
            product.setC_date(cDate);

            product.setReferenceCode(existingProduct.getReferenceCode());

            MultipartFile imageFile = productDTO.getImageFile();
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            if (imageFile != null && !imageFile.isEmpty()) {
                // Save new image with product name
                String fileName = productDTO.getName();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                product.setImage_url(fileName);
            } else if (productDTO.getExistingImageUrl() != null && !productDTO.getExistingImageUrl().isEmpty()) {
                // Use existing image from URL
                String existingImageName = productDTO.getExistingImageUrl().replace("http://localhost:9000/uploads/", "");
                product.setImage_url(existingImageName);
            } else {
                // Fallback to existing product image
                product.setImage_url(existingProduct.getImage_url());
            }
            productRepository.save(product);
        }
        else
            throw new objectNotFoundException("Any of Category or SubCategory or Brand or All not Found");
    }

    // Update Product Service
    public void updateProduct(int productId, ProductDTO productDTO) throws IOException {
        Product product = productRepository.findProductById(productId);

        if(product!=null)
        {
            if(productDTO.getName()!=null)
                product.setName(productDTO.getName());
            if (productDTO.getVariantName()!=null)
                product.setVariantName(productDTO.getVariantName());
            if(productDTO.getDescription()!=null)
                product.setDescription(productDTO.getDescription());
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
            if(productDTO.getStatus()!=0)
                product.setProductIsActive(productDTO.getStatus());
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

            MultipartFile imageFile = productDTO.getImageFile();
            if (imageFile != null && !imageFile.isEmpty()) {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Delete old image if present
                String oldImageName = product.getImage_url();
                if (oldImageName != null && !oldImageName.isEmpty()) {
                    Path oldImagePath = uploadPath.resolve(oldImageName);
                    if (Files.exists(oldImagePath)) {
                        Files.delete(oldImagePath);
                    }
                }

                // Save new image with brand name (force jpg if needed)
                String fileName = productDTO.getName();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                product.setImage_url(product.getName());
            }

            productRepository.save(product);
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

    // Search Products Service
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCaseAndIsDeleted(query,1);
    }

    public List<Product> searchProductsbyKeywordForAdmin(String keyword) {
        List<Product> products = productRepository.searchProductByKeyword(keyword);
        if(products.isEmpty())
            throw new objectNotFoundException("Product with name " + keyword + " not found");
        return products;
    }

    // Check Slug Title Service
    public boolean checkSlugTitles(String slugTitle){
        String slug_title = productRepository.checkSlugTitle(slugTitle);
        if(slug_title != null && !slug_title.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    // Bulk Price update
    public void bulkPriceUpdate(List<Product> products) {
        for (Product product : products) {
            Product oldProduct = productRepository.findProductById(product.getId());
            oldProduct.setMrp(product.getMrp());
            oldProduct.setDiscount_amt(product.getDiscount_amt());
            oldProduct.setWholesaler_amt(product.getWholesaler_amt());
            productRepository.save(oldProduct);
        }
    }
}