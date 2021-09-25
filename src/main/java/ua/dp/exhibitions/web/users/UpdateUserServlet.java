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
        String login = request.getParameter("login");

        UserDAO userDAO = UserDAO.getInstance();


        User user = null;
        try {
            user = userDAO.getUser(login);
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

        String message = "";
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        if (currentUser == null || !currentUser.getRole().equals("admin")) {
            log.trace("User was not allowed to update a user");
            message = "Only administrators are allowed to update Users!";

        } else {
            String userLogin = request.getParameter("userLogin");

            Map<String, String> parameters = new HashMap<>();
            parameters.put("login", request.getParameter("login"));
            parameters.put("password", request.getParameter("password"));
            parameters.put("role", request.getParameter("role"));

            log.trace("Attempting to update a user with login:" + userLogin);
            UserDAO userDAO = UserDAO.getInstance();

            try {
                userDAO.updateUser(userLogin, parameters);
            } catch (DaoException e) {
                log.error("Catching DaoException: " + e.getMessage());
                request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            }


//            if () {
//                message = "User " + userLogin + " was successfully updated.";
//                log.trace(message);
//            } else {
//                message = "User " + userLogin + " was not updated";
//                log.trace(message);
//            }

            message = "User " + userLogin + " was successfully updated.";
            log.trace(message);
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("jsp/users/information.jsp").forward(request, response);
    }
}
