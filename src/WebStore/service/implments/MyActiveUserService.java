package WebStore.service.implments;

import WebStore.DatabaseAccessObject.interfaces.UserDAO;
import WebStore.DatabaseAccessObject.implments.MyUserDAO;
import WebStore.service.interfaces.ActiveUserService;

import java.sql.SQLException;

public class MyActiveUserService implements ActiveUserService {
    UserDAO userDAO=new MyUserDAO();
    @Override
    public Boolean userActive(String activeCode) throws SQLException {
        if(userDAO.userActive(activeCode)){
            return true;
        }
        return false;
    }
}
