package com.project.aws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.project.model.GetDonor;
import com.project.model.GetRecipient;
import com.project.model.SimpleDonor;
import com.project.model.SimpleRecipient;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

/**
 * Lambda function entry point. You can change to use other pojo type or implement
 * a different RequestHandler.
 *
 * @see <a href=https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html>Lambda Java Handler</a> for more information
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final DynamoDbClient dynamoDbClient;
    private final String tableName;

    public App() {
        dynamoDbClient = DependencyFactory.dynamoDbClient();
        this.tableName = "Users";
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent event, final Context context) {
        return getUser(event);
    }
    
    public APIGatewayProxyResponseEvent getUser(APIGatewayProxyRequestEvent event) {
    	Map<String,String> queryParams = event.getQueryStringParameters();
    	String key = "id";
    	HashMap<String,AttributeValue> keyToGet = keyBuilder(key,queryParams.get(key));
    	GetItemRequest itemRequest = GetItemRequest.builder()
    									.key(keyToGet)
    									.tableName(this.tableName)
    									.build();
    	try {
    		Map<String,AttributeValue> item = this.dynamoDbClient.getItem(itemRequest).item();
    		String type = item.get("type").s();
    		
    		if(type.equals("donor")) {
    			try {
    				GetDonor currDonor = donorToReturn(item);
    				return generateResponse(currDonor);
    			} catch(DynamoDbException e) {
    				return errorResponse();
    			} catch(Exception e) {
    				return errorResponse();
    			}
    		}
    		
    		else if(type.equals("recipient")) {
    			try {
    				GetRecipient currRecipient = recipientToReturn(item);
    				return generateResponse(currRecipient);
    			} catch(DynamoDbException e) {
    				return errorResponse();
    			} catch(Exception e) {
    				return errorResponse();
    			}
    		}
    	} catch(DynamoDbException e) {
    		return errorResponse();
    	}
    	return errorResponse();
    }
    
    public GetDonor donorToReturn(Map<String,AttributeValue> item) throws Exception {
    	SimpleDonor donor = getSimpleDonor(item);
    	List<SimpleRecipient> recipients = new ArrayList<SimpleRecipient>();
    	List<AttributeValue> myRecipients = item.get("donations").l();
    	if(myRecipients != null && myRecipients.size() != 0) {
    		
    		HashMap<String,String> attributes = new HashMap<>();
			attributes.put("#id", "id"); attributes.put("#name", "name"); attributes.put("#state", "state");
			String projectionExpression = "#id,#name,bloodGroup,email,phone,hospitalAddress,pincode,city,#state,requestStatus,dateOfFulfilment";
    		
			for(int i=myRecipients.size()-1; i>=0; i--) {
    			String rid = myRecipients.get(i).s();
    			HashMap<String,AttributeValue> reqRecipientKey = keyBuilder("id",rid);
    			GetItemRequest recipientItemRequest = GetItemRequest.builder()
    													.key(reqRecipientKey)
    													.tableName(this.tableName)
    													.expressionAttributeNames(attributes)
    													.projectionExpression(projectionExpression)
    													.build();
    			
    			Map<String,AttributeValue> recipientItem = this.dynamoDbClient.getItem(recipientItemRequest).item();
    			recipients.add(getSimpleRecipient(recipientItem));
    		}
    	}
    		
    	GetDonor currDonor = new GetDonor(donor,recipients);
    	return currDonor;
    }
    
    public GetRecipient recipientToReturn(Map<String,AttributeValue> item) throws Exception{
    	SimpleRecipient recipient = getSimpleRecipient(item);
    	List<SimpleDonor> donors = new ArrayList<SimpleDonor>();
    	if(item.get("requestStatus").n().equals("0")) {
    		getMappedDonor(item.get("donorId").s(),donors);
    	}
    	else {
    		getPotentialDonors(recipient,donors);
    	}
    	
    	GetRecipient currRecipient = new GetRecipient(recipient, donors);
    	return currRecipient;
    }
    
    public void getMappedDonor(String donorId,List<SimpleDonor> donors) throws Exception{
    	HashMap<String,AttributeValue> reqDonorKey = keyBuilder("id",donorId);
    	HashMap<String,String> attributes = new HashMap<>();
    	attributes.put("#id","id"); attributes.put("#name","name"); attributes.put("#state","state");
    	String projectionExpression = "#id,#name,bloodGroup,email,phone,pincode,city,#state";
    	
    	GetItemRequest donorItemRequest = GetItemRequest.builder()
    										.key(reqDonorKey)
    										.tableName(this.tableName)
    										.expressionAttributeNames(attributes)
    										.projectionExpression(projectionExpression)
    										.build();
    	Map<String,AttributeValue> donorItem = this.dynamoDbClient.getItem(donorItemRequest).item();
    	donors.add(getSimpleDonor(donorItem));
    	
    }
    
    public void getPotentialDonors(SimpleRecipient recipient,List<SimpleDonor> donors) throws Exception{
    	HashMap<String,String> attributes = new HashMap<>();
    	attributes.put("#id","id"); attributes.put("#name","name"); attributes.put("#bloodGroup","bloodGroup"); attributes.put("#state","state");
    	attributes.put("#availabilityStatus","availabilityStatus"); attributes.put("#city","city"); attributes.put("#type","type");
    	
    	String type = "donor";
    	String availabilityStatus = "1";
    	String bloodGroup = recipient.getBloodGroup();
    	String city = recipient.getCity();
    	HashMap<String,AttributeValue> values = new HashMap<>();
    	values.put(":type",AttributeValue.builder().s(type).build());
    	values.put(":availabilityStatus",AttributeValue.builder().n(availabilityStatus).build());
    	values.put(":bloodGroup",AttributeValue.builder().s(bloodGroup).build());
    	values.put(":city",AttributeValue.builder().s(city).build());
    	
    	String filterExpression = "#type = :type AND #availabilityStatus = :availabilityStatus AND #bloodGroup = :bloodGroup AND #city = :city";
    	String projectionExpression = "#id,#name,#bloodGroup,email,phone,pincode,#city,#state";
    	
    	ScanRequest scanRequest = ScanRequest.builder()
    								.tableName(this.tableName)
    								.expressionAttributeNames(attributes)
    								.expressionAttributeValues(values)
    								.filterExpression(filterExpression)
    								.projectionExpression(projectionExpression)
    								.build();
    	
    	List<Map<String,AttributeValue>> potentialDonors = this.dynamoDbClient.scan(scanRequest).items();
    	if(potentialDonors != null) {
    		for(Map<String,AttributeValue> donor : potentialDonors) {
    			donors.add(getSimpleDonor(donor));
    		}
    	}
    	
    }
    
    public SimpleDonor getSimpleDonor(Map<String,AttributeValue> donorItem) {
    	SimpleDonor donor = new SimpleDonor();
    	if(donorItem != null) {
    		donor.setId(donorItem.get("id").s());
    		donor.setName(donorItem.get("name").s());
    		donor.setBloodGroup(donorItem.get("bloodGroup").s());
    		donor.setEmail(donorItem.get("email").s());
    		donor.setPhone(donorItem.get("phone").s());
	    	donor.setPincode(donorItem.get("pincode").s());
	    	donor.setCity(donorItem.get("city").s());
	    	donor.setState(donorItem.get("state").s());
    	}
    	return donor;
    }
    
    public SimpleRecipient getSimpleRecipient(Map<String,AttributeValue> recipientItem) {
    	SimpleRecipient recipient = new SimpleRecipient();
    	if(recipientItem != null) {
    		recipient.setId(recipientItem.get("id").s());
    		recipient.setName(recipientItem.get("name").s());
    		recipient.setBloodGroup(recipientItem.get("bloodGroup").s());
    		recipient.setEmail(recipientItem.get("email").s());
    		recipient.setPhone(recipientItem.get("phone").s());
    		recipient.setHospitalAddress(recipientItem.get("hospitalAddress").s());
    		recipient.setPincode(recipientItem.get("pincode").s());
    		recipient.setCity(recipientItem.get("city").s());
    		recipient.setState(recipientItem.get("state").s());
    		recipient.setRequestStatus(Integer.parseInt(recipientItem.get("requestStatus").n()));
    		if(recipientItem.containsKey("dateOfFulfilment"))
    			recipient.setDateOfFulfilment(recipientItem.get("dateOfFulfilment").s());
    		else
    			recipient.setDateOfFulfilment("");
    	}
    	return recipient;
    }
    
    public HashMap<String,AttributeValue> keyBuilder(String key,String value) {
    	HashMap<String,AttributeValue> keyToGet = new HashMap<>();
    	keyToGet.put(key, AttributeValue.builder()
    						.s(value)
    						.build());
    	return keyToGet;
    }
    
    public APIGatewayProxyResponseEvent generateResponse(GetDonor donor) {
    	APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    	Gson gson = new Gson();
    	String body = gson.toJson(donor);
    	response.setStatusCode(200);
    	response.setHeaders(responseHeaders());
    	response.setBody(body);
    	return response;
    }
    
    public APIGatewayProxyResponseEvent generateResponse(GetRecipient recipient) {
    	APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    	Gson gson = new Gson();
    	String body = gson.toJson(recipient);
    	response.setStatusCode(200);
    	response.setHeaders(responseHeaders());
    	response.setBody(body);
    	return response;
    }
    
    public APIGatewayProxyResponseEvent errorResponse() {
    	APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    	response.setStatusCode(500);
    	response.setHeaders(responseHeaders());
    	response.setBody("Internal Server Error");
    	return response;
    }
    
    public HashMap<String,String> responseHeaders() {
    	HashMap<String,String> headers = new HashMap<>();
    	headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token");
    	headers.put("Access-Control-Allow-Origin", "*");
    	headers.put("Access-Control-Allow-Methods", "OPTIONS,POST,GET,PUT");
    	return headers;
    }
  
}