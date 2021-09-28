package ua.dp.exhibitions.web.tickets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.dao.TicketsDAO;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;
import ua.dp.exhibitions.utils.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BuyTicketServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BuyTicketServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User currentUser = (User) request.getSession().getAttribute("currentUser");

        int userId = currentUser.getId();
        int showId = Integer.parseInt(request.getParameter("showId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String date=request.getParameter("date");


        TicketsDAO ticketsDAO = TicketsDAO.getInstance();
        try {
            ticketsDAO.buyTicket(userId, showId, quantity,date);
        } catch (DaoException e) {
            log.error("Catching DaoException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }

        Show show=null;
        String message=null;
        ShowsDAO showsDAO = ShowsDAO.getInstance();
        try {
            show = showsDAO.getShowById(showId);

            String messagePart1 =
                    Util.internationalizeMessage(request, "buy_ticket_servlet.message_part1")+" ";
            String messagePart2 =" "+
                    Util.internationalizeMessage(request, "buy_ticket_servlet.message_part2")+" ";
            String messagePart3 =" "+
                    Util.internationalizeMessage(request, "buy_ticket_servlet.message_part3")+" ";
            String messagePart4 =" "+
                    Util.internationalizeMessage(request, "buy_ticket_servlet.message_part4");



            message = messagePart1 + currentUser.getLogin() +
                    messagePart2 + quantity + messagePart3 + show.getSubject()+messagePart4;
        } catch (DaoException e) {
            log.error(e.getMessage());
        }


        UserDAO userDAO = UserDAO.getInstance();
        try {
            currentUser = userDAO.getUserById(userId);
        } catch (DaoException e) {
            log.error(e.getMessage());
        }

        request.getSession().setAttribute("currentUser",currentUser);
        request.setAttribute("message", message);
        request.getRequestDispatcher("jsp/information.jsp").forward(request, response);
    }
}