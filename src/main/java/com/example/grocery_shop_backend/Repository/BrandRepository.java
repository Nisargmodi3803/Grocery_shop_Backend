package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer>
{
    @Query("SELECT brand FROM Brand brand WHERE brand.id = :id AND brand.isDeleted=1")
    public Brand findBrandById(int id);

    @Query("SELECT brand FROM Brand brand WHERE brand.slug_title = :slugTitle AND brand.isDeleted=1")
    public Brand findBrandBySlugTitle(String slugTitle);

    @Query("SELECT brand FROM Brand brand WHERE brand.isDeleted=1")
    public List<Brand> findAllBrands();

    // Search Brand
    public List<Brand> findByNameContainingIgnoreCaseAndIsDeleted(String name, int isDeleted);


    @Query("SELECT brand.slug_title FROM Brand brand WHERE brand.isDeleted=1 AND brand.slug_title = :slugTitle")
    public String checkSlugTitles(String slugTitle);
}
