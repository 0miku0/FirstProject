package WebStore.controller;

import WebStore.bean.Admin;
import WebStore.service.implments.MyOrderService;
import WebStore.service.implments.MyUserService;
import WebStore.service.interfaces.AdminService;
import WebStore.service.implments.MyAdminService;
import WebStore.service.interfaces.OrderService;
import WebStore.service.interfaces.UserService;
import WebStore.utils.MD5Util;
import WebStore.utils.PageInfo.PageInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AdminServlet",urlPatterns = "/admin/AdminServlet")
public class AdminServlet extends HttpServlet {
    AdminService adminService = new MyAdminService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op) {
            case "addAdmin":
                //新增管理员账号
                try {
                    addAdmin(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "findAllAdmin":
                //查看管理员账号
                try {
                    findAllAdmin(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "updateAdmin":
                //修改管理员账号
                try {
                    updateAdmin(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "deleteOne":
                //删除账号
                try {
                    deleteOne(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "login":
                //登陆账号
                try {
                    login(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "findAllOrder":
                //查看所有订单
                try {
                    findAllOrder(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "lgout":
                //退出登录
                lgout(request, response);
                break;
            case "delOrder":
                //删除订单
                try {
                    delOrder(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "deleteOrderProduct":
                //删除订单商品
                try {
                    deleteOrderProduct(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void deleteOrderProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String opid = request.getParameter("opid");
        OrderService orderService=new MyOrderService();
        orderService.deleteOrderProduct(opid);
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer);
    }

    private void delOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String oid = request.getParameter("oid");
        OrderService orderService = new MyOrderService();
        orderService.delOrderByOid(oid);
        findAllOrder(request,response);
    }

    private void lgout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("admin",null);
        response.getWriter().print("已退出");
        response.setHeader("refresh","1,url="+request.getContextPath()+"/admin/index.jsp");
    }

    private void findAllOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String num = request.getParameter("num");
        OrderService orderService = new MyOrderService();
        if(num==null){
            num="1";
        }
        PageInfo pageInfo=orderService.findPageOrder(num);
        request.getSession().setAttribute("page",pageInfo);
        response.sendRedirect(getServletContext().getContextPath()+"/admin/order/orderList.jsp");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5Password = MD5Util.getMD5(password);
        if(adminService.login(username,md5Password)){
            Admin admin = new Admin(username, md5Password);
            request.getSession().setAttribute("admin",admin);
            response.sendRedirect(getServletContext().getContextPath()+"/admin/main.jsp");
        }else {
            response.getWriter().print("输入不正确");
            response.setHeader("refresh","1,url="+request.getContextPath()+"/admin/index.jsp");
        }
    }

    private void deleteOne(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String aid = request.getParameter("aid");
        if(adminService.deleteOne(aid)){
            findAllAdmin(request,response);
        }
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String aid = request.getParameter("aid");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5Password = MD5Util.getMD5(password);
        String verify = request.getParameter("password1");
        if(!password.equals(verify)){
            response.getWriter().print("两次密码不正确，重新输入");
            response.setHeader("refresh","1,url="+request.getContextPath()+
                    "/admin/admin/updateAdmin.jsp?aid="+aid+"&username="+username);
//        }else if(!adminService.usernameIsValid(username)){
//            response.getWriter().print("用户名已存在，请重新输入");
//            response.setHeader("refresh","1,url="+request.getContextPath()+
//                    "/admin/admin/updateAdmin.jsp?aid="+aid+"&username="+username);
        }else if(adminService.updateAdmin(aid,username,md5Password)){
            response.getWriter().print("修改成功！即将跳转到登录界面");
           findAllAdmin(request,response);
        }
    }

    private void findAllAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String num = request.getParameter("num");
        if(num!=null||!num.isEmpty()) {
            num = "1";
        }
            PageInfo pageInfo=adminService.findPageAdmin(num);
            request.getSession().setAttribute("pageInfo",pageInfo);
            response.sendRedirect(getServletContext().getContextPath()+"/admin/admin/adminList.jsp");
    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5Password = MD5Util.getMD5(password);
        String verify = request.getParameter("password1");
        if(!password.equals(verify)){
            response.getWriter().print("两次密码不正确，重新输入");
            response.setHeader("refresh","1,url="+request.getContextPath()+"/admin/admin/addAdmin.jsp");
        }else if(!adminService.usernameIsValid(username)){
            response.getWriter().print("用户名已存在，请重新输入");
            response.setHeader("refresh","1,url="+request.getContextPath()+"/admin/admin/addAdmin.jsp");
        }else if(adminService.registerAdmin(username,md5Password)){
            response.getWriter().print("注册成功！即将跳转到登录界面");
            response.setHeader("refresh","1,url="+request.getContextPath()+"/admin/admin/addAdmin.jsp");
        }
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
