package com.project.model;

import java.util.List;

public class GetRecipient {
	
	SimpleRecipient myDetails;
	List<SimpleDonor> donors;
	
	public GetRecipient() {
		
	}

	public GetRecipient(SimpleRecipient myDetails, List<SimpleDonor> donors) {
		this();
		this.myDetails = myDetails;
		this.donors = donors;
	}

	public SimpleRecipient getMyDetails() {
		return myDetails;
	}

	public void setMyDetails(SimpleRecipient myDetails) {
		this.myDetails = myDetails;
	}

	public List<SimpleDonor> getDonors() {
		return donors;
	}

	public void setDonors(List<SimpleDonor> donors) {
		this.donors = donors;
	}

}
