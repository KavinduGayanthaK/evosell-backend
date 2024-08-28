//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lk.ijse.evosellbackend.persistance.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.evosellbackend.dto.ItemDTO;
import lk.ijse.evosellbackend.persistance.ItemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemDataImpl implements ItemData {
    public ItemDataImpl() {
    }

    static Logger logger = LoggerFactory.getLogger(ItemDataImpl.class);

    public boolean save(ItemDTO itemDTO, Connection connection) {
        String ITEM_SAVE = "INSERT INTO item VALUES(?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(ITEM_SAVE);
            ps.setString(1, itemDTO.getItemCode());
            ps.setString(2, itemDTO.getItemName());
            ps.setString(3, itemDTO.getQty());
            ps.setString(4, itemDTO.getPrice());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error("Item save failed : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(ItemDTO itemDTO, Connection connection) {
        String ITEM_UPDATE = "UPDATE item SET name = ?, quantityOnHand = ?, price = ? WHERE itemCode =?";
        try {
            var ps = connection.prepareStatement(ITEM_UPDATE);

            ps.setString(1,itemDTO.getItemName());
            ps.setString(2,itemDTO.getQty());
            ps.setString(3,itemDTO.getPrice());
            ps.setString(4,itemDTO.getItemCode());

            return ps.executeUpdate()!=0;
        } catch (SQLException e) {
            logger.error("Item update failed : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id, Connection connection) {
        String DELETE_ITEM = "DELETE FROM item WHERE itemCode = ?";
        try {
            var ps = connection.prepareStatement(DELETE_ITEM);
            ps.setString(1,id);
            return ps.executeUpdate()!=0;
        }catch (SQLException e){
            logger.error("Item delete failed : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemDTO> getAll(Connection connection) {
        String GET_ITEM = "SELECT * FROM item";
        List<ItemDTO> itemDTOList = new ArrayList<>();
        try {
            var ps = connection.prepareStatement(GET_ITEM);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                itemDTOList.add(new ItemDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                ));
            }
            return itemDTOList;
        } catch (SQLException e) {
            logger.error("Item get failed : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemDTO getItemCode(String itemCode, Connection connection) {
        String GET_ITEM = "SELECT * FROM item WHERE itemCode =?";
        try{
            var ps = connection.prepareStatement(GET_ITEM);
            ps.setString(1,itemCode);
            ResultSet resultSet = ps.executeQuery();
            ItemDTO itemDTO = new ItemDTO();
            while (resultSet.next()){
                itemDTO.setItemCode(resultSet.getString(1));
                itemDTO.setItemName(resultSet.getString(2));
                itemDTO.setQty(resultSet.getString(3));
                itemDTO.setPrice(resultSet.getString(4));
            }
            return itemDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
