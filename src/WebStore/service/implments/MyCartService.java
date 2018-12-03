package WebStore.service.implments;

import WebStore.DatabaseAccessObject.implments.MyCartDAO;
import WebStore.DatabaseAccessObject.interfaces.CartDAO;
import WebStore.bean.ShoppingItem;
import WebStore.service.interfaces.CartService;

import java.sql.SQLException;
import java.util.List;

public class MyCartService implements CartService {
    CartDAO cartDAO=new MyCartDAO();
    @Override
    public boolean addCart(String uid, String pid,String snum) throws SQLException {
        return cartDAO.addCart(uid,pid,snum);
    }

    @Override
    public List<ShoppingItem> getShoppingItems(String uid) throws SQLException {
        return cartDAO.getShoppingItems(uid);
    }

    @Override
    public boolean delOneItem(String uid, String pid) throws SQLException {
        return cartDAO.delOneItem(uid,pid);
    }
}
