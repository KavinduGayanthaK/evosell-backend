package lk.ijse.evosellbackend.persistance;

import lk.ijse.evosellbackend.dto.CustomerDTO;

import java.sql.Connection;

public interface CustomerData {
    boolean save(CustomerDTO customerDTO, Connection connection);
}
