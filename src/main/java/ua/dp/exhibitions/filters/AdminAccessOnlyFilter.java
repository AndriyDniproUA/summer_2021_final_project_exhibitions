package ua.dp.exhibitions.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.daoUtil.ShowsDaoUtil;
import ua.dp.exhibitions.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * AdminAccessOnlyFilter is a filter class
 * limiting access to pages reserved for administrator
 */
public class AdminAccessOnlyFilter implements Filter{
    private static final Logger log = LogManager.getLogger(AdminAccessOnlyFilter.class);
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {

        HttpSession session =((HttpServletRequest)request).getSession();
        User currentUser = (User)session.getAttribute("currentUser");

        if (currentUser == null || !currentUser.getRole().equals("admin")) {
            log.trace("A user was not allowed to use administrator privileges");

            String message = "This function is reserved for administrators only!";
            request.setAttribute("message", message);
            request.getRequestDispatcher("jsp/warning.jsp").forward(request, response);
        }
        next.doFilter(request, response);
    }

}
