package Entities;

import lombok.Data;

import java.util.List;

@Data
public class ProductsRequestBody {

    //"product_title": "string",
    //      "product_id": 0,
    //      "count_of_product": 0,
    //      "product_price": 0,
    //      "service_type_id": 0,
    //      "category_id": 0,
    //      "product_description": "string"

    private String product_title;
    private int product_id;
    private int count_of_product;
    private int product_price;
    private int service_type_id;
    private int category_id;
    private String product_description;





}
