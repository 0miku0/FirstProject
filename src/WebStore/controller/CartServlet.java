package WebStore.controller;

import WebStore.bean.ShoppingItem;
import WebStore.bean.User;
import WebStore.service.implments.MyCartService;
import WebStore.service.interfaces.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CartServlet",urlPatterns = "/user/CartServlet")
public class CartServlet extends HttpServlet {
    CartService cartService=new MyCartService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case ("findCart"):
                try {
                    findCart(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case ("addCart"):
                try {
                    addCart(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case ("delItem"):
                try {
                    delItem(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void delItem(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String pid = request.getParameter("pid");
        String uid = request.getParameter("uid");
        if(cartService.delOneItem(uid,pid)){
            findCart(request,response);
        }
    }

    private void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String pid = request.getParameter("pid");
        String uid = request.getParameter("uid");
        String snum = request.getParameter("snum");
        if(snum==null){
            snum="1";
        }
        if(cartService.addCart(uid,pid,snum)){
            String referer = request.getHeader("Referer");
            response.sendRedirect(referer);
        }
    }

    private void findCart(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
//            System.out.println(111);
            response.sendRedirect(request.getContextPath()+"/user/login.jsp");
        }
        List<ShoppingItem> shoppingItemList=cartService.getShoppingItems(user.getUid());
        request.getSession().setAttribute("shoppingItems",shoppingItemList);
        response.sendRedirect(request.getContextPath()+"/user/shoppingcart.jsp");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
