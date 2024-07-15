package lk.ijse.evosellbackend.persistance.impl;

import lk.ijse.evosellbackend.dto.CustomerDTO;
import lk.ijse.evosellbackend.persistance.CustomerData;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerDataImpl implements CustomerData {
    @Override
    public boolean save(CustomerDTO customerDTO, Connection connection) {
        String SAVE_CUSTOMER = "INSERT INTO customer VALUES(?,?,?,?,?)";
        try{
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1, customerDTO.getCustomerId());
            ps.setString(2, customerDTO.getCustomerName());
            ps.setString(3, customerDTO.getCustomerAddress());
            ps.setString(4, customerDTO.getCustomerEmail());
            ps.setString(5, customerDTO.getCustomerNic());

            return ps.executeUpdate() !=0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
