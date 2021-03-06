package ua.dp.exhibitions.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.daoUtil.ShowsDaoValidateShowRooms;
import ua.dp.exhibitions.datasource.CustomDataSource;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.exceptions.DaoException;
import ua.dp.exhibitions.utils.DbUtil;
import ua.dp.exhibitions.daoUtil.ShowsDaoUtil;

import java.sql.*;
import java.util.*;

/**
 * ShowsDAO provides access to show records in the database
 */
public class ShowsDAO {
    private static final Logger log = LogManager.getLogger(ShowsDAO.class);
    private static ShowsDAO instance;

    private ShowsDAO() {
    }

    /**
     * getInstance() returns a single instance of the ShowsDAO (singleton)
     */
    public static ShowsDAO getInstance() {
        if (instance == null) {
            instance = new ShowsDAO();
        }
        return instance;
    }


    /**
     * getSelectedShows(limit, offset) returns a sorted (by date/by name/by price)
     * and filtered (by single date) list of shows
     */
    public List<Show> getSelectedShows(String orderBy, String someDate, int limit, int offset) throws DaoException{
        log.debug("Calling getShows(with parameters) in ShowsDAO");
        List<Show> shows;

        String sql = ShowsDaoUtil.combineSqlForShowSearch(orderBy, someDate, limit, offset);

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = CustomDataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            shows = ShowsDaoUtil.mapShows(rs);

        } catch (SQLException e) {
            log.error(e.getStackTrace() + " Message:" + e.getMessage());
            throw new DaoException("Failed to extract selected shows from the database!",e);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(st);
            DbUtil.close(con);
        }
        return shows;
    }

    /**
     * getAllShows() returns a sorted a list of shows
     */
    public List<Show> getAllShows() throws DaoException{
        log.debug("Calling getAllShows in ShowsDAO");
        List<Show> shows = new ArrayList<>();

        String sql = "SELECT id, subject, date_begins, date_ends, time_opens, time_closes, price FROM shows";

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = CustomDataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            shows = ShowsDaoUtil.mapShows(rs);

        } catch (SQLException e) {
            log.error(e.getStackTrace() + " Message:" + e.getMessage());
            throw new DaoException("Failed to extract all shows from the database!",e);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(st);
            DbUtil.close(con);
        }
        return shows;
    }


    /**
     * getShowById() returns a single show by ID
     */
    public Show getShowById(int id) throws DaoException{
        log.debug("Calling getShowById in ShowsDAO");

        String sql = "SELECT id, subject, date_begins, date_ends, time_opens, time_closes, price FROM shows WHERE id=?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Show show=null;

        try {
            con = CustomDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            show = ShowsDaoUtil.mapShows(rs).get(0);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Failed to find the show in the database!",e);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(ps);
            DbUtil.close(con);
        }
        return show;
    }


    /**
     * addShow() adds a new show to the database
     */
    public List<String> addShow(Show show) throws DaoException{
        Connection con = null;
        PreparedStatement ps = null;

        List<String> validationFeedback = ShowsDaoValidateShowRooms
                .validateShowRoomsAvailability(show);

        if (validationFeedback.size() == 0) {
            try {
                con = CustomDataSource.getConnection();
                con.setAutoCommit(false);

                String sql = "INSERT INTO shows\n" +
                        "VALUES (DEFAULT, ?,?,?,?,?,?)";

                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, show.getSubject());
                ps.setObject(2, show.getDateBegins());
                ps.setObject(3, show.getDateEnds());
                ps.setString(4, show.getTimeOpens().toString());
                ps.setString(5, show.getTimeCloses().toString());
                ps.setDouble(6, show.getPrice());
                ps.executeUpdate();

                ShowsDaoUtil.setShowIdByDb(show, ps);
                ShowsDaoUtil.bookRooms(show, con);

                con.commit();
                log.trace("Show " + show.getSubject() + " added to the shows table");
            } catch (SQLException e) {
                log.error(e.getStackTrace() + " Message:" + e.getMessage());
                DbUtil.rollback(con);
                throw new DaoException("Failed to add new show to the database!",e);
            } finally {
                DbUtil.close(ps);
                DbUtil.close(con);
            }
        }
        return validationFeedback;
    }


    /**
     * deleteShow() deletes show from the database by ID
     */
    public void deleteShow(int id) throws DaoException{
        Connection con = null;
        PreparedStatement ps = null;


        try {
            String sql = "DELETE FROM shows WHERE id=?";
            con = CustomDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException e) {
            log.error(e.getStackTrace() + " Message:" + e.getMessage());
            throw new DaoException("Failed to delete a show from the database!",e);

        } finally {
            DbUtil.close(ps);
            DbUtil.close(con);
        }
        log.trace("Show id=" + id + " was deleted from the users table");
    }

    /**
     * getNoOfShows() returns a total number of shows
     */
    public int getNoOfShows(String someDate) throws DaoException {
        log.debug("Calling getNoOfShows in ShowsDAO");

        Connection con=null;
        Statement st=null;
        ResultSet rs=null;

        try {
            String sql = "SELECT COUNT(id) FROM shows";
            if (someDate != null && !"".equals(someDate)) {
                sql += " WHERE date_begins<='" + someDate + "' AND date_ends>='" + someDate + "'";
            }

            con = CustomDataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()){
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Unable to get No of shows from ShowsDAO!",e);

        } finally {
            DbUtil.close(rs);
            DbUtil.close(st);
            DbUtil.close(con);
        }
        return 0;
    }
}
