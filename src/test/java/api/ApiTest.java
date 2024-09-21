package api;

import Entities.CustomResponse;
import Entities.RequestBodyTwo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CAshwiseToken;
import utilities.Config;

import java.security.interfaces.RSAKey;

public class ApiTest {
    @Test
    public void CreateSeller() {
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = Config.getProperty("cashwiseToken");
        RequestBodyTwo requestBody = new RequestBodyTwo();
        requestBody.setCompany_name("WiseCode");
        requestBody.setSeller_name("Nurgazy");
        requestBody.setEmail("wisedfvd@gmail.com");
        requestBody.setPhone_number("1231232233");
        requestBody.setAddress("Earth");

        //creating new response

        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(url);

        int status = response.statusCode();

        Assert.assertEquals(201, status);



    }
}
