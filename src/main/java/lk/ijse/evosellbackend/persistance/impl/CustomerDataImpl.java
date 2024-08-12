package lk.ijse.evosellbackend.persistance.impl;

import lk.ijse.evosellbackend.dto.CustomerDTO;
import lk.ijse.evosellbackend.persistance.CustomerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDataImpl implements CustomerData {
    @Override
    public boolean save(CustomerDTO customerDTO, Connection connection) {
        String SAVE_CUSTOMER = "INSERT INTO customer VALUES(?,?,?,?,?)";
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1, customerDTO.getCustomerId());
            ps.setString(2, customerDTO.getCustomerName());
            ps.setString(3, customerDTO.getCustomerAddress());
            ps.setString(4, customerDTO.getCustomerEmail());
            ps.setString(5, customerDTO.getCustomerNic());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<CustomerDTO> get(Connection connection) {
        String GET_ALL_CUSTOMER = "SELECT * FROM customer";
        List<CustomerDTO> customerDTOList = new ArrayList<>();

        try {
            var ps = connection.prepareStatement(GET_ALL_CUSTOMER);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                customerDTOList.add(new CustomerDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }
            return customerDTOList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CustomerDTO getCustomerId(String nic, Connection connection) {
        String GET_CUSTOMER_ID = "SELECT * FROM customer WHERE nic = ?";
        try {
            var ps = connection.prepareStatement(GET_CUSTOMER_ID);
            ps.setString(1, nic);
            ResultSet resultSet = ps.executeQuery();
            CustomerDTO customerDTO = new CustomerDTO();
            while (resultSet.next()) {

                customerDTO.setCustomerId(resultSet.getString(1));
                customerDTO.setCustomerName(resultSet.getString(2));
                customerDTO.setCustomerAddress(resultSet.getString(3));
                customerDTO.setCustomerEmail(resultSet.getString(4));
                customerDTO.setCustomerNic(resultSet.getString(5));
            }
            return customerDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(CustomerDTO customerDTO, Connection connection) {
        String UPDATE_CUSTOMER = "UPDATE customer SET name = ?,address=?,email=?,nic=? WHERE id=?";
        try {
            var ps = connection.prepareStatement(UPDATE_CUSTOMER);

            ps.setString(1, customerDTO.getCustomerName());
            ps.setString(2, customerDTO.getCustomerAddress());
            ps.setString(3, customerDTO.getCustomerEmail());
            ps.setString(4, customerDTO.getCustomerNic());
            ps.setString(5, customerDTO.getCustomerId());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id, Connection connection) {
        String DELETE_CUSTOMER = "DELETE FROM customer WHERE id = ?";
        try {
            var ps = connection.prepareStatement(DELETE_CUSTOMER);
            ps.setString(1,id);
            return ps.executeUpdate()!=0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
