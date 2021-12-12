package com.project.model;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Recipient {
	
	//attributes
	
	private String id;
	private String name;
	private int age;
	private String gender;
	private String bloodGroup;
	private String email;
	private String phone;
	private String hospitalAddress;
	private String pincode;
	private String city;
	private String state;
	private String covidPosDate;
	private String dateOfRequest;
	private int requestStatus; // 1 -> active ; 0 -> fulfilled
	private String donorId;
	private String dateOfFulfilment;
	private String type;
	
	public Recipient() {
		this.requestStatus=1;
		this.type="recipient";
	}

	public Recipient(String id, String name, int age, String gender, String bloodGroup, String email, String phone,
			String hospitalAddress, String pincode, String city, String state, String covidPosDate, String dateOfRequest,
			String donorId, String dateOfFulfilment) {
		this();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.bloodGroup = bloodGroup;
		this.email = email;
		this.phone = phone;
		this.hospitalAddress = hospitalAddress;
		this.pincode=pincode;
		this.city = city;
		this.state = state;
		this.covidPosDate = covidPosDate;
		this.dateOfRequest = dateOfRequest;
		this.donorId = donorId;
		this.dateOfFulfilment = dateOfFulfilment;
	}

	@DynamoDbPartitionKey
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
		this.pincode=pincode;
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

	public String getCovidPosDate() {
		return covidPosDate;
	}

	public void setCovidPosDate(String covidPosDate) {
		this.covidPosDate = covidPosDate;
	}

	public String getDateOfRequest() {
		return dateOfRequest;
	}

	public void setDateOfRequest(String dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}

	public int getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(int requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getDonorId() {
		return donorId;
	}

	public void setDonorId(String donorId) {
		this.donorId = donorId;
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
	
	public void setType(String type) {
		this.type = type;
	}

}
