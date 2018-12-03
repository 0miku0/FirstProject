package WebStore.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(filterName = "AdminLoginFilter",urlPatterns = "/admin/*")
public class AdminLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Object admin = req.getSession().getAttribute("admin");
        String op = req.getParameter("op");
        Boolean flag=true;
        if(op==null){
            op="";
        }
//        if(request.getParameter("ajax").equals("isUserUsernameAvailable")){
//            flag=false;
//        }
        if(req.getRequestURI().equals("/admin/index.jsp")){
            flag=false;
        }
//        System.out.println(req.getHeader("Referer"));
        String referer = req.getHeader("Referer");
//        System.out.println(req.getServletContext()n.getCotextPath());null????????
//        if(referer!=null&&referer.startsWith("http://localhost:8080/admin")){
        if(referer!=null&&referer.startsWith("http://localhost:8080/")){
            flag=false;
        }
        if(op.equals("login")){
            flag=false;
        }
        if(admin!=null){
            flag=false;
        }
        if(flag){
            res.sendRedirect(req.getServletContext().getContextPath()+"/admin/index.jsp");
    }
        chain.doFilter(req,res);
    }

    @Override
    public void destroy() {

    }
}
