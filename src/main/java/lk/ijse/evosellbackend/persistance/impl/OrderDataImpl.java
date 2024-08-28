package lk.ijse.evosellbackend.persistance.impl;

import lk.ijse.evosellbackend.dto.OrderDTO;
import lk.ijse.evosellbackend.persistance.OrderData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDataImpl implements OrderData {
   static Logger logger = LoggerFactory.getLogger(OrderDataImpl.class);
    @Override
    public boolean save(OrderDTO orderDTO, Connection connection) {
         String SAVE_ORDER = "INSERT INTO orders VALUES(?,?,?,?)";
        try{
            var ps = connection.prepareStatement(SAVE_ORDER);
            ps.setString(1,orderDTO.getOrderId());
            ps.setString(2, orderDTO.getCustomerId());
            ps.setString(3,orderDTO.getCustomerNic());
            ps.setString(4,orderDTO.getCustomerName());
            return  ps.executeUpdate() !=0;
        }catch(SQLException e){
            logger.error("Order SavedFailed: "+e.getMessage());
            return false;
        }
    }


}


