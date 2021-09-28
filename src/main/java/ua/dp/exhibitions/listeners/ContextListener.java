package ua.dp.exhibitions.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.filters.UpdateUserFilter;

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

        String logPath=context.getRealPath("/WEB-INF/");
        logPath.replace('/','\\');

        System.setProperty("logFile",logPath);

        final Logger log = LogManager.getLogger(ContextListener.class);
        log.debug("logPath ="+logPath);

    }
}
