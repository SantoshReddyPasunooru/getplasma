
package com.project.aws;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * The module containing all dependencies required by the {@link App}.
 */
public class DependencyFactory {

    private DependencyFactory() {}

    /**
     * @return an instance of DynamoDbClient
     */
    public static DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.create();
    }
    
    //return an instance of DynamoDbEnhancedClient
    public static DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient ddbClient) {
    	return DynamoDbEnhancedClient.builder()
				.dynamoDbClient(ddbClient)
				.build();
    }
}
