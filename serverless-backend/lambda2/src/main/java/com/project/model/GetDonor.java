package com.project.model;

import java.util.List;

public class GetDonor {
	
	SimpleDonor myDetails;
	List<SimpleRecipient> recipients;
	
	public GetDonor() {
		
	}

	public GetDonor(SimpleDonor myDetails, List<SimpleRecipient> recipients) {
		this();
		this.myDetails = myDetails;
		this.recipients = recipients;
	}

	public SimpleDonor getMyDetails() {
		return myDetails;
	}

	public void setMyDetails(SimpleDonor myDetails) {
		this.myDetails = myDetails;
	}

	public List<SimpleRecipient> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<SimpleRecipient> recipients) {
		this.recipients = recipients;
	}
	
	

}
