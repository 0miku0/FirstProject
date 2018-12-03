package WebStore.service.interfaces;

import WebStore.bean.ShoppingItem;

import java.sql.SQLException;
import java.util.List;

public interface CartService {
    boolean addCart(String uid, String pid,String snum) throws SQLException;

    List<ShoppingItem> getShoppingItems(String uid) throws SQLException;

    boolean delOneItem(String uid, String pid) throws SQLException;
}
