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

    @GetMapping("/all-cities")
    public ResponseEntity<List<City>> findAllAdminCities()
    {
       try{
           return new ResponseEntity<>(cityService.findAllAdminCities(), HttpStatus.OK);
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
    public ResponseEntity<String> updateCity(@PathVariable int id, @RequestBody CityUpdateDTO updateCity)
    {
        try {
            cityService.updateCity(id, updateCity);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //POST API {Add City}
    @PostMapping("/add-city")
    public ResponseEntity<String> addCity(@RequestBody CityUpdateDTO cityDto)
    {
        try {
            cityService.addCity(cityDto);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

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
    public ResponseEntity<List<City>> searchCity(@RequestParam String keyword){
        try {
            List<City> cities = cityService.searchCity(keyword);

            return new ResponseEntity<>(cities, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH API {Delete City}
    @PatchMapping("/delete-city/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable int id){
        try {
            cityService.deleteCity(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
