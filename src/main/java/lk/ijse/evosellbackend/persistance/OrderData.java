package lk.ijse.evosellbackend.persistance;

import lk.ijse.evosellbackend.dto.OrderDTO;

import java.sql.Connection;
import java.util.List;

public interface OrderData {
    boolean save(OrderDTO orderDTO, Connection connection);

    List<OrderDTO> getAllOrder(Connection connection);

}
