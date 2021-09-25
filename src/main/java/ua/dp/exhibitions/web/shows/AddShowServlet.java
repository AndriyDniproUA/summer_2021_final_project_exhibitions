package ua.dp.exhibitions.web.shows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.daoUtil.ShowsDaoUtil;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.exceptions.DaoException;
import ua.dp.exhibitions.utils.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AddShowServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(AddShowServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Integer> allRooms = ShowsDaoUtil.getAllRooms();
        request.setAttribute("allRooms", allRooms);
        request.getRequestDispatcher("jsp/shows/add_show.jsp").forward(request, response);
    }

    //TODO input validation
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            Show show = new Show();
            String subject = request.getParameter("subject");
            show.setSubject(subject);
            show.setDateEnds(Util.convertStringToLocalDate(request.getParameter("date_ends")));
            show.setDateBegins(Util.convertStringToLocalDate(request.getParameter("date_begins")));
            show.setTimeOpens(LocalTime.parse(request.getParameter("time_opens")));
            show.setTimeCloses(LocalTime.parse(request.getParameter("time_closes")));
            show.setPrice(Double.parseDouble(request.getParameter("price")));
            show.setRooms(request.getParameterValues("rooms"));
            ShowsDAO showsDAO = ShowsDAO.getInstance();

            List<String> validationFeedback = null;
            try {
                validationFeedback = showsDAO.addShow(show);
            } catch (DaoException e) {
                log.trace("Catching DaoException: " + e.getMessage());
                request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            }

            if (validationFeedback.size() != 0) {
                request.setAttribute("messageList", validationFeedback);
                request.getRequestDispatcher("jsp/warning.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Show " + subject + " was added successfully to the shows database");
                request.getRequestDispatcher("jsp/information.jsp").forward(request, response);
            }
        }
    }
