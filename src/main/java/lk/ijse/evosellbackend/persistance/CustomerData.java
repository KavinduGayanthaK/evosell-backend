package lk.ijse.evosellbackend.persistance;

import lk.ijse.evosellbackend.dto.CustomerDTO;

import java.sql.Connection;
import java.util.List;

public interface CustomerData {
    boolean save(CustomerDTO customerDTO, Connection connection);

    List<CustomerDTO> get(Connection connection);

    CustomerDTO getCustomerId(String nic, Connection connection);

    boolean update(CustomerDTO customerDTO, Connection connection);

    boolean delete(String id ,Connection connection);

}
