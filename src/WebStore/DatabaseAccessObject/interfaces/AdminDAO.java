package WebStore.DatabaseAccessObject.interfaces;

import WebStore.bean.Admin;
import WebStore.utils.PageInfo.PageInfoDAO;

import java.sql.SQLException;

public interface AdminDAO extends PageInfoDAO<Admin> {

     boolean usernameIsValid(String username) throws SQLException;

     boolean registerAdmin(String username, String password) throws SQLException;

    boolean updateAdmin(String aid, String username, String password) throws SQLException;

    boolean deleteOne(String aid) throws SQLException;

    boolean login(String username, String password) throws SQLException;
}
