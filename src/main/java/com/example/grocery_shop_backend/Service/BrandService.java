package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Entities.Brand;
import com.example.grocery_shop_backend.Repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService
{
    @Autowired
    BrandRepository brandRepository;

    public List<Brand> findAllBrands()
    {
        return brandRepository.findAll();
    }

    public Brand findBrandById(int id)
    {
        return brandRepository.findBrandById(id);
    }

    public Brand findBrandBySlugTitle(String slugTitle)
    {
        return brandRepository.findBrandBySlugTitle(slugTitle);
    }
}
