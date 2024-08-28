package lk.ijse.evosellbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.evosellbackend.dto.ItemDTO;
import lk.ijse.evosellbackend.persistance.ItemData;
import lk.ijse.evosellbackend.persistance.impl.ItemDataImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/item")
public class ItemController extends HttpServlet {


    Connection connection;
    ItemDTO itemDTO = new ItemDTO();
    ItemData itemData = new ItemDataImpl();
    static Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Override
    public void init()  {
        try {
            var cdx = new InitialContext();
            DataSource pool =(DataSource) cdx.lookup("java:comp/env/jdbc/studentRegistration");
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
        itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);

        try(var writer = resp.getWriter()) {
            boolean save = itemData.save(itemDTO, this.connection);
            if (save) {
                logger.info("Item saved Successful");
                writer.write("saved");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                logger.error("Item saved unsuccessful");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

}
