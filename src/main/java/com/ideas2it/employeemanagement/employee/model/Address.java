package com.ideas2it.employeemanagement.employee.model;

import com.ideas2it.employeemanagement.employee.model.Employee;

/**
 * POJO class used to store
 * employee address details that are
 * door number,street,district,state,pincode
 * 
 * @author  sibi
 * @created 2021-03-08
 */
public class Address {

    private int addressId;
    private String addressType;
    private String doorNumber;
    private String street;
    private String village;
    private String district;
    private String state;
    private String pincode;
	private Employee employee;
	private boolean isDeleted;

    public Address() {
	}

    public Address(int addressId, String addressType, String doorNumber, String street
            , String village, String district, String state
			, String pincode, boolean isDeleted) {
		this.addressId = addressId;
        this.addressType = addressType;
        this.doorNumber = doorNumber;
        this.street = street;
        this.village = village;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
		this.isDeleted = isDeleted;
    }
	
	public Address(String addressType, String doorNumber, String street
            , String village, String district, String state
			, String pincode, boolean isDeleted) {
        this.addressType = addressType;
        this.doorNumber = doorNumber;
        this.street = street;
        this.village = village;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
		this.isDeleted = isDeleted;
    }
	
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
	
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
	
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
	
    public int getAddressId() {
        return addressId;
    }

    public String getAddressType() {
        return addressType;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getVillage() {
        return village;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }
	
    public String toString() {
        return "\nAddress Type  : " + addressType
               + "\nDoor Number   : " + doorNumber
               + "\nStreet        : " + street
               + "\nvillage       : " + village
               + "\nDistrict      : " + district
               + "\nState         : " + state
               + "\nPin Code      : " + pincode + "\n";
    }
}