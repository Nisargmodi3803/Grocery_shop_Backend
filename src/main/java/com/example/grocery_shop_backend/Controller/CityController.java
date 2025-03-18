package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.CityUpdateDTO;
import com.example.grocery_shop_backend.Entities.City;
import com.example.grocery_shop_backend.Service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
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

    // GET API {Search City} [Only Names Testing]
//    @GetMapping("/search-city")
//    public ResponseEntity<List<String>> searchCity(@RequestParam String cityName){
//        try {
//            List<City> cities = cityService.searchCity(cityName);
//            List<String> names = cities.stream()
//                .map(City::getCityName)
//                .collect(Collectors.toList());
//
//            return new ResponseEntity<>(names, HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }

    // GET API {Search City}
    @GetMapping("/search-city")
    public ResponseEntity<List<City>> searchCity(@RequestParam String cityName){
        try {
            List<City> cities = cityService.searchCity(cityName);

            return new ResponseEntity<>(cities, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
