package ua.dp.exhibitions.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * AdminAccessOnlyFilter is a filter class
 * providing UTF-8 encoding for all requests and responses
 */
public class CharacterSetFilter implements Filter{

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        next.doFilter(request, response);
    }

}
