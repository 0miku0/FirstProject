package WebStore.DatabaseAccessObject.implments;

import WebStore.DatabaseAccessObject.interfaces.UserDAO;
import WebStore.bean.User;
import WebStore.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class MyUserDAO implements UserDAO {
    private QueryRunner queryRunner=new QueryRunner(C3P0Utils.getCpds());
    private static  final String TABLE="`firstusertable`";
    public MyUserDAO() {
    }

    @Override
    public boolean userRegister(String username, String nickname, String email, String password, String birthday, String updateTime,String activeCode) throws SQLException {
        int update = queryRunner.update("insert into  "+ TABLE +" values (?,?,?,?,?,?,?);",
                username, nickname,email,password,birthday,updateTime,activeCode);
        if(update!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User userLogin(String username, String password) throws SQLException {
        User user = queryRunner.query("select * from " + TABLE + " where username=? and password=? and status='1';",
                new BeanHandler<>(User.class), username, password);
        return user;
    }

    @Override
    public boolean userUpdate(String uid, String username, String nickname, String email, String password, String birthday) throws SQLException {
        int update = queryRunner.update("update  "+ TABLE +" set nickname=?,email=?,password=?,birthday=? where uid=?;",
                nickname,email,password,birthday,uid);
        if(update!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User getUserByUid(String uid) throws SQLException {
        User user = queryRunner.query("select * from " + TABLE + " where uid=? ;",
                new BeanHandler<>(User.class), uid);
        return user;
    }

    @Override
    public boolean isUserUsernameAvailable(String username) throws SQLException {
        String user = queryRunner.query("select username from " + TABLE + " where username=?;",
                new BeanHandler<>(String.class), username);
        boolean flag=false;
        if(user!=null){
            flag=true;
        }
        return flag;
    }

    @Override
    public boolean userActive(String activeCode) throws SQLException {
        int update = queryRunner.update("update  "+ TABLE +" set status='1' where activeCode=?;", activeCode);
        if(update!=0){
            return true;
        }else {
            return false;
        }
    }
}
