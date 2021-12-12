package com.project.aws;

import java.time.LocalDate;
import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.project.model.UpdateModel;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

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
        tableName = "Users";
  
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent event, final Context context) {
    	
        return match(event);
    }
    
    public APIGatewayProxyResponseEvent match(APIGatewayProxyRequestEvent event) {
    	Gson gson = new Gson();
    	UpdateModel updateModel = gson.fromJson(event.getBody(), UpdateModel.class);
    	boolean flag1 = updateDonor(updateModel);
    	boolean flag2 = updateRecipient(updateModel);
    	
    	if(flag1 && flag2)
    		return generateResponse();
    	return errorResponse();
    }
    
    public boolean updateDonor(UpdateModel updateModel) {
    	boolean flag = true;
    	
    	String key = "id";
    	HashMap<String,AttributeValue> itemToUpdate = new HashMap<>();
    	itemToUpdate.put(key, AttributeValue.builder().s(updateModel.getDonorId()).build());
    	
    	HashMap<String,String> attributes = new HashMap<>();
    	attributes.put("#donations","donations"); 
    	attributes.put("#availabilityStatus","availabilityStatus");
    	attributes.put("#dateOfLastDonation","dateOfLastDonation");
    	
    	HashMap<String,AttributeValue> values = new HashMap<>();
    	String availabilityStatus = "0";
    	String dateOfLastDonation = LocalDate.now().toString();
    	values.put(":newDonation", AttributeValue.builder().l(AttributeValue.builder().s(updateModel.getRecipientId()).build()).build());
    	values.put(":availabilityStatus",AttributeValue.builder().s(availabilityStatus).build());
    	values.put(":dateOfLastDonation", AttributeValue.builder().s(dateOfLastDonation).build());
    	
    	String updateExpression = "SET #donations = list_append(#donations,:newDonation), #availabilityStatus = :availabilityStatus, #dateOfLastDonation = :dateOfLastDonation";
    	
    	UpdateItemRequest updateRequest = UpdateItemRequest.builder()
    										.tableName(this.tableName)
    										.key(itemToUpdate)
    										.expressionAttributeNames(attributes)
    										.expressionAttributeValues(values)
    										.updateExpression(updateExpression)
    										.build();
    	
    	try {
    		this.dynamoDbClient.updateItem(updateRequest);
    	} catch(DynamoDbException exp) {
    		flag = false;
    	}
    	return flag;
    }
    
    public boolean updateRecipient(UpdateModel updateModel) {
    	boolean flag = true;
    	
    	String key = "id";
    	HashMap<String,AttributeValue> itemToUpdate = new HashMap<>();
    	itemToUpdate.put(key, AttributeValue.builder().s(updateModel.getRecipientId()).build());
    	
    	HashMap<String,String> attributes = new HashMap<>();
    	attributes.put("#donorId", "donorId");
    	attributes.put("#requestStatus", "requestStatus");
    	attributes.put("#dateOfFulfilment","dateOfFulfilment");
    	
    	HashMap<String,AttributeValue> values = new HashMap<>();
    	String donorId = updateModel.getDonorId();
    	String requestStatus = "0";
    	String dateOfFulfilment = LocalDate.now().toString();
    	values.put(":donorId", AttributeValue.builder().s(donorId).build());
    	values.put(":requestStatus", AttributeValue.builder().n(requestStatus).build());
    	values.put(":dateOfFulfilment", AttributeValue.builder().s(dateOfFulfilment).build());
    	
    	String updateExpression = "SET #donorId = :donorId, #requestStatus = :requestStatus, #dateOfFulfilment = :dateOfFulfilment";
    	
    	UpdateItemRequest updateRequest = UpdateItemRequest.builder()
    										.tableName(this.tableName)
    										.key(itemToUpdate)
    										.expressionAttributeNames(attributes)
    										.expressionAttributeValues(values)
    										.updateExpression(updateExpression)
    										.build();
    	
    	try {
    		this.dynamoDbClient.updateItem(updateRequest);
    	} catch(DynamoDbException exp) {
    		flag = false;
    	}
    	return flag;
    }
    
    public APIGatewayProxyResponseEvent generateResponse() {
    	APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    	response.setStatusCode(200);
    	response.setHeaders(responseHeaders());
    	response.setBody("OK");
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
