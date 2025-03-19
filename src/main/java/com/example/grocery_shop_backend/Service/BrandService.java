package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.BrandDTO;
import com.example.grocery_shop_backend.Entities.Brand;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.BrandRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BrandService
{
    @Autowired
    BrandRepository brandRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    // Find All Brands Service
    public List<Brand> findAllBrands()
    {
        return brandRepository.findAllBrands();
    }

    // Find Brand by ID Service
    public Brand findBrandById(int id)
    {
        return brandRepository.findBrandById(id);
    }

    // Find Brand by Slug Title Service
    public Brand findBrandBySlugTitle(String slugTitle)
    {
        return brandRepository.findBrandBySlugTitle(slugTitle);
    }

    // Add New Brand Service
    public void addBrand(BrandDTO brandDTO) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brand.setSlug_title(brandDTO.getSlugTitle());
        brand.setDescription(brandDTO.getDescription());
        brand.setIs_deleted(1);
        brand.setC_date(cDate);

        MultipartFile imageFile = brandDTO.getImageFile();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Delete old image if present
        String oldImageName = brand.getImage_url();
        if (oldImageName != null && !oldImageName.isEmpty()) {
            Path oldImagePath = uploadPath.resolve(oldImageName);
            if (Files.exists(oldImagePath)) {
                Files.delete(oldImagePath);
            }
        }

        // Save new image with brand name (force jpg if needed)
        String fileName = brandDTO.getName();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        brand.setImage_url(brandDTO.getName());

        brandRepository.save(brand);
    }

    // Update Brand Service
    @Transactional
    public Brand updateBrand(int brandId, BrandDTO brandDTO) throws IOException {
        Brand brand = brandRepository.findBrandById(brandId);

        if (brand != null) {
            if (brandDTO.getName() != null && !brandDTO.getName().isEmpty()) {
                brand.setName(brandDTO.getName());
            }

            if(brandDTO.getSlugTitle() != null && !brandDTO.getSlugTitle().isEmpty()) {
                brand.setSlug_title(brandDTO.getSlugTitle());
            }

            brand.setDescription(brandDTO.getDescription());

            MultipartFile imageFile = brandDTO.getImageFile();
            if (imageFile != null && !imageFile.isEmpty()) {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Delete old image if present
                String oldImageName = brand.getImage_url();
                if (oldImageName != null && !oldImageName.isEmpty()) {
                    Path oldImagePath = uploadPath.resolve(oldImageName);
                    if (Files.exists(oldImagePath)) {
                        Files.delete(oldImagePath);
                    }
                }

                // Save new image with brand name (force jpg if needed)
                String fileName = brandDTO.getName();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                brand.setImage_url(brandDTO.getName());
            }

            return brandRepository.save(brand);
        } else {
            throw new objectNotFoundException("Brand with ID " + brandId + " not found");
        }
    }



    // Delete Brand Service
    public void deleteBrand(int brandId)
    {
        Brand brand = brandRepository.findBrandById(brandId);

        if(brand!=null)
        {
            brand.setIs_deleted(2);
            brandRepository.save(brand);
        }
        else
            throw new objectNotFoundException("Brand with ID " + brandId + " not found");
    }

    // Retrieve Brand Service
    public boolean retrieveBrand(int brandId)
    {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new objectNotFoundException("Brand with ID " + brandId + " not found"));

        if(brand.getIs_deleted()==1)
            return false;
        else
        {
            brand.setIs_deleted(1);
            brandRepository.save(brand);
            return true;
        }
    }

    // Search Brand Service
    public List<Brand> searchBrand(String keyword){
        List<Brand> brands = brandRepository.findByNameContainingIgnoreCase(keyword);

        if(brands.isEmpty()){
            throw new objectNotFoundException("Brand with keyword " + keyword + " not found");
        }
        return brands;
    }

    // Check Slug Title Service
    public boolean checkSlugTitles(String slugTitle){
        String slug_title = brandRepository.checkSlugTitles(slugTitle);
        if(slug_title != null && !slug_title.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
