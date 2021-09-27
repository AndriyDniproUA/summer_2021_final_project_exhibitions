package ua.dp.exhibitions.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {

        ServletContext context = event.getServletContext();
        String localesFileName = context.getInitParameter("locales");

        String localesFileRealPath = context.getRealPath(localesFileName);

        Properties locales = new Properties();
        try {
            locales.load(new FileInputStream(localesFileRealPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        context.setAttribute("locales", locales);
        //********************************
        locales.list(System.out);
        //********************************
    }
}
