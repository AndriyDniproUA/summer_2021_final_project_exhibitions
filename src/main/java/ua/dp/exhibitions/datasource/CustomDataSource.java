package ua.dp.exhibitions.datasource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class CustomDataSource {
    private static Context envCtx;
    private static DataSource ds;
    private static final Logger log = LogManager.getLogger(CustomDataSource.class);

    static {
        try {
            envCtx = (Context) (new InitialContext().lookup("java:comp/env"));
            ds = (DataSource) envCtx.lookup("jdbc/postgres");
        } catch (NamingException e) {
            log.info(e.getMessage());
        }
    }





    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
