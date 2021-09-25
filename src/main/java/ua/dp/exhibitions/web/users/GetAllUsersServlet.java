package ua.dp.exhibitions.web.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import java.util.List;

public class GetAllUsersServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetAllUsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.trace("Calling Servlet class doGet method");

        try {
            UserDAO userDAO = UserDAO.getInstance();
            List<User> users = userDAO.getAllUsers();
            request.setAttribute("users", users);

            log.trace("redirecting to users.jsp");
            request.getRequestDispatcher("jsp/users/users.jsp").forward(request, response);

        } catch (DaoException e) {
            log.trace("Catching DaoException: "+e.getMessage());
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
