package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.Brand;
import com.example.grocery_shop_backend.Service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrandController
{
    @Autowired
    BrandService brandService;

    @GetMapping("/brand")
    public List<Brand> getAllBrands()
    {
        return brandService.findAllBrands();
    }

    @GetMapping("/brand-slug/{slugTitle}")
    public Brand getBrandBySlugTitle(@PathVariable String slugTitle)
    {
        return brandService.findBrandBySlugTitle(slugTitle);
    }

    @GetMapping("/brand-id/{id}")
    public Brand getBrandById(@PathVariable int id)
    {
        return brandService.findBrandById(id);
    }
}
