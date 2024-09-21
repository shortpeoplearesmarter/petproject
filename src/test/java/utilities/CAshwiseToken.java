package utilities;

import Entities.RequestBodyTwo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import okhttp3.RequestBody;

public class CAshwiseToken {

    public static String getToken(){

        //not hitting anything yet

        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBodyTwo requestBody = new RequestBodyTwo();

        requestBody.setEmail("ajgerasydykova2@gmail.com");
        requestBody.setPassword("Kaspersky1230");

        Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody).post(endPoint);

        return response.jsonPath().getString("jwt_token");
    }
}
