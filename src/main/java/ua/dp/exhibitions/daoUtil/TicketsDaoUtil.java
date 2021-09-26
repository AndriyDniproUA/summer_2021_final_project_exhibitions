package ua.dp.exhibitions.daoUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.datasource.CustomDataSource;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.entities.Ticket;
import ua.dp.exhibitions.utils.DbUtil;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketsDaoUtil {
    private static final Logger log = LogManager.getLogger(TicketsDaoUtil.class);

    public static List<Ticket> mapTickets(ResultSet rs) throws SQLException {
        log.debug("Calling mapTickets in TicketsDAOUtil");
        List<Ticket> tickets = new ArrayList<>();

        while (rs.next()) {
            Ticket ticket = new Ticket();

            ticket.setOrder_id(rs.getInt("order_id"));
            ticket.setShow_id(rs.getInt("show_id"));
            ticket.setDate(((Timestamp) rs.getObject("date")).toLocalDateTime().toLocalDate());
            ticket.setSubject(rs.getString("subject"));
            ticket.setQuantity(rs.getInt("quantity"));
            ticket.setCost(rs.getDouble("cost"));

            tickets.add(ticket);
        }
        return tickets;
    }


}
