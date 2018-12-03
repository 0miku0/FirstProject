package WebStore.controller;

import WebStore.bean.User;
import WebStore.service.interfaces.UserService;
import WebStore.service.implments.MyUserService;
import WebStore.utils.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "UserServlet",urlPatterns = "/user/UserServlet")
public class UserServlet extends HttpServlet {
    UserService userService=new MyUserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op) {
            case "register":
                //用户注册
                try {
                    UserRegister(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "login":
                //用户登陆
                try {
                    UserLogin(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "logout":
                //用户退出
                userLogout(request, response);
                break;
            case "update":
                //用户信息更改
                try {
                    userUpdate(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void userUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String uid = request.getParameter("uid");
        String username = request.getParameter("username");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String md5Password = MD5Util.getMD5(password);
        String birthday = request.getParameter("birthday");
        if(userService.userUpdate(uid,username,nickname,email,md5Password,birthday)){
            User user=userService.getUserByUid(uid);
            request.getSession().setAttribute("user",user);
            response.sendRedirect(request.getContextPath()+"/user/personal.jsp");
        }
    }

    private void userLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("user",null);
        response.getWriter().print("已退出");
        response.setHeader("refresh","1,url="+request.getContextPath()+"/index.jsp");
    }

    private void UserLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5Password = MD5Util.getMD5(password);
        String verifyCode = request.getParameter("verifyCode");
        User user=userService.UserLogin(username,md5Password,verifyCode);
        if(user!=null){
            request.getSession().setAttribute("user",user);
            response.getWriter().print("登陆成功");
            response.setHeader("refresh","1,url="+request.getContextPath()+"/index.jsp");
        }else {
            response.getWriter().print("账号或密码不正确");
            response.setHeader("refresh","1,url="+request.getContextPath()+"/user/login.jsp");
        }
    }

    private void UserRegister(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String username = request.getParameter("username");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String md5Password = MD5Util.getMD5(password);
        String birthday = request.getParameter("birthday");
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime = dateFormat.format(date);
        if(userService.UserRegister(username,nickname,email,md5Password,birthday,updateTime)){
            response.getWriter().print("注册成功");
            response.setHeader("refresh","1,url="+request.getContextPath()+"/user/login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
