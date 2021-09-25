package ua.dp.exhibitions.filters;

import ua.dp.exhibitions.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminAccessOnlyFilter implements Filter{

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {

        HttpSession session =((HttpServletRequest)request).getSession();
        User currentUser = (User)session.getAttribute("currentUser");


        if (currentUser!=null){
            String role = currentUser.getRole();
            System.out.println("************** ROLE IS:");
            System.out.println(role);
        }


        next.doFilter(request, response);
    }

}
