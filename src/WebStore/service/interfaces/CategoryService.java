package WebStore.service.interfaces;

import WebStore.bean.Category;
import WebStore.utils.PageInfo.PageInfo;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    Boolean addCategory(Category category) throws SQLException;
    Boolean deleteCategory(int cid) throws SQLException;
    Boolean updateCategory(Category category) throws SQLException;
    List<WebStore.bean.Category> findAllCategory() throws SQLException;

    PageInfo findPageCategory(String num) throws SQLException;
}
