package lk.ijse.evosellbackend.persistance;

import lk.ijse.evosellbackend.dto.ItemDTO;

import java.sql.Connection;
import java.util.List;


public interface ItemData {
    boolean save(ItemDTO itemDTO, Connection connection);

    boolean update(ItemDTO itemDTO, Connection connection);

    boolean delete(String id, Connection connection);

    List<ItemDTO> getAll(Connection connection);

    ItemDTO getItemCode(String itemCode, Connection connection);

}
