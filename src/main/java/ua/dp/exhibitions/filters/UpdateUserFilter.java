package ua.dp.exhibitions.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateUserFilter implements Filter {
    private static final Logger log = LogManager.getLogger(UpdateUserFilter.class);

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        int userId = Integer.parseInt(request.getParameter("id"));


        if (currentUser == null || (!currentUser.getRole().equals("admin") && userId != currentUser.getId())) {
            log.trace("A user was not allowed to use administrator privileges");

            String message = "This function is reserved for administrators and owners only!";
            request.setAttribute("message", message);
            request.getRequestDispatcher("jsp/warning.jsp").forward(request, response);
        }
        next.doFilter(request, response);
    }

}
