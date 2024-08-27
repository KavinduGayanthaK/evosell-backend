package lk.ijse.evosellbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO {
    private String itemCode;
    private String itemName;
    private String qty;
    private String price;
}
