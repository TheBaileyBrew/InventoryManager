package com.thebaileybrew.inventorymanager.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {

    private String itemType;
    private String orderDate;
    private String expectedDate;
    private String orderQuantity;

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
    public Order() {}

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

    private Order(Parcel in) {
        itemType = in.readString();
        orderDate = in.readString();
        expectedDate = in.readString();
        orderQuantity = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemType);
        dest.writeString(orderDate);
        dest.writeString(expectedDate);
        dest.writeString(orderQuantity);

    }
}
