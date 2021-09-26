package ua.dp.exhibitions.web.tickets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.dao.TicketsDAO;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;

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

        //*******************************


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
            message = "Dear " + currentUser.getLogin() +
                    " thank you for purchasing " + quantity + " ticket(s) to " + show.getSubject()+" show";
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

//        if (currentUser == null || !currentUser.getRole().equals("admin")) {
//            log.trace("User was not allowed to add a show");
//            message = "Only administrators are allowed to add Shows!";
//            request.setAttribute("message", message);
//            request.getRequestDispatcher("jsp/shows/warning_shows.jsp").forward(request, response);
//
//        } else if (request.getParameterValues("rooms")==null) {
//            log.trace("Empty room list was provided");
//
//            ShowsDAO showsDAO = ShowsDAO.getInstance();
//            Map<String, Integer> allRooms = showsDAO.getAllRooms();
//            request.setAttribute("allRooms", allRooms);
//
//            message = "Choose at least one room for the show!";
//            request.setAttribute("message", message);
//            request.getRequestDispatcher("jsp/shows/add_show.jsp").forward(request, response);
//
//
//        } else {
//            Show show = new Show();
//            String subject = request.getParameter("subject");
//
//            show.setSubject(subject);
//            show.setDateBegins(Util.convertStringToLocalDate(request.getParameter("date_begins")));
//            show.setDateEnds(Util.convertStringToLocalDate(request.getParameter("date_ends")));
//            show.setTimeOpens(LocalTime.parse(request.getParameter("time_opens")));
//            show.setTimeCloses(LocalTime.parse(request.getParameter("time_closes")));
//            show.setPrice(Double.parseDouble(request.getParameter("price")));
//            show.setRooms(request.getParameterValues("rooms"));
//
//            ShowsDAO showsDAO = ShowsDAO.getInstance();
//            List<String> validationFeedback = showsDAO.addShow(show);
//
//            if (validationFeedback.size() != 0) {
//                request.setAttribute("messageList", validationFeedback);
//                request.getRequestDispatcher("jsp/shows/warning_shows.jsp").forward(request, response);
//            } else {
//                request.setAttribute("message", "Show " + subject + " was added successfully to the shows database");
//                request.getRequestDispatcher("jsp/shows/information.jsp").forward(request, response);
//            }
//        }


}