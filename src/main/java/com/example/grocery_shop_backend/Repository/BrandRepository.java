package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand,Integer>
{
    @Query("SELECT brand FROM Brand brand WHERE brand.id = :id")
    public Brand findBrandById(int id);

    @Query("SELECT brand FROM Brand brand WHERE brand.slug_title = :slugTitle")
    public Brand findBrandBySlugTitle(String slugTitle);
}
