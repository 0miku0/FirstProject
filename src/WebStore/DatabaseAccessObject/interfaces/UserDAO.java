package WebStore.DatabaseAccessObject.interfaces;

import WebStore.bean.User;

import java.sql.SQLException;

public interface UserDAO {
    boolean userRegister(String username, String nickname, String email, String password, String birthday, String updateTime,String activeCode) throws SQLException;

    User userLogin(String username, String password) throws SQLException;

    boolean userUpdate(String uid, String username, String nickname, String email, String password, String birthday) throws SQLException;

    User getUserByUid(String uid) throws SQLException;

    boolean isUserUsernameAvailable(String username) throws SQLException;

    boolean userActive(String activeCode) throws SQLException;
}
