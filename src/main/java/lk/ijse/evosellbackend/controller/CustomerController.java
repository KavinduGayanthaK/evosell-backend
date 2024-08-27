package lk.ijse.evosellbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.evosellbackend.dto.CustomerDTO;
import lk.ijse.evosellbackend.persistance.CustomerData;
import lk.ijse.evosellbackend.persistance.impl.CustomerDataImpl;
import lk.ijse.evosellbackend.util.UtilProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/customer")
public class CustomerController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    Connection connection;
    CustomerData customerData = new CustomerDataImpl();
    CustomerDTO customerDTO = new CustomerDTO();

    @Override
    public void init() throws ServletException {
        logger.info("Initialization customerController with call init  method");
        try{
            var cdx = new InitialContext();
            DataSource pool = (DataSource) cdx.lookup("java:comp/env/jdbc/studentRegistration");//methana gana resourse eka onama ekak wenna puluwan eka api narrow cast krnawa dta source ekak wdiyata
            this.connection = pool.getConnection();

        }catch (NamingException | SQLException e) {
            logger.error("init failed with: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!req.getContentType().toLowerCase().contains("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        Jsonb jsonb = JsonbBuilder.create();
        customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);


        try (var writer = resp.getWriter()) {
            boolean saveCustomer = customerData.save(customerDTO, this.connection);

            if (saveCustomer) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                writer.write("Customer saved");
                logger.info("Customer Saved");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nic = req.getParameter("nic");
        System.out.println(nic);

        List<CustomerDTO> customerDTOList = new ArrayList<>();

        try(var writer = resp.getWriter()){
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            if (nic==null){
                customerDTOList = customerData.get(this.connection);
                writer.write(jsonb.toJson(customerDTOList));
            }else{
                customerDTO = customerData.getCustomerId(nic,this.connection);
                System.out.println("sdsd");
                writer.write(jsonb.toJson(customerDTO));
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().contains("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        Jsonb jsonb = JsonbBuilder.create();
        customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        try (var writer = resp.getWriter()) {
            boolean updateCustomer = customerData.update(customerDTO, this.connection);

            if (updateCustomer) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                writer.write("Customer Updated");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            String id = req.getParameter("id");
            boolean deleteStudent = customerData.delete(id, this.connection);

            if (deleteStudent) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                writer.write("Delete Customer");
                System.out.println("Delete Customer");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Student delete failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
