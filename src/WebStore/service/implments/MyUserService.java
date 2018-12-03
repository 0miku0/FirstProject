package WebStore.service.implments;

import WebStore.DatabaseAccessObject.interfaces.UserDAO;
import WebStore.DatabaseAccessObject.implments.MyUserDAO;
import WebStore.bean.User;
import WebStore.service.interfaces.UserService;
import WebStore.utils.EmailVerify.SendJMail;

import java.sql.SQLException;
import java.util.UUID;

public class MyUserService implements UserService {
    UserDAO userDAO=new MyUserDAO();
    public MyUserService() {
    }

    @Override
    public boolean UserRegister(String username, String nickname, String email, String password, String birthday, String updateTime) throws SQLException {
        String activeCode = UUID.randomUUID().toString();
       if(userDAO.userRegister(username,nickname,email,password,birthday,updateTime,activeCode)){
           String content= "http://192.168.4.63:8080/ActiveUser?activeCode="+activeCode;
           SendJMail.sendMail(email,content);
           return true;
       }
       return false;
    }

    @Override
    public User UserLogin(String username, String password, String verifyCode) throws SQLException {
        User user = userDAO.userLogin(username, password);
        return user;
    }

    @Override
    public boolean userUpdate(String uid, String username, String nickname, String email, String password, String birthday) throws SQLException {
        if(userDAO.userUpdate(uid,username,nickname,email,password,birthday)){
            return true;
        }
        return false;
    }

    @Override
    public User getUserByUid(String uid) throws SQLException {
        return userDAO.getUserByUid(uid);
    }
}
