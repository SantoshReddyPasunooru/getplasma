package com.project.aws;


import java.util.ArrayList;
import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.project.model.Donor;
import com.project.model.Recipient;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

/**
 * Lambda function entry point. You can change to use other pojo type or implement
 * a different RequestHandler.
 *
 * @see <a href=https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html>Lambda Java Handler</a> for more information
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final DynamoDbClient dynamoDbClient;
    private final DynamoDbEnhancedClient enhancedClinet;
    private final String tableName;

    public App() {
        dynamoDbClient = DependencyFactory.dynamoDbClient();
        enhancedClinet = DependencyFactory.dynamoDbEnhancedClient(dynamoDbClient);
        tableName = "Users";
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent event, final Context context) {
    	
        boolean success = postDetails(event.getBody());
        APIGatewayProxyResponseEvent response = generateResponse(success);
        return response;
    }
    
    public boolean postDetails(String jsonString) {
    	
    	Gson gson = new Gson();
    	
    	//Map jsonString to corresponding Model
    	if(jsonString.contains("donor")) {
    		Donor donor = gson.fromJson(jsonString, Donor.class);
    		String donorId = "D"+donor.getPhone().substring(5);
    		donor.setId(donorId);
    		donor.setDonations(new ArrayList<String>());
    		return putDonor(donor);
    	}
    	
    	else if(jsonString.contains("recipient")) {
    		Recipient recipient = gson.fromJson(jsonString, Recipient.class);
    		String recipientId = "R"+recipient.getPhone().substring(5);
    		recipient.setId(recipientId);
    		return putRecipient(recipient);
    	}
    	
    	return false;
    	
    }
    
    public boolean putDonor(Donor donor) {
    	try {
    		DynamoDbTable<Donor> table = this.enhancedClinet.table(this.tableName,TableSchema.fromBean(Donor.class));
    		
    		//put the donor into the DynamoDb Table
    		table.putItem(donor);
    		
    	} catch(DynamoDbException exp) {
    		//System.err.println(exp);
    		return false;
    	}
    	return true;
    }
    
    public boolean putRecipient(Recipient recipient) {
    	try {
    		DynamoDbTable<Recipient> table = this.enhancedClinet.table(this.tableName,TableSchema.fromBean(Recipient.class));
    		
    		//put the recipient into the DynamoDb Table
    		table.putItem(recipient);
    	} catch(DynamoDbException exp) {
    		//System.err.println(exp);
    		return false;
    	}
    	return true;
    }
    
    public APIGatewayProxyResponseEvent generateResponse(boolean success) {
    	APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    	HashMap<String,String> headers = responseHeaders();
    	
    	response.setStatusCode(success ? 200:500);
    	response.setHeaders(headers);
    	response.setBody(success ? "OK":"Internal Server Error");
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
