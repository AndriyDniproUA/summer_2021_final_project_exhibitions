package ua.dp.exhibitions.web.shows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.entities.Show;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
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


        String orderBy = request.getParameter("orderBy");
        String dateBegins = request.getParameter("date_begins");
        String dateEnds = request.getParameter("date_ends");

        //*************************
        System.out.println("in Servlet order: "+orderBy);
        System.out.println("begins: "+dateBegins);
        System.out.println("ends: "+dateBegins);
        //*****************************

        ShowsDAO showsDAO = ShowsDAO.getInstance();

        List<Show> shows = showsDAO.getShows(orderBy,dateBegins,dateEnds);
        request.setAttribute("shows",shows);

        log.trace("redirecting to shows.jsp");
        request.getRequestDispatcher("jsp/shows/shows.jsp").forward(request, response);
    }
}
