package steps;

import Entities.RequestBodyTwo;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.it.Ma;
import org.apiguardian.api.API;
import org.junit.Assert;
import utilities.APIRunner;

import java.util.HashMap;
import java.util.Map;

public class ApiSteps {

    Faker faker = new Faker();
    String email;
    String sellerNAme;
    int sellerId;

    @Given("user hits Get single seller api with {string}")
    public void user_hits_get_single_seller_api_with(String endpoint) {
        APIRunner.runGET(endpoint, 5022);
        sellerId = APIRunner.getCustomResponse().getSeller_id();
    }
    @Then("verify seller email is not empty")
    public void verify_seller_email_is_not_empty() {
        String email = APIRunner.getCustomResponse().getEmail();
        Assert.assertFalse(email.isEmpty());
    }

    @Given("user hits Get all seller api with {string}")
    public void user_hits_get_all_seller_api_with(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("size", 110);
        params.put("isArchived", false);

        APIRunner.runGET(endpoint, params);

    }
    @Then("verify seller ids are not equal to {int}")
    public void verify_seller_ids_are_not_equal_to(int int1) {
        int size = APIRunner.getCustomResponse().getResponses().size();

        for (int i = 0; i < size; i++){
            int sellerID = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            Assert.assertNotEquals(int1, sellerID);
        }
    }


    @Then("user hits put api with {string}")
    public void user_hits_put_api_with(String endpoint) {
        RequestBodyTwo requestBodyTwo = new RequestBodyTwo();
        requestBodyTwo.setCompany_name(faker.company().name());
        requestBodyTwo.setSeller_name(faker.name().firstName());
        requestBodyTwo.setEmail(faker.internet().emailAddress());
        requestBodyTwo.setAddress(faker.address().fullAddress());
        requestBodyTwo.setPhone_number(faker.phoneNumber().phoneNumber());

        APIRunner.runPUT(endpoint, requestBodyTwo, 5022);
        email = APIRunner.getCustomResponse().getEmail();
        sellerNAme = APIRunner.getCustomResponse().getSeller_name();
    }

    @Then("verify seller email was updated")
    public void verify_seller_email_was_updated() {
       Assert.assertFalse(email.isEmpty());
    }

    @Then("verify user first name was updated")
    public void verify_user_first_name_was_updated() {
        Assert.assertFalse(sellerNAme.isEmpty());
    }

    @Then("user hits archive api with endpoint {string}")
    public void user_hits_archive_api_with_endpoint(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", sellerId);
        params.put("archive", true);

        APIRunner.runPOST(endpoint, params);
    }

    @Then("user hits get all seller api with {string}")
    public void userHitsGetAllSellerApiWith(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("size", 110);
        params.put("isArchived", true);

        APIRunner.runGET(endpoint, params);

        int size = APIRunner.getCustomResponse().getResponses().size();

        boolean isPresent = false;

        for (int i = 0; i < size; i++){
            int ids = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();

            if (sellerId == ids){
                isPresent = true;
                break;
            }
        }
        Assert.assertTrue(isPresent);
    }

    @Given("user hits post api with {string}")
    public void user_hits_post_api_with(String endpoint) {
        RequestBodyTwo requestBodyTwo = new RequestBodyTwo();
        requestBodyTwo.setCompany_name("whatevernd");
        requestBodyTwo.setSeller_name("whatever");
        requestBodyTwo.setEmail(faker.internet().emailAddress());
        requestBodyTwo.setPhone_number("354457");
        requestBodyTwo.setAddress(faker.address().fullAddress());

        APIRunner.runPOST(endpoint, requestBodyTwo);
        sellerId = APIRunner.getCustomResponse().getSeller_id();
        sellerNAme = APIRunner.getCustomResponse().getSeller_name();
    }

    @Then("verify seller id was generated")
    public void verify_seller_id_was_generated() {
        Assert.assertTrue(sellerId != 0);
    }
    @Then("verify seller name is not empty")
    public void verify_seller_name_is_not_empty() {
        Assert.assertFalse(sellerNAme.isEmpty());
    }
    @Then("delete the seller with {string}")
    public void delete_the_seller_with(String endpoint) {
        APIRunner.runDELETE(endpoint + sellerId);
    }

    @Then("verify deleted seller is not in the list")
    public void verify_deleted_seller_is_not_in_the_list() {

        boolean isEmpty = true;

        int size = APIRunner.getCustomResponse().getResponses().size();

        for (int i = 0; i < size; i++){
            int id = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            if (id != sellerId){
                isEmpty = false;
                break;
            }
        }
        Assert.assertFalse(isEmpty);
    }
}
