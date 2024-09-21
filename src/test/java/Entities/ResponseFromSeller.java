package Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFromSeller {

    private List<SellerDTO> responses;

    private int total_page;
    private String expired;
    private Integer all_unpaid_sum;
    private Integer cash_in_flow;
    private Integer cash_out_flow;
    private Integer net_amount;
    private Integer number_of_archived_clients;
    private Integer number_of_non_archived_clients;
    private Integer number_of_invoice_or_expense;

}
