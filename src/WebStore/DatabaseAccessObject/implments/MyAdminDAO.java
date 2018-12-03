package WebStore.DatabaseAccessObject.implments;

import WebStore.DatabaseAccessObject.interfaces.AdminDAO;
import WebStore.bean.Admin;
import WebStore.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MyAdminDAO implements AdminDAO {
    private QueryRunner queryRunner=new QueryRunner(C3P0Utils.getCpds());
    private static  final String TABLE="`firstadmintable`";
    public MyAdminDAO() { }

    @Override
    public int totalRecordNumber() throws SQLException {
        String totalRecordNumber= queryRunner.query
                ("select count(*) as totalRecord from"+TABLE+";",new ScalarHandler()).toString();
        return Integer.parseInt(totalRecordNumber);
    }

    @Override
    public List<Admin> findPageObject(int limit, int offset) throws SQLException {
        List<Admin> adminList = queryRunner.query
                ("select * from "+TABLE+" limit ? offset ? ;",
                        new BeanListHandler<>(Admin.class),limit,offset);
        return adminList;
    }

    @Override
    public boolean usernameIsValid(String username) throws SQLException {
        String result = queryRunner.query("select username from "+TABLE+" where username=? ;",
                new BeanHandler<String>(String.class), username);
        if(result==null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean registerAdmin(String username, String password) throws SQLException {
        int update = queryRunner.update("insert into  "+ TABLE +" values (null,?,?);",username, password);
        if(update!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateAdmin(String aid, String username, String password) throws SQLException {
        int update = queryRunner.update("update  " + TABLE + " set username=?,password=? where aid=?;",
                username, password, aid);
        if(update!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteOne(String aid) throws SQLException {
        int update = queryRunner.update("delete from " + TABLE + " where aid=?;", aid);
        if(update!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean login(String username, String password) throws SQLException {
        String result = queryRunner.query("select username from "+TABLE+" where username=? and password=?;",
                new BeanHandler<String>(String.class), username,password);
        if(result==null){
            return false;
        }else {
            return true;
        }
    }


}
