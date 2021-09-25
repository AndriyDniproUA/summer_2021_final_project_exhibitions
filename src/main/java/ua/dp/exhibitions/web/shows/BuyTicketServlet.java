package ua.dp.exhibitions.web.shows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.utils.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class BuyTicketServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BuyTicketServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShowsDAO showsDAO = ShowsDAO.getInstance();

//        Map<String, Integer> allRooms = showsDAO.getAllRooms();
//        request.setAttribute("allRooms", allRooms);
//        request.getRequestDispatcher("jsp/shows/add_show.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String message = "";
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        int userId = currentUser.getId();
        String number = request.getParameter("number");
        String showId = request.getParameter("showId");

        //*************************************************
        System.out.println("userId->"+userId );
        System.out.println("showId->"+showId );
        System.out.println("number->"+number );
        //**************************************************





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
}