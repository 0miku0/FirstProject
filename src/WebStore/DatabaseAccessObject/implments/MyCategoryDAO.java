package WebStore.DatabaseAccessObject.implments;


import WebStore.bean.Category;
import WebStore.DatabaseAccessObject.interfaces.CategoryDAO;
import WebStore.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class MyCategoryDAO implements CategoryDAO {
    private QueryRunner queryRunner=new QueryRunner(C3P0Utils.getCpds());
    private static  final String TABLE="`firstcategorytable`";
    public MyCategoryDAO() { }

    @Override
    public boolean addCategory(String cname) throws SQLException {
        List<Category> allCategory = findAllCategory();
        for(int i=0;i<allCategory.size();i++){
            if(allCategory.get(i).getCname()!=null&&allCategory.get(i).getCname().equals(cname)){
                return false;
            }
        }
        queryRunner.update("insert into "+TABLE+"  values (null,?);", cname);
        return true;
    }

    @Override
    public boolean deleteCategory(int cid) throws SQLException {
        queryRunner.update("delete from "+TABLE+" where cid=?;",cid);
        return true;
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        queryRunner.update("update "+TABLE+" set cname=? where cid=?;", category.getCname(),category.getCid());
        return true;
    }

    //改用分页设计,改为totalRecordNumber()、findPageCategory(int limit, int offset)
    @Override
    public List<Category> findAllCategory() throws SQLException {
    List<Category> categoryList = queryRunner.query
            ("select * from "+TABLE+";", new BeanListHandler<>(Category.class));
    return  categoryList;

    }

    @Override
    public int totalRecordNumber() throws SQLException {
        String totalRecordNumber= queryRunner.query
                ("select count(*) as totalRecord from "+TABLE+";",new ScalarHandler()).toString();
        return Integer.parseInt(totalRecordNumber);
    }

    @Override
    public List<Category> findPageObject(int limit, int offset) throws SQLException {
        List<Category> categoryList = queryRunner.query
                ("select * from "+TABLE+" limit ? offset ?;",
                        new BeanListHandler<>(Category.class),limit,offset);
        return categoryList;
    }

    @Override
    public String findCategoryByCid(int cid) throws SQLException {
        String cname= queryRunner.query
                ("select cname from "+TABLE+" where cid = ?;",new ScalarHandler(),cid).toString();
        return cname;
    }
}
