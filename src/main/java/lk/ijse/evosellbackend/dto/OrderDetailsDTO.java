package lk.ijse.evosellbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailsDTO {
    private String orderId;
    private String customerId;
    private String customerNic;
    private String customerName;
    private String itemCode;
    private String itemName;
    private String itemQty;
    private String total;
    private String discount;
    private String netTotal;
    private String date;

    public OrderDetailsDTO(String orderId, String itemCode, String itemName, String itemQty, String total, String discount, String netTotal  ,String date) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.total = total;
        this.discount = discount;
        this.netTotal = netTotal;
        this.date = date;
    }


}

