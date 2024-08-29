package lk.ijse.evosellbackend.persistance;

import lk.ijse.evosellbackend.dto.OrderDetailsDTO;

import java.sql.Connection;
import java.util.List;

public interface OrderDetiailsData {
    boolean save(OrderDetailsDTO orderDetailsDTO, Connection connection);


    List<OrderDetailsDTO> getAll(Connection connection);
}
