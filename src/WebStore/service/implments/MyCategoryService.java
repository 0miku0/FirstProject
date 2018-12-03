package WebStore.service.implments;

import WebStore.bean.Category;
import WebStore.DatabaseAccessObject.interfaces.CategoryDAO;
import WebStore.DatabaseAccessObject.implments.MyCategoryDAO;
import WebStore.service.interfaces.CategoryService;
import WebStore.utils.PageInfo.PageInfo;

import java.sql.SQLException;
import java.util.List;

public class MyCategoryService implements CategoryService {
    private CategoryDAO categoryDAO=new MyCategoryDAO();
    private static  final int PAGE_COUNT=3;
    public MyCategoryService() {
    }

    @Override
    public Boolean addCategory(Category category) throws SQLException {
        if(categoryDAO.addCategory(category.getCname())){
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteCategory(int cid) throws SQLException {
        if(categoryDAO.deleteCategory(cid)){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateCategory(Category category) throws SQLException {
        if(categoryDAO.updateCategory(category)){
            return true;
        }
        return false;
    }

    //采用分页设计,改用findPageCategory()
    @Override
    public List<Category> findAllCategory() throws SQLException {
        return categoryDAO.findAllCategory();
    }

    @Override
    public PageInfo findPageCategory(String num) throws SQLException {
        int currentPageNumber = Integer.parseInt(num);
        PageInfo<Category> categoryPageInfo = new PageInfo<>(PAGE_COUNT,currentPageNumber,categoryDAO);
       categoryPageInfo.setPageInfo();
        return categoryPageInfo;
    }
}
