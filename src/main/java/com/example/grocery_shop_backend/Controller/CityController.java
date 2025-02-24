package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.CityUpdateDTO;
import com.example.grocery_shop_backend.Entities.City;
import com.example.grocery_shop_backend.Service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<City>> findAllCities()
    {
       try{
           return new ResponseEntity<>(cityService.findAllCities(), HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
    }

    // GET API {Find City By ID}
    @GetMapping("/city/{id}")
    public ResponseEntity<City> findCityById(@PathVariable int id)
    {
        try{
            return new ResponseEntity<>(cityService.findCityById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
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
