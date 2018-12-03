package WebStore.controller;

import WebStore.bean.Category;
import WebStore.bean.Product;
import WebStore.service.interfaces.CategoryService;
import WebStore.service.interfaces.ProductService;
import WebStore.service.implments.MyCategoryService;
import WebStore.service.implments.MyProductService;
import WebStore.utils.PageInfo.PageInfo;
import WebStore.utils.ProductWrite;
import WebStore.utils.PageInfo.SearchPageInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "ProductServlet",urlPatterns = "/admin/ProductServlet")
public class ProductServlet extends HttpServlet {
    ProductService productService=new MyProductService();
    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Product product = new Product();
        BeanUtils.populate(product,parameterMap);
        if(productService.updateProduct(product)) {
            findAllProduct(request, response);
        }
    }

    private void findProductByUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String pid = request.getParameter("pid");
        Product product = productService.findProductByPid(pid);
        if(product!=null) {
            CategoryService CategoryService = new MyCategoryService();
            List<Category> categories = CategoryService.findAllCategory();
            request.getSession().setAttribute("categories", categories);
            request.getSession().setAttribute("product", product);
            response.sendRedirect(getServletContext().getContextPath()+"/admin/product/updateProduct.jsp");
        }

    }

    private void deleteOne(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String pid = request.getParameter("pid");
        if(productService.deleteProductByPid(pid)){
            String[] parameters=new String[]{"pid","cid","pname","minPrice","maxPrice"};
            HashMap<String,String> parameterMap = new HashMap();
            for(int i=0;i<parameters.length;i++){
                String parameter="";
                parameterMap.put(parameters[i],parameter);
            }
            String num="1";
            SearchPageInfo searchPageInfo=productService.multiConditionSearchProduct(num,parameterMap);
            request.getSession().setAttribute("page",searchPageInfo);
            response.sendRedirect(getServletContext().getContextPath()+"/admin/product/productList.jsp");
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        File repository = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
        diskFileItemFactory.setRepository(repository);
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        List<FileItem> items = null;
        items=servletFileUpload.parseRequest(request);
        String contextRealPath = getServletContext().getRealPath("/")+"admin/product/pictures";
//        System.out.println(contextRealPath);
        String contextRelativePath="admin/product/pictures";
        ProductWrite productWrite = new ProductWrite(items,contextRealPath,contextRelativePath);
        productWrite.handle();
        Product product = productWrite.getProduct();
        productService.addProduct(product);
        response.sendRedirect(getServletContext().getContextPath()+"/admin/product/addProduct.jsp");
    }
    private void findAllProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
//        String num = request.getParameter("num");
//        if(num==null){
//            num="1";
//        }
//        if(!num.isEmpty()){
//            PageInfo pageInfo=null;
//            pageInfo= productService.findPageProduct(num);
//            request.getSession().setAttribute("page",pageInfo);
//            response.sendRedirect(getServletContext().getContextPath()+"/admin/product/productList.jsp");
//        }
        multiConditionSearchProduct(request,response);
    }

    private void findCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        CategoryService CategoryService = new MyCategoryService();
        List<Category> categories = CategoryService.findAllCategory();
        request.getSession().setAttribute("categories",categories);
        try {
            response.sendRedirect(getServletContext().getContextPath()+"/admin/product/addProduct.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String[] checkboxpids = request.getParameterValues("checkboxPid");
        for(int i=0;i<checkboxpids.length;i++){
            productService.deleteProductByPid(checkboxpids[i]);
        }
        findAllProduct(request,response);
    }
    private void multiConditionSearchProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String[] parameters=new String[]{"pid","cid","pname","minPrice","maxPrice"};
        HashMap<String,String> parameterMap = new HashMap();
        for(int i=0;i<parameters.length;i++){
            String parameter = request.getParameter(parameters[i]);
            if(parameter==null){
                parameter="";
            }
            parameterMap.put(parameters[i],parameter);
        }
        String num = request.getParameter("num");
        if(num==null){
            num="1";
        }
        SearchPageInfo searchPageInfo=productService.multiConditionSearchProduct(num,parameterMap);
        request.getSession().setAttribute("page",searchPageInfo);
        response.sendRedirect(getServletContext().getContextPath()+"/admin/product/productList.jsp");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if(op==null){
            boolean multipart = request.getContentType().startsWith("multipart");
            if (multipart){
                //添加商品
                op="addProduct";
            }
        }
        switch (op){
            case "findCategory":
                //查找可选商品种类
                try {
                    findCategory(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "findAllProduct":
                //查看/修改商品
                try {
                    findAllProduct(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "deleteOne":
                //删除商品
                try {
                    deleteOne(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "findProductByUpdate":
                //查找单个商品
                try {
                    findProductByUpdate(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "addProduct":
                //添加商品
                try {
                    addProduct(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "updateProduct":
                //修改商品
                try {
                    updateProduct(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "deleteMulti":
                //修改商品
                try {
                    deleteMulti(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case  "multiConditionSearchProduct":
                //多条件查询商品
                try {
                    multiConditionSearchProduct(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}


