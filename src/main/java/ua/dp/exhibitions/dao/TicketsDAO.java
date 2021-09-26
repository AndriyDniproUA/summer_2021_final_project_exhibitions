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
}
