package lk.ijse.evosellbackend.persistance;

import lk.ijse.evosellbackend.dto.ItemDTO;

import java.sql.Connection;


public interface ItemData {
    boolean save(ItemDTO itemDTO, Connection connection);

    boolean update(ItemDTO itemDTO, Connection connection);

    boolean delete(String id, Connection connection);
}
