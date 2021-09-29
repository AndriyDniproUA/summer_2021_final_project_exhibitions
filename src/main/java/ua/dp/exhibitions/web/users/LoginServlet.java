package ua.dp.exhibitions.web.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;
import ua.dp.exhibitions.utils.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * LoginServlet handles login page
 */
public class LoginServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/users/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        log.trace("Login attempt by user with login " + login);

        User candidate = new User();
        candidate.setLogin(login);
        candidate.setPassword(password);

        User currentUser=null;
        UserDAO userDAO = UserDAO.getInstance();

        try {
            currentUser = userDAO.getUserByLogin(login);
            log.trace("Login success status:" + candidate.equals(currentUser));

        } catch (DaoException e){
            log.error("Catching DaoException: "+e.getMessage());
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }

        if (candidate.equals(currentUser)) {
            log.trace("Logged as user:" + currentUser.getLogin() + " role:" + currentUser.getRole());
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", currentUser);
            request.getRequestDispatcher("jsp/welcome.jsp").forward(request, response);
        } else {
            String message =
                    Util.internationalizeMessage(request, "login_servlet.message");

            request.setAttribute("message",message);
            request.getRequestDispatcher("jsp/warning.jsp").forward(request, response);
        }
    }
}
