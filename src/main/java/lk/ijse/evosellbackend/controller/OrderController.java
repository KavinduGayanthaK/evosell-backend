package lk.ijse.evosellbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.evosellbackend.dto.OrderDTO;
import lk.ijse.evosellbackend.dto.OrderDetailsDTO;
import lk.ijse.evosellbackend.persistance.OrderData;
import lk.ijse.evosellbackend.persistance.OrderDetiailsData;
import lk.ijse.evosellbackend.persistance.impl.OrderDataImpl;
import lk.ijse.evosellbackend.persistance.impl.OrderDetailsDataImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/order")
public class OrderController extends HttpServlet {
    Connection connection;
    OrderDTO orderDTO = new OrderDTO();
    OrderData orderData = new OrderDataImpl();
    OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
    OrderDetiailsData orderDetiailsData = new OrderDetailsDataImpl();
    static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Override
    public void init() throws ServletException {
        logger.info("Initialization orderController with call init  method");
        try{
            var cdx = new InitialContext();
            DataSource pool = (DataSource) cdx.lookup("java:comp/env/jdbc/studentRegistration");
            this.connection = pool.getConnection();

        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().contains("application/json") || req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        Jsonb jsonb = JsonbBuilder.create();
        orderDetailsDTO = jsonb.fromJson(req.getReader(), OrderDetailsDTO.class);
        orderDTO.setOrderId(orderDetailsDTO.getOrderId());
        orderDTO.setCustomerId(orderDetailsDTO.getCustomerId());
        orderDTO.setCustomerName(orderDetailsDTO.getCustomerName());
        orderDTO.setCustomerNic(orderDetailsDTO.getCustomerNic());

        boolean save = orderData.save(orderDTO,this.connection);
        if (save){
            resp.setStatus(HttpServletResponse.SC_CREATED);
            logger.info("Order Saved");
            boolean saveOrderDetails = orderDetiailsData.save(orderDetailsDTO,this.connection);
            if (saveOrderDetails){
                resp.setStatus(HttpServletResponse.SC_CREATED);
                logger.info("Order Details Saved");
            }else{
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.info("Order Details Save Failed");
            }
        }else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.info("Order Save Failed");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<OrderDTO> orderDTOList;
        List<OrderDetailsDTO> orderDetailsDTOList;
        List<OrderDetailsDTO> combinedDTOS = new ArrayList<>();

        try(var writer = resp.getWriter()){
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();

            orderDTOList = orderData.getAllOrder(this.connection);
            orderDetailsDTOList = orderDetiailsData.getAll(this.connection);

            for(OrderDTO orderDTO :orderDTOList) {
                for(OrderDetailsDTO orderDetailsDTO1 : orderDetailsDTOList){
                    if (orderDTO.getOrderId().equals(orderDetailsDTO1.getOrderId())){
                        OrderDetailsDTO combinedDetailsDTO = new OrderDetailsDTO(
                                orderDTO.getOrderId(),
                                orderDTO.getCustomerId(),
                                orderDTO.getCustomerNic(),
                                orderDTO.getCustomerName(),
                                orderDetailsDTO1.getItemCode(),
                                orderDetailsDTO1.getItemName(),
                                orderDetailsDTO1.getItemQty(),
                                orderDetailsDTO1.getTotal(),
                                orderDetailsDTO1.getDiscount(),
                                orderDetailsDTO1.getNetTotal(),
                                orderDetailsDTO1.getDate()
                        );
                        combinedDTOS.add(combinedDetailsDTO);
                    }
                }
            }
            System.out.println(combinedDTOS);
            writer.write(jsonb.toJson(combinedDTOS));
        }
    }
}
