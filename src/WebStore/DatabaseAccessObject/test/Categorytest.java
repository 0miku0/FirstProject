package WebStore.DatabaseAccessObject.test;

import WebStore.DatabaseAccessObject.implments.MyCategoryDAO;
import WebStore.bean.Category;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class Categorytest {
    @Test
    public void test() throws SQLException {
        MyCategoryDAO myDatabaseAccessObject = new MyCategoryDAO();
        boolean b = myDatabaseAccessObject.addCategory(1111+"");
        Assert.assertTrue(b);
    }
    @Test
    public void test1(){
        int i = Integer.parseInt("111");
        System.out.println(i);
    }

    @Test
    public void testTotalRecordNumber() throws SQLException {
        MyCategoryDAO myDatabaseAccessObject = new MyCategoryDAO();
        int i = myDatabaseAccessObject.totalRecordNumber();
        System.out.println(i);
    }

    @Test
    public void testFindPageCategory() throws SQLException {
        MyCategoryDAO myDatabaseAccessObject = new MyCategoryDAO();
        List<Category> pageCategory = myDatabaseAccessObject.findPageObject(3, 0);
        for(int i=0;i<pageCategory.size();i++){
            System.out.println(pageCategory.get(i).getCid());
        }
    }

    @Test
    public void testfindCategoryByCid() throws SQLException {
        MyCategoryDAO myDatabaseAccessObject = new MyCategoryDAO();
        String categoryByCid = myDatabaseAccessObject.findCategoryByCid(3);
        System.out.println(categoryByCid);

    }



}
