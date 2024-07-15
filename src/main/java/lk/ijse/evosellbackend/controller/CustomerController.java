package lk.ijse.evosellbackend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(value = "/customer")
public class CustomerController extends HttpServlet {

    Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            var driverClass = getServletContext().getInitParameter("driver-class");
            var dbUrl = getServletContext().getInitParameter("dbURL");
            var dbUserName = getServletContext().getInitParameter("dbPassword");
            var dbPassword = getServletContext().getInitParameter("dbUserName");
            Class.forName(driverClass);
            this.connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();;
        }
    }
}
