package WebStore.controller;

import WebStore.bean.Product;
import WebStore.service.interfaces.MainService;
import WebStore.service.implments.MyMainService;
import WebStore.utils.PageInfo.SearchPageByCid;
import WebStore.utils.PageInfo.SearchPageByPname;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "MainServlet",urlPatterns = "/MainServlet",loadOnStartup = 1)
public class MainServlet extends HttpServlet {
    private final static int topProductsAmount=5;
    private final static int hotProductsAmount=6;
    MainService mainService=new MyMainService();
    @Override
    public void init() throws ServletException {
        List<Product> topProducts= null;
        List<Product> hotProducts= null;
        try {
            topProducts = mainService.topProductsInit(topProductsAmount);
            hotProducts = mainService.hotProductsInit(hotProductsAmount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getServletContext().setAttribute("topProducts",topProducts);
        getServletContext().setAttribute("hotProducts",hotProducts);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if(op==null){
            op="null";
        }
        switch (op) {
            case "findProductsByName":
                //通过名字pname进行分页搜索
                try {
                    findProductsByName(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "findProductByCid":
                //通过类别cid进行分页搜索
                try {
                    findProductByCid(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "findProductByPid":
                //通过产品主键pid进行查找
                try {
                    findProductByPid(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "null":
                //默认主页跳转
                indexSkip(request, response);
                break;
        }
        if(op==null){
            response.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
        }

    }

    private void indexSkip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
    }

    private void findProductByPid(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String pid = request.getParameter("pid");
        Product product=mainService.findProductByPid(pid);
        request.getSession().setAttribute("product",product);
        response.sendRedirect(getServletContext().getContextPath()+"/productdetail.jsp");
    }

    private void findProductByCid(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HashMap<String,String> parameterMap = new HashMap();
        String cid = request.getParameter("cid");
        parameterMap.put("cid",cid);
        String num = request.getParameter("num");
        if(num==null){
            num="1";
        }
        SearchPageByCid  searchPageByCid=mainService.findProductByCid(num,parameterMap);
        request.getSession().setAttribute("page",searchPageByCid);
        response.sendRedirect(getServletContext().getContextPath()+"/products.jsp");
    }

    private void findProductsByName(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HashMap<String,String> parameterMap = new HashMap();
        String likePname = request.getParameter("pname");
            parameterMap.put("likePname",likePname);
        String num = request.getParameter("num");
        if(num==null){
            num="1";
        }
        SearchPageByPname searchPageByPname=mainService.findProductsByName(num,parameterMap);
        request.getSession().setAttribute("page",searchPageByPname);
        response.sendRedirect(getServletContext().getContextPath()+"/searchProducts.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}


