package ua.dp.exhibitions.web.shows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllShowsServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetAllShowsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in GetAllShowsServlet doPost");
        ShowsDAO showsDAO = ShowsDAO.getInstance();

        String orderBy = request.getParameter("orderBy");
        String someDate = request.getParameter("someDate");

        List<Show> shows = null;
        try {
            shows = showsDAO.getSelectedShows(orderBy,someDate);
        } catch (DaoException e) {
            log.trace("Catching DaoException: "+e.getMessage());
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }

        request.setAttribute("shows",shows);
        log.trace("redirecting to shows.jsp");
        request.getRequestDispatcher("jsp/shows/shows.jsp").forward(request, response);
    }
}
