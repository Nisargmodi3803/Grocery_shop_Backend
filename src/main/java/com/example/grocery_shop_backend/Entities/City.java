package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_city")
public class City
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private int cityId;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "city_is_active")
    private int cityIsActive;

    @Column(name = "is_deleted")
    private int isDeleted; // 1 => Not deleted & 2 => deleted

    @Column(name = "c_date")
    private String cDate;

    public City(){}

    public City(int cityId, String cityName, int cityIsActive, int isDeleted, String cDate) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityIsActive = cityIsActive;
        this.isDeleted = isDeleted;
        this.cDate = cDate;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int isCityIsActive() {
        return cityIsActive;
    }

    public void setCityIsActive(int cityIsActive) {
        this.cityIsActive = cityIsActive;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }
}
