package WebStore.DatabaseAccessObject.test;

import WebStore.DatabaseAccessObject.interfaces.AdminDAO;
import WebStore.DatabaseAccessObject.implments.MyAdminDAO;
import WebStore.bean.Admin;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class AdminTest {
    @Test
    public void testfindPageObject() throws SQLException {
        AdminDAO adminDAO = new MyAdminDAO();
        List<Admin> pageObject = adminDAO.findPageObject(3, 0);
        System.out.println(pageObject.size());
        for (int i=0;i<pageObject.size();i++){
            Admin admin = pageObject.get(i);
            System.out.println(admin.getUsername());
            System.out.println(admin.getPassword());
        }

    }
}
