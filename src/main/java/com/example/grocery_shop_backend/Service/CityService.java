package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.CityUpdateDTO;
import com.example.grocery_shop_backend.Entities.City;
import com.example.grocery_shop_backend.Exception.DuplicateEntryException;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CityService
{
    @Autowired
    private CityRepository cityRepository;

    // Find all Cities Service
    public List<City> findAllCities()
    {
        List<City> cities = cityRepository.findAllCities();
        if (cities.isEmpty())
            throw new objectNotFoundException("No cities found");
        return cities;
    }

    // Update City Service
    @Transactional
    public City updateCity(int cityId, CityUpdateDTO updateCity)
    {
        City city = cityRepository.findCityById(cityId);

        if(city!=null)
        {
            if(updateCity!=null)
            {
                city.setCityName(updateCity.getCity());
                return cityRepository.save(city);
            }
            else
                throw new objectNotFoundException("No update found");
        }
        else
            throw new objectNotFoundException("No city found");
    }

    // Find City By ID Service
    public City findCityById(int cityId)
    {
        City city = cityRepository.findCityById(cityId);

        if (city == null)
            throw new objectNotFoundException("No city found");
        return city;
    }

    // Add City Service
    @Transactional
    public void addCity(String cityName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        List<City> existedCity = cityRepository.findAllCities();
        List<String> cities = existedCity.stream()
                .map(City::getCityName)
                .filter(Objects::nonNull) // Exclude null city names
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        String lowerCaseCityName = cityName.toLowerCase();

        if (cities.contains(lowerCaseCityName)) {
            throw new DuplicateEntryException("City already exists");
        }

        City city = new City();
        city.setCityName(cityName);
        city.setCityIsActive(1);
        city.setIsDeleted(1);
        city.setcDate(cDate);
        cityRepository.save(city);
    }

}
