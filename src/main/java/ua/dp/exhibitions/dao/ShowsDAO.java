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

public class ShowsDAO {
    private static final Logger log = LogManager.getLogger(ShowsDAO.class);
    private static ShowsDAO instance;

    private ShowsDAO() {
    }

    public static ShowsDAO getInstance() {
        if (instance == null) {
            instance = new ShowsDAO();
        }
        return instance;
    }

    public List<Show> getSelectedShows(String orderBy, String someDate) throws DaoException{
        log.debug("Calling getShows(with parameters) in ShowsDAO");
        List<Show> shows;

        String sql = ShowsDaoUtil.combineSqlForShowSearch(orderBy, someDate);

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
}
