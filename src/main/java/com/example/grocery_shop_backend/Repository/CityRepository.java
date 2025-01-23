package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>
{
    @Query("SELECT city FROM City city WHERE city.isDeleted=1 AND city.cityIsActive=1")
    List<City> findAllCities();

    @Query("SELECT city FROM City city WHERE (city.cityId = :cityId AND city.isDeleted=1) AND city.cityIsActive=1")
    City findCityById(int cityId);
}
