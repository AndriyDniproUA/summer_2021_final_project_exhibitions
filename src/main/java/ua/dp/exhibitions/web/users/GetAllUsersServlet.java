package ua.dp.exhibitions.web.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.entities.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import java.sql.*;
import java.util.List;

public class GetAllUsersServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetAllUsersServlet.class);
    Connection connection=null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.trace("Calling Servlet class doGet method");

        UserDAO userDAO = UserDAO.getInstance();
        List<User> users = userDAO.getAllUsers();
        request.setAttribute("users",users);
        log.debug("After Setting users attribute in request");

        log.trace("redirecting to users.jsp");
        request.getRequestDispatcher("jsp/users/users.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.trace("Calling Servlet class doPost method");
        response.getWriter().println("Hello");
    }
}
