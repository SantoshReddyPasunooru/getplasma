package com.project.model;

public class SimpleRecipient {
	
	//attributes
	
	private String id;
	private String name;
	private String bloodGroup;
	private String email;
	private String phone;
	private String hospitalAddress;
	private String pincode;
	private String city;
	private String state;
	private int requestStatus;
	private String dateOfFulfilment;
	private final String type;
	
	public SimpleRecipient() {
		this.type = "recipient";
	}

	public SimpleRecipient(String id, String name, String bloodGroup, String email, String phone,
			String hospitalAddress, String pincode, String city, String state, int requestStatus,
			String dateOfFulfilment) {
		this();
		this.id = id;
		this.name = name;
		this.bloodGroup = bloodGroup;
		this.email = email;
		this.phone = phone;
		this.hospitalAddress = hospitalAddress;
		this.pincode = pincode;
		this.city = city;
		this.state = state;
		this.requestStatus = requestStatus;
		this.dateOfFulfilment = dateOfFulfilment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHospitalAddress() {
		return hospitalAddress;
	}

	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(int requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getDateOfFulfilment() {
		return dateOfFulfilment;
	}

	public void setDateOfFulfilment(String dateOfFulfilment) {
		this.dateOfFulfilment = dateOfFulfilment;
	}

	public String getType() {
		return type;
	}
	

}
