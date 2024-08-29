package lk.ijse.evosellbackend.persistance.impl;

import lk.ijse.evosellbackend.dto.OrderDetailsDTO;
import lk.ijse.evosellbackend.persistance.OrderDetiailsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDataImpl implements OrderDetiailsData {
    Logger logger = LoggerFactory.getLogger(OrderDetailsDataImpl.class);
    @Override
    public boolean save(OrderDetailsDTO orderDetailsDTO, Connection connection) {
        String SAVE_ORDER_DETAILS = "INSERT INTO orderDetails VALUES(?,?,?,?,?,?,?,?)";
        try{
            var ps = connection.prepareStatement(SAVE_ORDER_DETAILS);
            ps.setString(1,orderDetailsDTO.getOrderId());
            ps.setString(2,orderDetailsDTO.getItemCode());
            ps.setString(3,orderDetailsDTO.getItemName());
            ps.setString(4,orderDetailsDTO.getItemQty());
            ps.setString(5,orderDetailsDTO.getTotal());
            ps.setString(6,orderDetailsDTO.getDiscount());
            ps.setString(7,orderDetailsDTO.getNetTotal());
            ps.setString(8,orderDetailsDTO.getDate());
            return ps.executeUpdate() != 0 ;
        }catch (SQLException e){
            logger.error("Order details save failed : "+e.getMessage());
            return false;
        }
    }

    @Override
    public List<OrderDetailsDTO> getAll(Connection connection) {
        String GET_ALL_ORDER_DETAILS = "SELECT * FROM orderDetails";
        List<OrderDetailsDTO> orderDetailsDTOS =  new ArrayList<>();
        try{
            var ps = connection.prepareStatement(GET_ALL_ORDER_DETAILS);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                orderDetailsDTOS.add(
                        new  OrderDetailsDTO(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8)
                        ));
            };
            return orderDetailsDTOS;
        } catch (SQLException e) {
            logger.error("Get Order Details Failed : " +e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
