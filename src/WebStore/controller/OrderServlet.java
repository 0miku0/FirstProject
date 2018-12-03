package WebStore.controller;

import WebStore.bean.Order;
import WebStore.bean.OrderProduct;
import WebStore.bean.User;
import WebStore.service.implments.MyOrderService;
import WebStore.service.interfaces.OrderService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "OrderServlet",urlPatterns = "/user/OrderServlet")
public class OrderServlet extends HttpServlet {
    OrderService orderService=new MyOrderService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "myoid":
                try {
                    findUserOrder(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "placeOrder":
                try {
                    placeOrder(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "cancelOrder":
                try {
                    cancelOrder(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "orderDetail":
                try {
                    orderDetail(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void orderDetail(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String oid = request.getParameter("oid");
//        Order order=orderService.getOrderByOid();
        List<OrderProduct> orderList=orderService.getOrderProductByOid(oid);
        request.getSession().setAttribute("orderList",orderList);
        response.sendRedirect(getServletContext().getContextPath()+"/admin/order/orderDetails.jsp");
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String oid = request.getParameter("oid");
        String state = request.getParameter("state");
//        System.out.println(oid+"====="+state);
        orderService.cancelOrder(oid,state);
        findUserOrder(request,response);
    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException, SQLException {
        String[] pids = request.getParameterValues("pid");
        String[] buyNums = request.getParameterValues("buyNum");
        HashMap<Integer, Integer> pidBuyNumMap = new HashMap<Integer, Integer>();
        for(int i=0;i<pids.length;i++){
            pidBuyNumMap.put(Integer.parseInt(pids[i]),Integer.parseInt(buyNums[i]));
        }
        //uid recipients  tel address   money
        Map<String, String[]> parameterMap = request.getParameterMap();
        Order order = new Order();
        BeanUtils.populate(order,parameterMap);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderTime = dateFormat.format(date);
        order.setOrderTime(orderTime);
        //state前端未设计
        order.setState("1");
        if(orderService.addOrder(order,pidBuyNumMap)){
            findUserOrder(request,response);
//            response.sendRedirect(getServletContext().getContextPath()+"user/myOrders.jsp");
        }
    }

    private void findUserOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        User user=(User)request.getSession().getAttribute("user");
        String uid = user.getUid();
        List<Order> orderList=orderService.getOrderByUid(uid);
        if(orderList!=null){
            request.getSession().setAttribute("orders",orderList);
//            System.out.println(getServletContext().getContextPath());
//            System.out.println(getServletContext().getContextPath()+"user/myOrders.jsp");
//            System.out.println(getServletContext().getContextPath()+"/user/myOrders.jsp");
            response.sendRedirect(getServletContext().getContextPath()+"/user/myOrders.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
