package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer>
{
    @Query("SELECT brand FROM Brand brand WHERE brand.id = :id AND brand.is_deleted=1")
    public Brand findBrandById(int id);

    @Query("SELECT brand FROM Brand brand WHERE brand.slug_title = :slugTitle AND brand.is_deleted=1")
    public Brand findBrandBySlugTitle(String slugTitle);
}
