package WebStore.DatabaseAccessObject.test;

import WebStore.DatabaseAccessObject.implments.MyProductDAO;
import WebStore.bean.Product;
import org.junit.Test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProductTest {

    @Test
    public void testfindPageCategory() throws SQLException {
        MyProductDAO myProductDAO = new MyProductDAO();
        List<Product> pageCategory = myProductDAO.findPageObject(4, 0);
        System.out.println(pageCategory.size());
        for (int i=0;i<pageCategory.size();i++){
            Product product = pageCategory.get(i);
            System.out.println(product.getPname());
            System.out.println(product.getDesc());
        }

    }

    @Test
    public void testfindPageObjectMultiCondition() throws SQLException {
        MyProductDAO myProductDAO = new MyProductDAO();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pid","13");
        stringStringHashMap.put("cid","7");
        stringStringHashMap.put("pname","恍惚");
        stringStringHashMap.put("minPrice","100");
        stringStringHashMap.put("maxPrice","");
        List<Product> pageCategory = myProductDAO.findPageObjectMultiCondition
                (3,0,stringStringHashMap);
        System.out.println(pageCategory.size());
        for (int i=0;i<pageCategory.size();i++){
            Product product = pageCategory.get(i);
            System.out.println(product.getPname());
            System.out.println(product.getDesc());
        }
    }
    @Test
    public void testfindPageObjectByNname() throws SQLException {
        MyProductDAO myProductDAO = new MyProductDAO();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pname","恍惚");
        List<Product> pageCategory = myProductDAO.findPageObjectMultiCondition(3,0,stringStringHashMap);
        System.out.println(pageCategory.size());
        for (int i=0;i<pageCategory.size();i++){
            Product product = pageCategory.get(i);
            System.out.println(product.getPname());
            System.out.println(product.getDesc());
        }

    }
}
