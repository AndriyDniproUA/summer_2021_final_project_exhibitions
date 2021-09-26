package ua.dp.exhibitions.web.cabinet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.daoUtil.UserDaoUtil;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PersonalCabinetUpdateUserServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(PersonalCabinetUpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/cabinet/personal_cabinet_update_user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        int userId = currentUser.getId();
        String roleAsString = currentUser.getRole();
        double currentBalance=currentUser.getBalance();
        double updatedBalance=currentBalance+Double.parseDouble(request.getParameter("deposit"));

        Integer roleId=null;
        try {
            roleId = UserDaoUtil.getRoleIdByRoleName(roleAsString);
        } catch (DaoException e) {
            log.error("Catching DaoException: " + e.getMessage());
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("login", request.getParameter("login"));
        parameters.put("password", request.getParameter("password"));
        parameters.put("role", String.valueOf(roleId));
        parameters.put("balance",String.valueOf(updatedBalance));


        log.trace("Attempting to update a user with ID:" + userId);
        UserDAO userDAO = UserDAO.getInstance();

        try {
            userDAO.updateUser(userId, parameters);
        } catch (DaoException e) {
            log.error("Catching DaoException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }


        log.trace("User ID:" + userId + " was successfully updated");
        String message = "Your data was successfully updated";


        try {
            currentUser = userDAO.getUserById(userId);
        } catch (DaoException e) {
            log.error(e.getMessage());
        }
        request.getSession().setAttribute("currentUser",currentUser);


        request.setAttribute("message", message);
        request.getRequestDispatcher("jsp/users/information.jsp").forward(request, response);
    }
}
