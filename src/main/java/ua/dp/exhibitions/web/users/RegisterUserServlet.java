package ua.dp.exhibitions.web.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

public class RegisterUserServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(RegisterUserServlet.class);
    Connection connection=null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/users/register.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("Utf-8");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        log.trace("Attempt to register user with login:" + login);


        UserDAO userDAO = UserDAO.getInstance();
        User currentUser =  userDAO.getUser(login);

        if (currentUser!=null) {
            String message = "User with login " + login + "  is already registered!";
            log.trace(message);

            request.setAttribute("message",message);
            request.getRequestDispatcher("jsp/users/warning.jsp").forward(request, response);
        }

        User candidate=new User();
        candidate.setLogin(login);
        candidate.setPassword(password);

        userDAO.addUser(candidate);
        currentUser=userDAO.getUser(login);

        HttpSession session = request.getSession();

        session.setAttribute("currentUser", currentUser);
        request.getRequestDispatcher("jsp/users/welcome.jsp").forward(request, response);
    }
}
