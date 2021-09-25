package ua.dp.exhibitions.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.entities.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DbUtil {
    private static final Logger log = LogManager.getLogger(DbUtil.class);

    public static void close(AutoCloseable ac){
        try {
            if (ac!=null) {
                ac.close();
            }
        } catch (Exception e) {
            log.error(e.getStackTrace());
        }
    }

    public static void rollback(Connection con){
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                log.error(e.getStackTrace() + " Message:" + e.getMessage());
            }
        }
    }









}
