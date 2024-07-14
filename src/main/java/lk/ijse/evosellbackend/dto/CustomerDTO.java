package lk.ijse.evosellbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerEmail;
    private String customerNic;
}
