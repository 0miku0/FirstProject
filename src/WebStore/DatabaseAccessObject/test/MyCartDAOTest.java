package WebStore.DatabaseAccessObject.test;

import WebStore.DatabaseAccessObject.implments.MyCartDAO;
import WebStore.bean.ShoppingItem;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class MyCartDAOTest {
    @Test
    public void test0() throws SQLException {
        MyCartDAO myCartDAO = new MyCartDAO();
        boolean b = myCartDAO.addCart("3", "20","2");
    }

    @Test
    public void test1() throws SQLException {
        MyCartDAO myCartDAO = new MyCartDAO();
        List<ShoppingItem> shoppingItems = myCartDAO.getShoppingItems("3");
        System.out.println(shoppingItems.size());
        ShoppingItem shoppingItem = shoppingItems.get(0);
        System.out.println(shoppingItem.getPid()+"--"+shoppingItem.getUid()+"---"+shoppingItem.getSnum()+"--"+shoppingItem.getProduct());

    }
}
