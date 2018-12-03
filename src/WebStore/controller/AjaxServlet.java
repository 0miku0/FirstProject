package WebStore.controller;

import WebStore.service.interfaces.AjaxService;
import WebStore.service.implments.MyAjaxService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AjaxServlet",urlPatterns ="/admin/AjaxServlet")
public class AjaxServlet extends HttpServlet {
    AjaxService ajaxService=new MyAjaxService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ajax = request.getParameter("ajax");
        switch (ajax){
            case "isUserUsernameAvailable":
                try {
                    isUserUsernameAvailable(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void isUserUsernameAvailable(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String username = request.getParameter("username");
        if(ajaxService.isUserUsernameAvailable(username)){
            response.getWriter().print("exist");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
