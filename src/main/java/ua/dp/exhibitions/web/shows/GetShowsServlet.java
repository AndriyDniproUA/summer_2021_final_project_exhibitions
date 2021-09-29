package ua.dp.exhibitions.web.shows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * GetShowsServlet handles shows page
 */
public class GetShowsServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetShowsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in GetShowsServlet doPost");

        int page=1;
        int itemNum=1;
        int recordsPerPage=10;
        String orderBy;


        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
            itemNum=(page-1)*recordsPerPage+1;
        }

        if (request.getParameter("orderBy")!=null){
            orderBy = request.getParameter("orderBy");
            request.getSession().setAttribute("orderBy",orderBy);
        } else {
           orderBy =(String)request.getSession().getAttribute("orderBy");
        }

        ShowsDAO showsDAO = ShowsDAO.getInstance();

        String someDate = request.getParameter("someDate");

        List<Show> shows = null;
        try {
            shows = showsDAO.getSelectedShows(orderBy,someDate,recordsPerPage,(page-1)*recordsPerPage);

            int noOfRecords=showsDAO.getNoOfShows(someDate);
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("shows",shows);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("itemNum", itemNum);


        } catch (DaoException e) {
            log.trace("Catching DaoException: "+e.getMessage());
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }


        log.trace("redirecting to shows.jsp");
        request.getRequestDispatcher("jsp/shows/shows.jsp").forward(request, response);
    }
}




