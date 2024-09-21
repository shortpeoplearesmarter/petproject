package pages;

import Entities.ResponseFromSeller;
import Entities.SellerDTO;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class APIChainig {
    public static void main(String[] args) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        String bearerToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MjYxMDQ2MjIsImlhdCI6MTcyMzUxMjYyMiwidXNlcm5hbWUiOiJhamdlcmFzeWR5a292YTJAZ21haWwuY29tIn0.CBE0U4t7cKgFTtyNp-A5htGd9fdtj-T9Wz7_Fo2rpHmX18Ov26eYGXQFwCpXIC-9jfjxsc4E1rjS2JfUeBo8-w";
        Request request = new Request.Builder()
                .url("https://backend.cashwise.us/api/myaccount/sellers?isArchived=false&page=1&size=100")
                .method("GET", null)
                .addHeader("Authorization", bearerToken)
                .build();
            Response response1 = client.newCall(request).execute();

            // Check for successful response
            if (!response1.isSuccessful()) {
                throw new IOException("Unexpected code " + response1);
            }

            String responseBody1 = response1.body().string();

            // Extract IDs from the response
            List<String> ids = extractIdsFromResponse(responseBody1);

            // Second API Calls for each ID
            for (String id : ids) {
                Request request2 = new Request.Builder()
                        .url("https://backend.cashwise.us/api/myaccount/sellers/" + id)
                        .build();
                Response response2 = client.newCall(request2).execute();

                if (!response2.isSuccessful()) {
                    System.err.println("Failed to fetch details for ID: " + id);
                    continue;
                }

                String responseBody2 = response2.body().string();
                System.out.println(responseBody2);
            }

            //Reparsing response1 for further processing
            Gson gson = new Gson();
            ResponseFromSeller responseFromSeller = gson.fromJson(responseBody1, ResponseFromSeller.class);

            System.out.println("responseFromSeller = " + responseFromSeller);

            for (SellerDTO respons : responseFromSeller.getResponses()) {
                System.out.println(respons);
            }

        }


    private static List<String> extractIdsFromResponse(String responseBody) {
        // Dummy implementation for extracting IDs
        // You need to parse the responseBody JSON to extract actual IDs
        List<String> ids = new ArrayList<>();
        // Implement the logic to parse responseBody and extract IDs
        // Example:
        // ids.add("some_id"); // Add extracted IDs here
        return ids;
    }
}
