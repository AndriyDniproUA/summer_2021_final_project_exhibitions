package ua.dp.exhibitions.web.shows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class DeleteShowServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(DeleteShowServlet.class);
    Connection connection = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String message = "";
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        if (currentUser == null || !currentUser.getRole().equals("admin")) {
            log.trace("User was not allowed to delete a show");
            message = "Only administrator are allowed to delete Shows!";
            request.setAttribute("message", message);
            request.getRequestDispatcher("jsp/shows/warning_shows.jsp").forward(request, response);

        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            log.trace("Attempt to delete show with id:" + id);

            ShowsDAO showsDAO = ShowsDAO.getInstance();
            if (showsDAO.deleteShow(id)) {
                message = "Show " + id + " was successfully removed from the Shows table";
                log.trace(message);
                request.setAttribute("message", message);
                request.getRequestDispatcher("jsp/shows/information.jsp").forward(request, response);
            } else {
                message = "Show id= " + id + " was not removed from the Shows table";
                log.trace(message);
                request.setAttribute("message", message);
                request.getRequestDispatcher("jsp/shows/warning_shows.jsp").forward(request, response);
            }
        }
    }
}
