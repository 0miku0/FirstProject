package WebStore.utils;

import WebStore.DatabaseAccessObject.implments.MyProductDAO;
import WebStore.bean.Product;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class test {
    @Test
    public void test() throws SQLException {
        String[] split = "1111111.22222.3333".split("\\.");
        System.out.println(split[2]);
    }
}
