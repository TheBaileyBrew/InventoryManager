package com.thebaileybrew.inventorymanager.data.models;

public class Device {

    private String deviceSerialNumber;
    private String deviceModel;
    private String deviceCategory;

    public Device() {}

    public Device(String deviceSerialNumber, String deviceModel, String deviceCategory) {
        this.deviceSerialNumber = deviceSerialNumber;
        this.deviceModel = deviceModel;
        this.deviceCategory = deviceCategory;
    }

    public String getDeviceSerialNumber() {
        return deviceSerialNumber;
    }

    public void setDeviceSerialNumber(String deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(String deviceCategory) {
        this.deviceCategory = deviceCategory;
    }
}
