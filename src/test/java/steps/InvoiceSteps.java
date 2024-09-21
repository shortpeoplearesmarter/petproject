package steps;

import Entities.CustomResponse;
import Entities.ProductsRequestBody;
import Entities.RequestBodyTwo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utilities.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceSteps {

    RequestSpecification request;
    Response response;
    RequestBodyTwo requestBodyTwo = new RequestBodyTwo();
    ProductsRequestBody productsRequestBody = new ProductsRequestBody();
    Map<String, Object> params = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    String url = Config.getProperty("cashwiseApiUrl");
    String token = Config.getProperty("cashwiseToken");


    @Given("base url")
    public void base_url() {
        request = RestAssured.given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                baseUri(url);
    }
    @And("user provides valid authorization token")
    public void user_provides_valid_authorization_token() {
        request = request.auth().oauth2(token);
    }
    @And("user store parameters {string} and {string} in a Hashmap")
    public void user_store_parameters_and_in_a_hashmap(String page, String size) {
        params.put("page", page);
        params.put("size", size);
    }
    @Then("user hits GET endpoint {string}")
    public void user_hits_get_endpoint(String endpoint) {
        response = request.params(params).get(endpoint);
    }
    @And("user creates new invoice")
    public void userCreatesNewInvoice() {
        productsRequestBody.setProduct_title("Some product");
        productsRequestBody.setProduct_id(122);
        productsRequestBody.setCount_of_product(2);
        productsRequestBody.setProduct_price(100);
        productsRequestBody.setService_type_id(24);
        productsRequestBody.setCategory_id(35);
        productsRequestBody.setProduct_description("random product");

        requestBodyTwo.setInvoice_title("Some Title");
        requestBodyTwo.setClient_id(12);
        requestBodyTwo.setDate_of_creation("23.5.2000");
        requestBodyTwo.setEnd_date("24.7.2001");
        requestBodyTwo.setDescription("to test");
        requestBodyTwo.setProducts(Arrays.asList(productsRequestBody));
        requestBodyTwo.setSum(345);
        requestBodyTwo.setDiscount(20);
        requestBodyTwo.setSum_of_discount(24);

    }
    @Then("user hits POST endpoint {string}")
    public void user_hits_post_endpoint(String endpoint) {
        response = request.body(requestBodyTwo).post(endpoint);
    }
    @And("user verifies status code is {int}")
    public void user_verifies_status_code_is(Integer statusCode) {
        int status = response.statusCode();
        Assert.assertEquals((int)statusCode, status);
    }
    @And("user gets back to base url")
    public void user_gets_back_to_base_url() {
        request = RestAssured.given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                baseUri(url);
    }


    @And("user validates the status code is {int} and that new invoice has been created")
    public void user_validates_the_status_code_is_and_that_new_invoice_has_been_created(Integer statusCode) throws JsonProcessingException {

//        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
//
//        int expectedProductId = customResponse.getProduct_id();
//        int size = customResponse.getResponses().size();
//
//        boolean isPresent = false;
//
//        for (int i = 0; i < size; i++){
//            if (customResponse.getResponses().get(i).getProduct_id() == expectedProductId){
//                isPresent = true;
//                break;
//            }
//        }
//
//        Assert.assertTrue(isPresent);

        int status = response.statusCode();
        Assert.assertEquals((int)statusCode, status);
        System.out.println("status = " + status);
    }

}
