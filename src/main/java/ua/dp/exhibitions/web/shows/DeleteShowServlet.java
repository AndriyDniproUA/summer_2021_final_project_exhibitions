package ua.dp.exhibitions.web.shows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.exceptions.DaoException;
import ua.dp.exhibitions.utils.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteShowServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(DeleteShowServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        log.trace("Attempt to delete show with id:" + id);

        ShowsDAO showsDAO = ShowsDAO.getInstance();
        try {
            showsDAO.deleteShow(id);
        } catch (DaoException e) {
            log.trace("Catching DaoException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }

        String messageBeg =
                Util.internationalizeMessage(request, "delete_show_servlet.message_beg")+" ";
        String messageEnd =
                " "+ Util.internationalizeMessage(request, "delete_show_servlet.message_end");



        String message = messageBeg + id + messageEnd;
        log.trace(message);
        request.setAttribute("message", message);
        request.getRequestDispatcher("jsp/information.jsp").forward(request, response);
    }
}

