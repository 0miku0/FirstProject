package WebStore.controller;

import WebStore.service.interfaces.ActiveUserService;
import WebStore.service.implments.MyActiveUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ActiveUserServlet",urlPatterns = "/ActiveUser")
public class ActiveUserServlet extends HttpServlet {
    ActiveUserService activeUserService=new MyActiveUserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activeCode = request.getParameter("activeCode");
        try {
            if(activeUserService.userActive(activeCode)){
                response.getWriter().print("注册成功");
                response.setHeader("refresh","1,url="+request.getContextPath()+"/user/login.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
