package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.BrandDTO;
import com.example.grocery_shop_backend.Entities.Brand;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BrandService
{
    @Autowired
    BrandRepository brandRepository;

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
    public void addBrand(BrandDTO brandDTO)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brand.setImage_url(brandDTO.getImage());
        brand.setSlug_title(brandDTO.getSlugTitle());
        brand.setDescription(brandDTO.getDescription());
        brand.setIs_deleted(1);
        brand.setC_date(cDate);

        brandRepository.save(brand);
    }

    // Update Brand Service
    public Brand updateBrand(int brandId,BrandDTO brandDTO)
    {
        Brand brand = brandRepository.findBrandById(brandId);

        if(brand!=null)
        {
            if(brandDTO.getName()!=null)
                brand.setName(brandDTO.getName());
            if (brandDTO.getImage()!=null)
                brand.setImage_url(brandDTO.getImage());
            if (brandDTO.getSlugTitle()!=null)
                brand.setSlug_title(brandDTO.getSlugTitle());

            brandRepository.save(brand);
        }
        else
            throw new objectNotFoundException("Brand with ID " + brandId + " not found");

        return brand;
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
}
