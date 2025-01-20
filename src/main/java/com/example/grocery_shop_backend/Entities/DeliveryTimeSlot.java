package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_delivery_time_slot")
public class DeliveryTimeSlot
{
    @Id
    @Column(name = "delievry_time_slot_id")
    private int deliveryTimeSlotId;

    @Column(name = "delivery_time_slot")
    private String deliveryTime;

    @Column(name ="delivery_time_slot_prioriy")
    private int deliveryTimePrioriy; // LESS VALUE MORE PRIORITY

    @Column(name = "is_deleted")
    private int isDeleted; // 1 => Not deleted & 2 => Deleted

    @Column(name = "c_date")
    private String cDate;

    public DeliveryTimeSlot() {

    }

    public DeliveryTimeSlot(int deliveryTimeSlotId, String deliveryTime, int deliveryTimePrioriy, int isDeleted, String cDate) {
        this.deliveryTimeSlotId = deliveryTimeSlotId;
        this.deliveryTime = deliveryTime;
        this.deliveryTimePrioriy = deliveryTimePrioriy;
        this.isDeleted = isDeleted;
        this.cDate = cDate;
    }

    public void setDeliveryTimeSlotId(int deliveryTimeSlotId) {
        this.deliveryTimeSlotId = deliveryTimeSlotId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getDeliveryTimePrioriy() {
        return deliveryTimePrioriy;
    }

    public void setDeliveryTimePrioriy(int deliveryTimePrioriy) {
        this.deliveryTimePrioriy = deliveryTimePrioriy;
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
