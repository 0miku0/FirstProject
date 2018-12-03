package WebStore.service.interfaces;

import WebStore.bean.User;

import java.sql.SQLException;

public interface UserService {
    boolean UserRegister(String username, String nickname, String email, String password, String birthday, String updateTime) throws SQLException;

    User UserLogin(String username, String password, String verifyCode) throws SQLException;

    boolean userUpdate(String uid, String username, String nickname, String email, String password, String birthday) throws SQLException;

    User getUserByUid(String uid) throws SQLException;
}
