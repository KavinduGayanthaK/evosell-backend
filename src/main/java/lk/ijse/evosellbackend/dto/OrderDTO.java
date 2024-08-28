package lk.ijse.evosellbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    private String orderId;
    private String customerId;
    private String customerName;
    private String customerNic;



}
