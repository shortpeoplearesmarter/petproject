package Entities;

import lombok.Data;

import java.util.List;

@Data
public class RequestBodyTwo {
    private String password;

    //create a category
    private String category_title;
    private String category_description;
    private boolean flag;

    //create 15 sellers
    private String company_name;
    private String seller_name;
    private String email;
    private String phone_number;
    private String address;

    //archive sellers
    private Integer sellersIdsForArchive;
    private boolean archive;

    //"invoice_title": "string",
    //  "client_id": 0,
    //  "date_of_creation": "string",
    //  "end_date": "string",
    //  "description": "string",

    private String invoice_title;
    private int client_id;
    private String date_of_creation;
    private String end_date;
    private String description;

    List<ProductsRequestBody> products;

    //"sum": 0,
    //  "discount": 0,
    //  "sum_of_discount": 0

    private int sum;
    private int discount;
    private int sum_of_discount;









}

