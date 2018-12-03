package WebStore.DatabaseAccessObject.interfaces;

import WebStore.bean.Category;
import WebStore.utils.PageInfo.PageInfoDAO;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO extends PageInfoDAO<Category> {

    boolean addCategory(String cname) throws SQLException;

     boolean deleteCategory(int cid) throws SQLException;

     boolean updateCategory(Category category) throws SQLException;

     List<Category> findAllCategory() throws SQLException;

//    public int totalRecordNumber() throws SQLException;
//
//    List<Category> findPageCategory(int limit, int offset) throws SQLException;

     String findCategoryByCid(int cid) throws SQLException;
}
