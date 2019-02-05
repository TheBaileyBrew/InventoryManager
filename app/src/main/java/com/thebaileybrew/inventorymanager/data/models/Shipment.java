package com.thebaileybrew.inventorymanager.data.models;

import java.util.Objects;

public class Shipment {

    private String shipmentSerialNumber;
    private String shipmentCustomerNumber;
    private String shipmentRequestDate;
    private Boolean shipmentKit;
    private Boolean shipmentDevice;
    private Boolean shipmentBinder;
    private Boolean shipmentGuide;
    private int shipmentLoadedProfile;
    private int shipmentDeviceID;
    private Boolean shipmentTested;
    private Boolean shipmentShipped;
    private String shipmentUserName;

    public Shipment() {}

    public Shipment(String shipmentSerialNumber, String shipmentCustomerNumber,
                    String shipmentRequestDate, Boolean shipmentKit, Boolean shipmentDevice,
                    Boolean shipmentBinder, Boolean shipmentGuide, int shipmentLoadedProfile,
                    int shipmentDeviceID, Boolean shipmentTested, Boolean shipmentShipped,
                    String shipmentUserName) {
        this.shipmentBinder = shipmentBinder;
        this.shipmentCustomerNumber = shipmentCustomerNumber;
        this.shipmentSerialNumber = shipmentSerialNumber;
        this.shipmentRequestDate = shipmentRequestDate;
        this.shipmentKit = shipmentKit;
        this.shipmentDevice = shipmentDevice;
        this.shipmentDeviceID = shipmentDeviceID;
        this.shipmentGuide = shipmentGuide;
        this.shipmentLoadedProfile = shipmentLoadedProfile;
        this.shipmentTested = shipmentTested;
        this.shipmentShipped = shipmentShipped;
        this.shipmentUserName = shipmentUserName;
    }

    public String getShipmentSerialNumber() {
        return shipmentSerialNumber;
    }
    public void setShipmentSerialNumber(String shipmentSerialNumber) {
        this.shipmentSerialNumber = shipmentSerialNumber;
    }
    public String getShipmentCustomerNumber() {
        return shipmentCustomerNumber;
    }
    public void setShipmentCustomerNumber(String shipmentCustomerNumber) {
        this.shipmentCustomerNumber = shipmentCustomerNumber;
    }
    public String getShipmentRequestDate() {
        return shipmentRequestDate;
    }
    public void setShipmentRequestDate(String shipmentRequestDate) {
        this.shipmentRequestDate = shipmentRequestDate;
    }
    public Boolean getShipmentKit() {
        return shipmentKit;
    }
    public void setShipmentKit(Boolean shipmentKit) {
        this.shipmentKit = shipmentKit;
    }
    public Boolean getShipmentDevice() {
        return shipmentDevice;
    }
    public void setShipmentDevice(Boolean shipmentDevice) {
        this.shipmentDevice = shipmentDevice;
    }
    public Boolean getShipmentBinder() {
        return shipmentBinder;
    }
    public void setShipmentBinder(Boolean shipmentBinder) {
        this.shipmentBinder = shipmentBinder;
    }
    public Boolean getShipmentGuide() {
        return shipmentGuide;
    }
    public void setShipmentGuide(Boolean shipmentGuide) {
        this.shipmentGuide = shipmentGuide;
    }
    public int getShipmentLoadedProfile() {
        return shipmentLoadedProfile;
    }
    public void setShipmentLoadedProfile(int shipmentLoadedProfile) {
        this.shipmentLoadedProfile = shipmentLoadedProfile;
    }
    public int getShipmentDeviceID() {
        return shipmentDeviceID;
    }
    public void setShipmentDeviceID(int shipmentDeviceID) {
        this.shipmentDeviceID = shipmentDeviceID;
    }
    public Boolean getShipmentTested() {
        return shipmentTested;
    }
    public void setShipmentTested(Boolean shipmentTested) {
        this.shipmentTested = shipmentTested;
    }
    public Boolean getShipmentShipped() {
        return shipmentShipped;
    }
    public void setShipmentShipped(Boolean shipmentShipped) {
        this.shipmentShipped = shipmentShipped;
    }
    public String getShipmentUserName() {
        return shipmentUserName;
    }
    public void setShipmentUserName(String shipmentUserName) {
        this.shipmentUserName = shipmentUserName;
    }
}
