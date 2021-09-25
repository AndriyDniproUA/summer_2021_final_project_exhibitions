package ua.dp.exhibitions.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.daoUtil.ShowsDaoUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class AddShowServletInputValidation implements Filter {
    private static final Logger log = LogManager.getLogger(AddShowServletInputValidation.class);
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {

        HttpServletRequest httpRequest =((HttpServletRequest)request);

        if (request.getParameterValues("rooms") == null
                && httpRequest.getMethod().equalsIgnoreCase("POST"))  {
            log.trace("Empty room list was provided");
            String message = "Choose at least one room for the show!";

            Map<String, Integer> allRooms = ShowsDaoUtil.getAllRooms();
            request.setAttribute("allRooms", allRooms);
            request.setAttribute("message", message);
            request.getRequestDispatcher("jsp/shows/add_show.jsp").forward(request, response);
        }

        next.doFilter(request, response);
    }

}

