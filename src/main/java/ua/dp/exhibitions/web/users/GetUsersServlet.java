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
import java.util.List;


/**
 * GetUsersPageServlet fetches users list per page
 */
public class GetUsersServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetUsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.trace("Calling GetUsersPageServlet class doGet method");

        int page=1;
        int itemNum=1;
        int recordsPerPage=10;

        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
            itemNum=(page-1)*recordsPerPage+1;
        }
        UserDAO userDAO = UserDAO.getInstance();

        try {
            List<User> users = userDAO.getAllUsers(recordsPerPage,(page-1)*recordsPerPage);

            int noOfRecords=userDAO.getNoOfUsers();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("users", users);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("itemNum", itemNum);

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
