package WebStore.service.implments;

import WebStore.DatabaseAccessObject.interfaces.AdminDAO;
import WebStore.DatabaseAccessObject.implments.MyAdminDAO;
import WebStore.bean.Admin;
import WebStore.service.interfaces.AdminService;
import WebStore.utils.PageInfo.PageInfo;

import java.sql.SQLException;

public class MyAdminService implements AdminService {
    AdminDAO adminDAO=new MyAdminDAO();
    private static  final int PAGE_COUNT=3;

    @Override
    public boolean usernameIsValid(String username) throws SQLException {
        return adminDAO.usernameIsValid(username);
    }
    @Override
    public boolean registerAdmin(String username, String password) throws SQLException {
        return adminDAO.registerAdmin(username,password);
    }

    @Override
    public PageInfo findPageAdmin(String num) throws SQLException {
        int currentPageNumber = Integer.parseInt(num);
        PageInfo<Admin> adminPageInfo = new PageInfo<>(PAGE_COUNT, currentPageNumber, adminDAO);
        adminPageInfo.setPageInfo();
        return adminPageInfo;
    }

    @Override
    public boolean updateAdmin(String aid, String username, String password) throws SQLException {
        return adminDAO.updateAdmin(aid,username,password);
    }

    @Override
    public boolean deleteOne(String aid) throws SQLException {
        return adminDAO.deleteOne(aid);
    }

    @Override
    public boolean login(String username, String password) throws SQLException {
        return adminDAO.login(username,password);
    }
}
