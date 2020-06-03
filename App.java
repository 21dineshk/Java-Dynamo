package com.ensemble;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;

import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {
    static final Logger log = LoggerFactory.getLogger(App.class);
    public static Object handleRequest(Request request,Context context) throws ResourceNotFoundException{
        AmazonDynamoDB client= AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDBMapper mapper=new DynamoDBMapper(client);
        Student student=null;
        switch(request.getHttpMethod()){
            case "GET":
                student =mapper.load(Student.class,request.getId());
                if(student==null){
                    throw new ResourceNotFoundException("Resource not found:"+request.getId());
                }
                return student;
            case "POST":
                student=request.getStudent();
                mapper.save(student);
                return student;
            default:
                break;
        }
        return null;
    }


}
