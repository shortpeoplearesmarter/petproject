package pages;

import Entities.ResponseFromSeller;
import Entities.SellerDTO;
import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.HashMap;

public class main {
    public static void main(String[] args) throws IOException {


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create("", mediaType);
        String bearerToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MjYxMDQ2MjIsImlhdCI6MTcyMzUxMjYyMiwidXNlcm5hbWUiOiJhamdlcmFzeWR5a292YTJAZ21haWwuY29tIn0.CBE0U4t7cKgFTtyNp-A5htGd9fdtj-T9Wz7_Fo2rpHmX18Ov26eYGXQFwCpXIC-9jfjxsc4E1rjS2JfUeBo8-w";
        Request request = new Request.Builder()
                .url("https://backend.cashwise.us/api/myaccount/sellers?isArchived=false&page=1&size=100")
                .method("GET", null)
                .addHeader("Authorization", bearerToken)
                .build();
        Response response = client.newCall(request).execute();


        ResponseBody body = response.body();
        String responseBeforeJson = body.string();
        System.out.println("response.body().toString() = " + response);   // way of converting to json
        Gson gson = new Gson();

        System.out.println("here we have output as a json -> " + gson.toJson(responseBeforeJson));  // from json
//
        ResponseFromSeller responseFromSeller = gson.fromJson(responseBeforeJson, ResponseFromSeller.class);

        System.out.println("responseFromSeller = " + responseFromSeller);

        for (SellerDTO respons : responseFromSeller.getResponses()) {
            System.out.println(respons);
        }

//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", bearerToken);
//        headers.set("Accept", org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
//
//        ResponseEntity<ResponseFromSeller> responseEntity = restTemplate.exchange(
//                "https://backend.cashwise.us/api/myaccount/sellers?isArchived=false&page=1&size=100",
//                HttpMethod.GET,
//                new HttpEntity<>(headers),
//                ResponseFromSeller.class
//        );

//        ResponseFromSeller responseBody = responseEntity.getBody();
//
//        System.out.println(" -------------------------------------------------");
//        System.out.println(responseBody);
//
//        for (SellerDTO respons : responseBody.getResponses()) {
//            System.out.println(respons);
//        }
//    }


}}
