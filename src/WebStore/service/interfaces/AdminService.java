package WebStore.service.interfaces;

import WebStore.utils.PageInfo.PageInfo;

import java.sql.SQLException;

public interface AdminService {
    boolean usernameIsValid(String username) throws SQLException;

    boolean registerAdmin(String username, String password) throws SQLException;

    PageInfo findPageAdmin(String num) throws SQLException;

    boolean updateAdmin(String aid, String username, String password) throws SQLException;

    boolean deleteOne(String aid) throws SQLException;

    boolean login(String username, String password) throws SQLException;
}
