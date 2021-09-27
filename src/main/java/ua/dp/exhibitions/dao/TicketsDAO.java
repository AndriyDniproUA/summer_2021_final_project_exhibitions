package ua.dp.exhibitions.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.daoUtil.ShowsDaoUtil;
import ua.dp.exhibitions.daoUtil.TicketsDaoUtil;
import ua.dp.exhibitions.datasource.CustomDataSource;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.entities.Ticket;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;
import ua.dp.exhibitions.utils.DbUtil;
import ua.dp.exhibitions.utils.Util;

import java.sql.*;
import java.util.List;

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


    public void buyTicket(int userId, int showId, int quantity, String date) throws DaoException {
        Connection con = null;

        try {
            con = CustomDataSource.getConnection();
            con.setAutoCommit(false);

            writeIntoTicketsTable(userId, showId, quantity, date, con);
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

    private void writeIntoTicketsTable(int userId, int showId, int quantity, String date, Connection con) throws DaoException {
        PreparedStatement ps = null;

        String sql = "INSERT INTO tickets (user_id, show_id, quantity, date)\n" +
                "VALUES  (?, ?, ?, ?)";

        ///***************************************
        System.out.println("Date:"+date);
        //****************************************

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, showId);
            ps.setInt(3, quantity);
            ps.setObject(4, Util.convertStringToLocalDate(date));

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
            throw new DaoException("We are sorry!\n Your balance:"
                    +user.getBalance()+" is no sufficient to purchase the selected tickets!");
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

    public List<Ticket> getTicketsByUserId(int user_id) throws DaoException{
        log.debug("Calling getTicketsByUserId in TicketsDAO");
        List<Ticket> tickets=null;

        String sql = "SELECT t.order_id, t.show_id, t.date, s.subject,  t.quantity, s.price*t.quantity AS cost\n" +
                "FROM tickets t\n" +
                "         JOIN users u ON t.user_id = u.id\n" +
                "         JOIN shows s ON t.show_id = s.id\n" +
                "WHERE u.id = ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = CustomDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,user_id);
            rs = ps.executeQuery();

            tickets = TicketsDaoUtil.mapTickets(rs);

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Failed to return tickets from the database!",e);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(ps);
            DbUtil.close(con);
        }
        return tickets;
    }

    public int countTicketsByShowId(int show_id) throws DaoException{
        log.debug("Calling countTicketsByShowId in TicketsDAO");

        String sql="SELECT SUM(quantity) FROM tickets t JOIN shows s ON t.show_id = s.id WHERE s.id = ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int totalNumber=0;

        try {
            con = CustomDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, show_id);
            rs = ps.executeQuery();


            if (rs.next()){
                totalNumber=rs.getInt(1);
            }


        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Failed to count tickets for the show! "+e.getMessage(),e);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(ps);
            DbUtil.close(con);
        }

        return totalNumber;
    }
}
