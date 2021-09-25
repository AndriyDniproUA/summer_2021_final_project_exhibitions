package ua.dp.exhibitions.web.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class DeleteUserServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(DeleteUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setCharacterEncoding("Utf-8");
        String message = "";
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        if (currentUser == null || !currentUser.getRole().equals("admin")) {
            log.trace("User was not allowed to delete a user");
            message = "Only administrator are allowed to delete Users!";
            request.setAttribute("message", message);
            request.getRequestDispatcher("jsp/users/warning.jsp").forward(request, response);

        } else {
            String login = request.getParameter("login");
            log.trace("Attempt to delete user with login:" + login);

            UserDAO userDAO = UserDAO.getInstance();

            try {
                userDAO.deleteUser(login);
            } catch (DaoException e) {
                log.error("Catching DaoException: " + e.getMessage());
                request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            }

//            if (userDAO.deleteUser(login)) {
//                message = "User " + login + " was successfully removed from the Users table";
//                log.trace(message);
//                request.setAttribute("message", message);
//                request.getRequestDispatcher("jsp/users/information.jsp").forward(request, response);
//            } else {
//                message = "User " + login + " was not removed from the Users table";
//                log.trace(message);
//                request.setAttribute("message", message);
//                request.getRequestDispatcher("jsp/users/warning.jsp").forward(request, response);

            message = "User " + login + " was successfully removed from the Users table";
            log.trace(message);
            request.setAttribute("message", message);
            request.getRequestDispatcher("jsp/users/information.jsp").forward(request, response);
        }
    }

}
