package utilities;

import Entities.CustomResponse;
import Entities.RequestBodyTwo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
public class APIRunner {

    @Getter
    private static CustomResponse customResponse;

    //get without params
    public static void runGET(String path, int i){
        String token = CAshwiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path + i;
        Response response = RestAssured.given().auth().oauth2(token).get(url);
        System.out.println("status code: " + response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("this is a single response");
        }
    }

    //get api with params
    public static void runGET(String path, Map<String, Object> params){
        String token = CAshwiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;
        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        System.out.println("status code: " + response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        try {
             customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("this is a single response");
        }
    }

    //post with requestbody
    public static void runPOST(String path, RequestBodyTwo requestBodyTwo){
        String token = CAshwiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;
        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).
                body(requestBodyTwo).post(url);
        System.out.println("status code: " + response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        try {
             customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("this is a single response");

        }
    }

    //post api with parameters
    public static void runPOST(String path, Map<String, Object> params){
        String token = CAshwiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;
        Response response = RestAssured.given().auth().oauth2(token).params(params).post(url);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("status code: " + response.statusCode());
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("this is a single response");
        }
    }

    //
    public static void runDELETE(String path){
        String token = CAshwiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;
        Response response = RestAssured.given().auth().oauth2(token).delete(url);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("status code: " + response.statusCode());
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("this is a single response");
        }
    }

    public static void runPUT(String path, RequestBodyTwo requestBodyTwo, int i){
        String token = CAshwiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path + i;
        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).
                body(requestBodyTwo).put(url);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("status code: " + response.statusCode());
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("this is a single response");
        }
    }


    }
