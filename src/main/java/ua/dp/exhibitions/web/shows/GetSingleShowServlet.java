package ua.dp.exhibitions.web.shows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.entities.Ticket;
import ua.dp.exhibitions.exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * GetSingleShowServlet handles single show page
 */
public class GetSingleShowServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetSingleShowServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in GetSingleShowServlet doPost");
        ShowsDAO showsDAO = ShowsDAO.getInstance();

        int show_id = Integer.parseInt(request.getParameter("show_id"));

        List<Show> shows = new ArrayList<>();
        Show show;
        try {
            show = showsDAO.getShowById(show_id);
            shows.add(show);

        } catch (DaoException e) {
            log.trace("Catching DaoException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }

        request.setAttribute("shows", shows);
        log.trace("redirecting to shows.jsp");
        request.getRequestDispatcher("jsp/shows/shows.jsp").forward(request, response);
    }
}
