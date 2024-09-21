package Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {

    private Long seller_id;
    private String company_name;
    private String seller_name;
    private String seller_surname;
    private String email;
    private String phone_number;
    private String address;
    private String created;
    private Boolean income;
    private int number_of_invoices;
}
