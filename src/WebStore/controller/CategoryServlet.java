package WebStore.controller;

import WebStore.bean.Category;
import WebStore.service.interfaces.CategoryService;
import WebStore.service.implments.MyCategoryService;
import WebStore.utils.PageInfo.PageInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryServlet",urlPatterns = "/admin/CategoryServlet",loadOnStartup =1)
public class CategoryServlet extends HttpServlet {
     private CategoryService categoryService=new MyCategoryService();

    @Override
    public void init() throws ServletException {
        List<Category> allCategory = null;
        try {
            allCategory = categoryService.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getServletContext().setAttribute("categories",allCategory);
    }
    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
       /* List<Category> allCategory = categoryModule.findAllCategory();
        request.getSession().setAttribute("categoryList",allCategory);
        response.sendRedirect(getServletContext().getContextPath()+"/admin/category/categoryList.jsp");*/
       //分页设计
        String num = request.getParameter("num");
        if(num==null||num.isEmpty()){
            num="1";
        }
            PageInfo pageInfo=null;
            try {
                pageInfo= categoryService.findPageCategory(num);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.getSession().setAttribute("pageInfo",pageInfo);
            response.sendRedirect(getServletContext().getContextPath()+"/admin/category/categoryList.jsp");

    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String cname = request.getParameter("cname");
        categoryService.addCategory(new Category(cname));
        response.sendRedirect(getServletContext().getContextPath()+"/admin/category/addCategory.jsp");
    }
    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String[] checkboxcids = request.getParameterValues("checkboxCid");
        for(int i=0;i<checkboxcids.length;i++){
            int cid = Integer.parseInt(checkboxcids[i]);
            categoryService.deleteCategory(cid);
        }
        findAllCategory(request,response);
    }
    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String cname = request.getParameter("cname");
        String cid = request.getParameter("cid");
        int i = Integer.parseInt(cid);
        categoryService.updateCategory(new Category(i,cname));
        findAllCategory(request,response);
    }
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String cid = request.getParameter("cid");
        int i = Integer.parseInt(cid);
        categoryService.deleteCategory(i);
        findAllCategory(request,response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String op = request.getParameter("op");
        switch (op){

            case "addCategory":
                //新增一个分类
                try {
                    addCategory(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "findAllCategory":
                //查询所有分类
                findAllCategory(request,response);
                break;
            case "deleteMulti":
                //删除多个分类
                try {
                    deleteMulti(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "updateCategory":
                //修改分类
                try {
                    updateCategory(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "deleteCategory":
                //删除分类
                try {
                    deleteCategory(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
