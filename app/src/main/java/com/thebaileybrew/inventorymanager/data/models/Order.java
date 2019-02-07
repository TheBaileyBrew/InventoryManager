package com.thebaileybrew.inventorymanager.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Order {

    private String itemType;
    private String orderDate;
    private String expectedDate;
    private String orderQuantity;
    private List<String> orderedUnits;
    private String orderID;


    public Order() {}

    public Order(String orderDate, String expectedDate, String itemType, String orderQuantity, List<String> orderedUnits, String orderID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.expectedDate = expectedDate;
        this.itemType = itemType;
        this.orderQuantity = orderQuantity;
        this.orderedUnits = orderedUnits;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderedUnits(List<String> orderedUnits) {
        this.orderedUnits = orderedUnits;
    }

    public List<String> getOrderedUnits() {
        return orderedUnits;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderID() {
        return orderID;
    }
}
