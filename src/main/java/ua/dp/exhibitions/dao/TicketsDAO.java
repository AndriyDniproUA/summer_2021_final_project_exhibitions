package ua.dp.exhibitions.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.datasource.CustomDataSource;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;
import ua.dp.exhibitions.utils.DbUtil;

import java.sql.*;

public class TicketsDAO {
    private static final Logger log = LogManager.getLogger(TicketsDAO.class);
    private static TicketsDAO instance;

    private TicketsDAO() {
    }

    public static TicketsDAO getInstance() {
        if (instance == null) {
            instance = new TicketsDAO();
        }
        return instance;
    }


    public void buyTicket(int userId, int showId, int quantity) throws DaoException {
        Connection con = null;

        try {
            con = CustomDataSource.getConnection();
            con.setAutoCommit(false);

            writeIntoTicketsTable(userId, showId, quantity, con);
            subtractFromUserBalance(userId,showId,quantity,con);

            con.commit();
            log.trace("A ticket was registered->  userId:" + userId + ", showId:" + showId + ", quantity:" + quantity);
        } catch (SQLException e) {
            log.error("error:" + e.getMessage());
            DbUtil.rollback(con);
            throw new DaoException(e.getMessage(), e);
        } finally {
            DbUtil.close(con);
        }


    }

    private void writeIntoTicketsTable(int userId, int showId, int quantity, Connection con) throws DaoException {
        PreparedStatement ps = null;

        String sql = "INSERT INTO tickets (user_id, show_id, quantity)\n" +
                "VALUES  (?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, showId);
            ps.setInt(3, quantity);

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Database error:" + e.getMessage());
            throw new DaoException("Couldn't write into TICKETS table!", e);
        } finally {
            DbUtil.close(ps);
        }
    }

    private void subtractFromUserBalance(int userId, int showId, int quantity, Connection con) throws DaoException {
        ShowsDAO showsDAO = ShowsDAO.getInstance();
        Show show = showsDAO.getShowById(showId);

        UserDAO userDAO=UserDAO.getInstance();
        User user =userDAO.getUserById(userId);

        if  ((user.getBalance()-show.getPrice()*quantity)<0){
            throw new DaoException("We are sorry!\n Your balance:"+user.getBalance()+" is no sufficient to purchase the selected tickets!");
        }



        PreparedStatement ps = null;
        String sql = "UPDATE users SET balance=? WHERE id=?";


       //*****************************************************
        System.out.println("balance: "+user.getBalance());
        System.out.println("show: "+show.getSubject());
        //*****************************************************


        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, user.getBalance()-show.getPrice()*quantity);
            ps.setInt(2, userId);

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Database error:" + e.getMessage());
            throw new DaoException("Problem updating USER table!", e);
        } finally {
            DbUtil.close(ps);
        }
    }


//    public List<Show> getSelectedShows(String orderBy, String someDate) throws DaoException{
//        log.debug("Calling getShows(with parameters) in ShowsDAO");
//        List<Show> shows;
//
//        String sql = ShowsDaoUtil.combineSqlForShowSearch(orderBy, someDate);
//
//        Connection con = null;
//        Statement st = null;
//        ResultSet rs = null;
//
//        try {
//            con = CustomDataSource.getConnection();
//            st = con.createStatement();
//            rs = st.executeQuery(sql);
//            shows = ShowsDaoUtil.mapShows(rs);
//
//        } catch (SQLException e) {
//            log.error(e.getStackTrace() + " Message:" + e.getMessage());
//            throw new DaoException("Failed to extract selected shows from the database!",e);
//        } finally {
//            DbUtil.close(rs);
//            DbUtil.close(st);
//            DbUtil.close(con);
//        }
//        return shows;
//    }
//
//    public List<Show> getAllShows() throws DaoException{
//        log.debug("Calling getAllShows in ShowsDAO");
//        List<Show> shows = new ArrayList<>();
//
//        String sql = "SELECT id, subject, date_begins, date_ends, time_opens, time_closes, price FROM shows";
//
//        Connection con = null;
//        Statement st = null;
//        ResultSet rs = null;
//
//        try {
//            con = CustomDataSource.getConnection();
//            st = con.createStatement();
//            rs = st.executeQuery(sql);
//            shows = ShowsDaoUtil.mapShows(rs);
//
//        } catch (SQLException e) {
//            log.error(e.getStackTrace() + " Message:" + e.getMessage());
//            throw new DaoException("Failed to extract all shows from the database!",e);
//        } finally {
//            DbUtil.close(rs);
//            DbUtil.close(st);
//            DbUtil.close(con);
//        }
//        return shows;
//    }
//
//    public List<String> addShow(Show show) throws DaoException{
//        Connection con = null;
//        PreparedStatement ps = null;
//
//        List<String> validationFeedback = ShowsDaoValidateShowRooms
//                .validateShowRoomsAvailability(show);
//
//        if (validationFeedback.size() == 0) {
//            try {
//                con = CustomDataSource.getConnection();
//                con.setAutoCommit(false);
//
//                String sql = "INSERT INTO shows\n" +
//                        "VALUES (DEFAULT, ?,?,?,?,?,?)";
//
//                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                ps.setString(1, show.getSubject());
//                ps.setObject(2, show.getDateBegins());
//                ps.setObject(3, show.getDateEnds());
//                ps.setString(4, show.getTimeOpens().toString());
//                ps.setString(5, show.getTimeCloses().toString());
//                ps.setDouble(6, show.getPrice());
//                ps.executeUpdate();
//
//                ShowsDaoUtil.setShowIdByDb(show, ps);
//                ShowsDaoUtil.bookRooms(show, con);
//
//                con.commit();
//                log.trace("Show " + show.getSubject() + " added to the shows table");
//            } catch (SQLException e) {
//                log.error(e.getStackTrace() + " Message:" + e.getMessage());
//                DbUtil.rollback(con);
//                throw new DaoException("Failed to add new show to the database!",e);
//            } finally {
//                DbUtil.close(ps);
//                DbUtil.close(con);
//            }
//        }
//        return validationFeedback;
//    }
//
//    public void deleteShow(int id) throws DaoException{
//        Connection con = null;
//        PreparedStatement ps = null;
//
//
//        try {
//            String sql = "DELETE FROM shows WHERE id=?";
//            con = CustomDataSource.getConnection();
//            ps = con.prepareStatement(sql);
//            ps.setInt(1, id);
//            ps.execute();
//
//        } catch (SQLException e) {
//            log.error(e.getStackTrace() + " Message:" + e.getMessage());
//            throw new DaoException("Failed to delete a show from the database!",e);
//
//        } finally {
//            DbUtil.close(ps);
//            DbUtil.close(con);
//        }
//        log.trace("Show id=" + id + " was deleted from the users table");
//    }
}
