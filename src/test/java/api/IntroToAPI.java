package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class IntroToAPI {
    public static void main(String[] args) {

        String baseUri = "https://backend.cashwise.us";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MjQ5Nzg4MDksImlhdCI6MTcyMjM4NjgwOSwidXNlcm5hbWUiOiJTQWRhc2Rhc2RAR21haWwuY29tIn0.ifFTocP1yN3kAidkao1Z1oriUICeZ8IYU9CKkpMxHiflicU_HZVDFb1dfY9L5TrpXRPedXRNKR93PzXCjlxRPQ";

//            RestAssured.given()
//                .auth()
//                .oauth2(token)
//                .baseUri(baseUri)
//                .and()
//                .queryParam("isArchived", false)
//                .queryParam("page", 1)
//                .queryParam("size", 2)
//                .when()
//                .get("/api/myaccount/sellers")
//                .then()
//                .statusCode(200);

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 2);
        Response response = RestAssured.given().auth().oauth2(token).
                params(params).get(baseUri + "/pages/myaccount/sellers");
        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());

        String sellerId = response.jsonPath().get("responses.seller_id[0]").toString();
        System.out.println(sellerId);

        Response response1 = RestAssured.given().auth().oauth2(token).
                get(baseUri + "/pages/myaccount/sellers/" + sellerId);
        System.out.println(response1.prettyPrint());

        String actualSellerId = response1.jsonPath().get("seller_id").toString();
        Assert.assertEquals(sellerId, actualSellerId);



    }
}