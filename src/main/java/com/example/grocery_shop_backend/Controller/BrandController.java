package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.BrandDTO;
import com.example.grocery_shop_backend.Entities.Brand;
import com.example.grocery_shop_backend.Service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BrandController
{
    @Autowired
    BrandService brandService;

    // GET API {Find All Brands}
    @GetMapping("/brand")
    public ResponseEntity<List<Brand>> getAllBrands()
    {
        try{
            return ResponseEntity.ok(brandService.findAllBrands());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find Brand by ID}
    @GetMapping("/brand-slug/{slugTitle}")
    public ResponseEntity<Brand> getBrandBySlugTitle(@PathVariable String slugTitle)
    {
        try {
            return ResponseEntity.ok(brandService.findBrandBySlugTitle(slugTitle));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find Brand by Slug Title}
    @GetMapping("/brand-id/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable int id)
    {
        try {
            return ResponseEntity.ok(brandService.findBrandById(id));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST API {Add New Brand}
    @PostMapping("/add-brand")
    public ResponseEntity<String> addBrand(@RequestBody BrandDTO brandDTO)
    {
        try {
            brandService.addBrand(brandDTO);
            return ResponseEntity.ok("Success");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    // PATCH API {Update Brand}
    @PatchMapping("update-brand/{brandId}")
    public Brand updateBrand(@PathVariable int brandId, @RequestBody BrandDTO brandDTO)
    {
        return brandService.updateBrand(brandId, brandDTO);
    }

    // PATCH API {Delete Brand}
    @PatchMapping("/delete-brand/{brandId}")
    public ResponseEntity<String> deleteBrand(@PathVariable int brandId)
    {
        brandService.deleteBrand(brandId);
        return ResponseEntity.ok("Success");
    }

    // PATCH API {Retrieve Brand}
    @PatchMapping("/retrieve-brand/{brandId}")
    public ResponseEntity<String> retrieveBrand(@PathVariable int brandId)
    {
        boolean success = brandService.retrieveBrand(brandId);
        if(success)
            return ResponseEntity.ok("Brand retrieved successfully");
        else
            return ResponseEntity.badRequest().body("Brand Already Present");
    }
}
