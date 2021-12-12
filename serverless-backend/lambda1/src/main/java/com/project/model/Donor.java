package com.project.model;

import java.util.List;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Donor {
	
	//attributes
	
	private String id;
	private String name;
	private int age;
	private String gender;
	private int weight;
	private String bloodGroup;
	private String email;
	private String phone;
	private String pincode;
	private String city;
	private String state;
	private String covidPosDate;
	private String covidNegDate;
	private String dateOfRegistration;
	private int availabilityStatus;
	private List<String> donations;
	private String dateOfLastDonation;
	private String type;
	
	public Donor() {
		this.availabilityStatus=1;
		this.type="donor";
	}

	public Donor(String id, String name, int age, String gender, int weight, String bloodGroup, String email,
			String phone, String pincode, String city, String state, String covidPosDate, String covidNegDate,
			String dateOfRegistration, List<String> donations, String dateOfLastDonation) {
		this();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.weight = weight;
		this.bloodGroup = bloodGroup;
		this.email = email;
		this.phone = phone;
		this.pincode = pincode;
		this.city = city;
		this.state = state;
		this.covidPosDate = covidPosDate;
		this.covidNegDate = covidNegDate;
		this.dateOfRegistration = dateOfRegistration;
		this.donations = donations;
		this.dateOfLastDonation = dateOfLastDonation;
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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

	public String getCovidPosDate() {
		return covidPosDate;
	}

	public void setCovidPosDate(String covidPosDate) {
		this.covidPosDate = covidPosDate;
	}

	public String getCovidNegDate() {
		return covidNegDate;
	}

	public void setCovidNegDate(String covidNegDate) {
		this.covidNegDate = covidNegDate;
	}

	public String getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(String dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public int getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(int availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public List<String> getDonations() {
		return donations;
	}

	public void setDonations(List<String> donations) {
		this.donations = donations;
	}

	public String getDateOfLastDonation() {
		return dateOfLastDonation;
	}

	public void setDateOfLastDonation(String dateOfLastDonation) {
		this.dateOfLastDonation = dateOfLastDonation;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
