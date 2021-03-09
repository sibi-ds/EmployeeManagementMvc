package com.ideas2it.employeemanagement.model;

/**
 * POJO class used to store
 * employee address details that are
 * door number,street,district,state,pincode
 * 
 * @author  sibi
 * @created 2021-03-08
 */
public class Address {

    private char addressType;
    private String doorNumber;
    private String street;
    private String district;
    private String state;
    private int pincode;
    
    private Address() {
    }

    public Address(char addressType, String doorNumber, String street
            , String district, String state, int pincode) {
        this.addressType = addressType;
        this.doorNumber = doorNumber;
        this.street = street;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
    }

    public void setAddressType(char addressType) {
        this.addressType = addressType;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public char getAddressType() {
        return addressType;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public int getPincode() {
        return pincode;
    }

    public String toString() {
        return "\nDoor Number : " + doorNumber
               + "\nStreet :      " + street
               + "\nDistrict :    " + district
               + "\nState :       " + state
               + "\nPin Code :    " + pincode + "\n";
    }
}