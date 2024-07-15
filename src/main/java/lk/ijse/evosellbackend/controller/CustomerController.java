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

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(value = "/customer")
public class CustomerController extends HttpServlet {

    Connection connection;
    CustomerData customerData = new CustomerDataImpl();
    CustomerDTO customerDTO = new CustomerDTO();

    @Override
    public void init() throws ServletException {
        try {
            var driverClass = getServletContext().getInitParameter("driver-class");
            var dbUrl = getServletContext().getInitParameter("dbURL");
            var dbUserName = getServletContext().getInitParameter("dbUserName");
            var dbPassword = getServletContext().getInitParameter("dbPassword");
            Class.forName(driverClass);
            this.connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!req.getContentType().toLowerCase().contains("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        Jsonb jsonb = JsonbBuilder.create();
        customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        customerDTO.setCustomerId(UtilProcess.generateID());

        try (var writer = resp.getWriter()) {
            System.out.println(customerDTO.getCustomerId());
            boolean saveCustomer = customerData.save(customerDTO, this.connection);

            if (saveCustomer) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                writer.write("Customer saved");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }
}
