package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_delivery_boy")
public class DeliveryBoy
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_boy_id")
    private int deliveryBoyId;

    @Column(name = "delivery_boy_name")
    private String deliveryBoyName;

    @Column(name = "delievery_boy_email")
    private String deliveryBoyEmail;

    @Column(name = "delivery_boy_mobile_no")
    private String deliveryBoyMobileNo;

    @Column(name = "delivery_boy_password")
    private String deliveryBoyPassword;

    @Column(name = "delivery_vehicle_no")
    private String deliveryVehicleNo;

    @Column(name = "delivery_route")
    private String deliveryRoute;

    @Column(name = "is_delete")
    private int isDeleted; // 1 => Not deleted & 2 => Deleted

    @Column(name = "c_date")
    private String cDate;

    public DeliveryBoy(){}

    public DeliveryBoy(int deliveryBoyId, String deliveryBoyName, String deliveryBoyEmail, String deliveryBoyMobileNo, String deliveryBoyPassword, String deliveryVehicleNo, String deliveryRoute, int isDeleted, String cDate) {
        this.deliveryBoyId = deliveryBoyId;
        this.deliveryBoyName = deliveryBoyName;
        this.deliveryBoyEmail = deliveryBoyEmail;
        this.deliveryBoyMobileNo = deliveryBoyMobileNo;
        this.deliveryBoyPassword = deliveryBoyPassword;
        this.deliveryVehicleNo = deliveryVehicleNo;
        this.deliveryRoute = deliveryRoute;
        this.isDeleted = isDeleted;
        this.cDate = cDate;
    }

    public int getDeliveryBoyId() {
        return deliveryBoyId;
    }

    public void setDeliveryBoyId(int deliveryBoyId) {
        this.deliveryBoyId = deliveryBoyId;
    }

    public String getDeliveryBoyName() {
        return deliveryBoyName;
    }

    public void setDeliveryBoyName(String deliveryBoyName) {
        this.deliveryBoyName = deliveryBoyName;
    }

    public String getDeliveryBoyEmail() {
        return deliveryBoyEmail;
    }

    public void setDeliveryBoyEmail(String deliveryBoyEmail) {
        this.deliveryBoyEmail = deliveryBoyEmail;
    }

    public String getDeliveryBoyMobileNo() {
        return deliveryBoyMobileNo;
    }

    public void setDeliveryBoyMobileNo(String deliveryBoyMobileNo) {
        this.deliveryBoyMobileNo = deliveryBoyMobileNo;
    }

    public String getDeliveryBoyPassword() {
        return deliveryBoyPassword;
    }

    public void setDeliveryBoyPassword(String deliveryBoyPassword) {
        this.deliveryBoyPassword = deliveryBoyPassword;
    }

    public String getDeliveryVehicleNo() {
        return deliveryVehicleNo;
    }

    public void setDeliveryVehicleNo(String deliveryVehicleNo) {
        this.deliveryVehicleNo = deliveryVehicleNo;
    }

    public String getDeliveryRoute() {
        return deliveryRoute;
    }

    public void setDeliveryRoute(String deliveryRoute) {
        this.deliveryRoute = deliveryRoute;
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
