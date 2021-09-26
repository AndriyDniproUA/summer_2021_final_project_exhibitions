package ua.dp.exhibitions.web.cabinet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.TicketsDAO;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.daoUtil.TicketsDaoUtil;
import ua.dp.exhibitions.daoUtil.UserDaoUtil;
import ua.dp.exhibitions.entities.Ticket;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalCabinetServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(PersonalCabinetServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TicketsDAO ticketsDAO = TicketsDAO.getInstance();

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        int userId = currentUser.getId();

        List<Ticket> tickets = null;
        try {
            tickets = ticketsDAO.getTicketsByUserId(userId);
        } catch (DaoException e) {
            log.error("Catching DaoException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
        request.setAttribute("tickets", tickets);

        request.getRequestDispatcher("jsp/cabinet/personal_cabinet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TicketsDAO ticketsDAO = TicketsDAO.getInstance();

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        int userId = currentUser.getId();

        List<Ticket> tickets = null;
        try {
            tickets = ticketsDAO.getTicketsByUserId(userId);
        } catch (DaoException e) {
            log.error("Catching DaoException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
        request.setAttribute("tickets", tickets);

        request.getRequestDispatcher("jsp/users/information.jsp").forward(request, response);
    }
}

