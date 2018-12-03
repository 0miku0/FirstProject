package WebStore.service.implments;

import WebStore.DatabaseAccessObject.interfaces.UserDAO;
import WebStore.DatabaseAccessObject.implments.MyUserDAO;
import WebStore.service.interfaces.AjaxService;

import java.sql.SQLException;

public class MyAjaxService implements AjaxService {
    UserDAO userDAO=new MyUserDAO();
    @Override
    public boolean isUserUsernameAvailable(String username) throws SQLException {
        if(userDAO.isUserUsernameAvailable(username)){
            return true;
        }
        return false;
    }
}
