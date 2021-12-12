package com.project.model;

public class SimpleDonor {

	//attributes
	
	private String id;
	private String name;
	private String bloodGroup;
	private String email;
	private String phone;
	private String pincode;
	private String city;
	private String state;
	private final String type;
	
	public SimpleDonor() {
		this.type="donor";
	}

	public SimpleDonor(String id, String name, String bloodGroup, String email, String phone, String pincode,
			String city, String state) {
		this();
		this.id = id;
		this.name = name;
		this.bloodGroup = bloodGroup;
		this.email = email;
		this.phone = phone;
		this.pincode = pincode;
		this.city = city;
		this.state = state;
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

	public String getType() {
		return type;
	}
		
}
