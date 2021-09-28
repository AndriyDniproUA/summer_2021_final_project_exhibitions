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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterUserServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(RegisterUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/users/register.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("Utf-8");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        log.trace("Attempt to register user with login:" + login);

        UserDAO userDAO = UserDAO.getInstance();

        User currentUser = null;
        try {
            currentUser = userDAO.getUserByLogin(login);
        } catch (DaoException e) {
            log.error("Catching UserDaoException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }

        User candidate = new User();
        candidate.setLogin(login);
        candidate.setPassword(password);

        try {
            userDAO.addUser(candidate);
            currentUser = userDAO.getUserByLogin(login);

        } catch (DaoException e) {
            log.error("Catching UserDaoException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }

        HttpSession session = request.getSession();

        session.setAttribute("currentUser", currentUser);
        request.getRequestDispatcher("jsp/welcome.jsp").forward(request, response);
    }
}
