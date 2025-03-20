package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.CityUpdateDTO;
import com.example.grocery_shop_backend.Entities.City;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public List<City> findAllAdminCities()
    {
        List<City> cities = cityRepository.findAllAdminCities();
        if (cities.isEmpty())
            throw new objectNotFoundException("No cities found");
        return cities;
    }

    // Update City Service
    @Transactional
    public void updateCity(int cityId, CityUpdateDTO updateCity)
    {
        City city = cityRepository.findCityById(cityId);

        if(city!=null)
        {
            if(updateCity!=null)
            {
                city.setCityName(updateCity.getCity());
                city.setCityIsActive(updateCity.getStatus());
                cityRepository.save(city);
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
    public void addCity(CityUpdateDTO cityDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        City city = new City();
        city.setCityName(cityDto.getCity());
        city.setCityIsActive(cityDto.getStatus());
        city.setIsDeleted(1);
        city.setcDate(cDate);
        cityRepository.save(city);
    }

    // Search City Service
    public List<City> searchCity(String cityName){
        List<City> cities = cityRepository.searchCity(cityName);

        if(cities.isEmpty()){
            throw new objectNotFoundException("No city found");
        }
        return cities;
    }

    // Delete City Service
    public void deleteCity(int cityId) {
        City city = cityRepository.findCityById(cityId);

        if(city==null){
            throw new objectNotFoundException("No city found");
        }
        city.setIsDeleted(2);
        cityRepository.save(city);
    }

}
