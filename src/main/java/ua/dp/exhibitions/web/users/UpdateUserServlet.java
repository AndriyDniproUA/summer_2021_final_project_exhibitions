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
import java.util.HashMap;
import java.util.Map;

public class UpdateUserServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(UpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("id"));
        UserDAO userDAO = UserDAO.getInstance();

        User user = null;
        try {

            user = userDAO.getUserById(userId);
        } catch (DaoException e) {
            log.error("Catching DaoException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("jsp/users/update_user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        User user=null;

        UserDAO userDAO = UserDAO.getInstance();
        try {
            user=userDAO.getUserById(userId);
        } catch (DaoException e) {
            log.error(e.getMessage());
        }

        double currentBalance=user.getBalance();
        double updatedBalance=currentBalance+Double.parseDouble(request.getParameter("deposit"));

        Map<String, String> parameters = new HashMap<>();
        parameters.put("login", request.getParameter("login"));
        parameters.put("password", request.getParameter("password"));
        parameters.put("role", request.getParameter("role"));
        parameters.put("balance",String.valueOf(updatedBalance));


        log.trace("Attempting to update a user with ID:" + userId);

        try {
            userDAO.updateUser(userId, parameters);
        } catch (DaoException e) {
            log.error("Catching DaoException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }

        log.trace("User ID:" + userId + " was successfully updated");
        String message = "User was successfully updated";

        request.setAttribute("message", message);
        request.getRequestDispatcher("jsp/users/information.jsp").forward(request, response);
    }
}
