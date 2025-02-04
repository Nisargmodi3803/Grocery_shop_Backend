package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.CityUpdateDTO;
import com.example.grocery_shop_backend.Entities.City;
import com.example.grocery_shop_backend.Service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CityController
{
    @Autowired
    private CityService cityService;

    // GET API {Find All Cities}
    @GetMapping("/cities")
    public List<City> findAllCities()
    {
        return cityService.findAllCities();
    }

    // GET API {Find City By ID}
    @GetMapping("/city/{id}")
    public City findCityById(@PathVariable int id)
    {
        return cityService.findCityById(id);
    }

    // PATCH API {Update City}
    @PatchMapping("update-city/{id}")
    public City updateCity(@PathVariable int id, @RequestBody CityUpdateDTO updateCity)
    {
        return cityService.updateCity(id, updateCity);
    }

    //POST API {Add City}
    @PostMapping("/add-city")
    public ResponseEntity<String> addCity(@RequestParam String cityName)
    {
        cityService.addCity(cityName);
        return ResponseEntity.ok("Success");
    }
}
