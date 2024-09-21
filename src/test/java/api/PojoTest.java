package api;

import Entities.CustomResponse;
import Entities.RequestBodyTwo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.google.common.util.concurrent.FakeTimeLimiter;
import io.cucumber.java.it.Ma;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CAshwiseToken;
import utilities.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PojoTest {

    Faker faker = new Faker();

    @Test
    public void CreateCategory() throws JsonProcessingException {

        // BASIC API HIT

        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/categories";
        String token = CAshwiseToken.getToken();

        RequestBodyTwo requestBodyTwo = new RequestBodyTwo();

        requestBodyTwo.setCategory_title("Testtt");
        requestBodyTwo.setCategory_description("ysfysTEST");
        requestBodyTwo.setFlag(true);

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBodyTwo).post(url);
        int status = response.statusCode();
        Assert.assertEquals(201, status);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        int id = customResponse.getCategory_id();
    }


    @Test
    public void create15Sellers(){

        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CAshwiseToken.getToken();

        RequestBodyTwo requestBodyTwo = new RequestBodyTwo();

        for (int i = 0; i < 15; i++){
            requestBodyTwo.setCompany_name(faker.name().title());
            requestBodyTwo.setSeller_name(faker.name().name());
            requestBodyTwo.setEmail(faker.internet().emailAddress());
            requestBodyTwo.setPhone_number(faker.number().digit());
            requestBodyTwo.setAddress(faker.address().fullAddress());

            Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).
                    body(requestBodyTwo).post(url);
            int status = response.statusCode();
            response.prettyPrint();
            Assert.assertEquals(201, status);
        }
    }
    @Test
    public void getAllSellers() throws JsonProcessingException {
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CAshwiseToken.getToken();

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 100);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        int status = response.statusCode();
        Assert.assertEquals(200, status);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        int size = customResponse.getResponses().size();

        for (int i = 0; i < size; i++){
            String email = customResponse.getResponses().get(i).getEmail();
            Assert.assertFalse(email.isEmpty());
        }
    }

    @Test
    public void CreateSellerName(){
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CAshwiseToken.getToken();

        RequestBodyTwo requestBodyTwo = new RequestBodyTwo();
        requestBodyTwo.setCompany_name("whatever");
        requestBodyTwo.setSeller_name("whateverNAme");
        requestBodyTwo.setPhone_number("35463");
        requestBodyTwo.setAddress("fgvedgv");

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).
                body(requestBodyTwo).post(url);

        int status = response.statusCode();
        Assert.assertEquals(201, status);
    }

    @Test
    public void archiveTest() {
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        String token = CAshwiseToken.getToken();

        Map<String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", 4988);
        params.put("archived", true);

        Response response = RestAssured.given().auth().oauth2(token).params(params).post(url);

        int status = response.statusCode();
        Assert.assertEquals(200, status);

    }

    //archive all active sellers and put it into Map
    @Test
    public void GetAllActiveSellers() throws JsonProcessingException {
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/";
        String token = CAshwiseToken.getToken();

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 50);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        response.prettyPrint();
        int statusCode = response.statusCode();

        //to get an id we need to serialize and deserialize
        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        String urlArchive = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        int size = customResponse.getResponses().size();
        for (int i = 0; i < size; i++){
            int id = customResponse.getResponses().get(i).getSeller_id();

            Map<String, Object> paramsToArchive = new HashMap<>();
            paramsToArchive.put("sellersIdsForArchive", id);
            paramsToArchive.put("archive", true);

            Response response1 = RestAssured.given().auth().oauth2(token).params(paramsToArchive).post(urlArchive);

            int status1 = response1.statusCode();

            Assert.assertEquals(200, status1);
        }
    }

    @Test
    public void UnarchiveSellers() throws JsonProcessingException {
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/";
        String token = CAshwiseToken.getToken();

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", true);
        params.put("page", 1 );
        params.put("size", 110 );

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int status = response.statusCode();
        Assert.assertEquals(200, status);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        String urlToArchive = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        int size = customResponse.getResponses().size();

        for(int i = 0; i < size; i ++ ){
            if (customResponse.getResponses().get(i).getEmail() != null){
                if(customResponse.getResponses().get(i).getEmail().endsWith("@hotmail.com")){
                    int id = customResponse.getResponses().get(i).getSeller_id();

                    Map<String, Object> paramsToArchive = new HashMap<>();

                    paramsToArchive.put("sellersIdsForArchive",id );
                    paramsToArchive.put("archive", false);

                    Response response1 = RestAssured.given().auth().oauth2(token).params(paramsToArchive).post(urlToArchive);

                    int status1 = response1.statusCode();

                    Assert.assertEquals(200, status1);
            }
            }
        }
    }
//create a single seller
    //get all sellers
    //get the created one by its id or email



    @Test
    public void createASingleSeller() throws JsonProcessingException {
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CAshwiseToken.getToken();

        RequestBodyTwo requestBodyTwo = new RequestBodyTwo();

        requestBodyTwo.setSeller_name("uniqu Name");
        requestBodyTwo.setCompany_name("Uniqu Company");
        requestBodyTwo.setEmail("uniqu@gmail.com");
        requestBodyTwo.setPhone_number("364457");
        requestBodyTwo.setAddress("uniqu address");

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBodyTwo).post(url);

        int status = response.statusCode();
        response.prettyPrint();
        Assert.assertEquals(201, status);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        int ExpectedSellerId = customResponse.getSeller_id();


        String url2 = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("size", 1000 );
        params.put("page", 1 );

        Response response1 = RestAssured.given().auth().oauth2(token).params(params).get(url2);
        int statusCode = response.statusCode();
        Assert.assertEquals(201, statusCode);

        CustomResponse customResponse1 = mapper.readValue(response1.asString(), CustomResponse.class);

        int size = customResponse1.getResponses().size();

        boolean isPresent = false;

        for(int i = 0; i < size; i ++ ){
            if(customResponse1.getResponses().get(i).getSeller_id() == ExpectedSellerId){
                isPresent = true;
                break;
            }
        }

        Assert.assertTrue(isPresent);






    }

}




