package ua.dp.exhibitions.web.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteUserServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(DeleteUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String login = request.getParameter("login");
//        log.trace("Attempt to delete user with login:" + login);

        int userId = Integer.parseInt(request.getParameter("id"));
        log.trace("Attempt to delete user with ID:" + userId);

        UserDAO userDAO = UserDAO.getInstance();

        try {
//            userDAO.deleteUserByLogin(login);
            userDAO.deleteUserById(userId);
        } catch (DaoException e) {
            log.error("Catching DaoException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }

        String message = "User was successfully removed from the database";
        log.trace("User ID:"+userId+"was removed from the Users table");
        request.setAttribute("message", message);
        request.getRequestDispatcher("jsp/users/information.jsp").forward(request, response);
    }
}


