package WebStore.DatabaseAccessObject.interfaces;

import WebStore.bean.ShoppingItem;
import WebStore.utils.TranscationUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface CartDAO {
    List<ShoppingItem> getShoppingItems(String uid) throws SQLException;

    boolean addCart(String uid, String pid,String snum) throws SQLException;

    boolean delOneItem(String uid, String pid) throws SQLException;

    void updateCart(int uid, String[] pids, TranscationUtils transcationUtils) throws SQLException;
}
