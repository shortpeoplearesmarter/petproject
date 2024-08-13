package api;

import Entities.RequestBody;
import io.cucumber.java.hu.Ha;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CAshwiseToken;
import utilities.Config;

import java.util.*;

public class testClass {
    @Test
    public void testToken(){
        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBody requestBody = new RequestBody();

        requestBody.setEmail("ajgerasydykova2@gmail.com");
        requestBody.setPassword("Kaspersky1230");

        Response response =  RestAssured.given().contentType(ContentType.JSON).body(requestBody).post(endPoint);
        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);

        String token = response.jsonPath().getString("jwt_token");
        System.out.println(token);

    }

    @Test
    public void GetSingleSeller(){
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/" + 4607;
        String token = CAshwiseToken.getToken();

        Response response = RestAssured.given().auth().oauth2(token).get(url);

        String expectedEmail = response.jsonPath().getString("email");
        response.prettyPrint();

        Assert.assertFalse(expectedEmail.isEmpty());
        Assert.assertTrue(expectedEmail.endsWith(".com"));
    }

    @Test
    public void GetAllSEllers(){
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/";
        String token = CAshwiseToken.getToken();

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("size", 10);
        params.put("page", 1);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        int statusCode = response.statusCode();

        Assert.assertEquals(200, statusCode);


        String email = response.jsonPath().getString("responses[0].email");
        Assert.assertFalse(email.isEmpty());

        String email2 = response.jsonPath().getString("responses[1].email");
        Assert.assertFalse(email2.isEmpty());
    }

    @Test

    public void GetAllSellersLoop(){
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/";
        String token = CAshwiseToken.getToken();

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 10);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        int statusCode = response.statusCode();

        Assert.assertEquals(200, statusCode);

        List<String> emails = response.jsonPath().getList("responses.email");

        for (String email : emails){
            Assert.assertFalse(email.isEmpty());
        }


    }
}
