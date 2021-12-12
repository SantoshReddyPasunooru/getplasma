package com.project.model;

public class UpdateModel {
	
	private String recipientId;
	private String donorId;
	
	public UpdateModel() {
		
	}

	public UpdateModel(String recipientId, String donorId) {
		this();
		this.recipientId = recipientId;
		this.donorId = donorId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getDonorId() {
		return donorId;
	}

	public void setDonorId(String donorId) {
		this.donorId = donorId;
	}

}
