package ua.dp.exhibitions.daoUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.ShowsDAO;
import ua.dp.exhibitions.dao.TicketsDAO;
import ua.dp.exhibitions.datasource.CustomDataSource;
import ua.dp.exhibitions.entities.Show;
import ua.dp.exhibitions.exceptions.DaoException;
import ua.dp.exhibitions.utils.DbUtil;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowsDaoUtil {
    private static final Logger log = LogManager.getLogger(ShowsDaoUtil.class);

    public static String[] mapRooms(ResultSet rs) throws SQLException {
        log.debug("Calling mapRooms in ShowsDaoUtil");
        List<String> rooms = new ArrayList<>();

        while (rs.next()) {
            rooms.add(rs.getString("room"));
        }

        String[] roomsArray = new String[rooms.size()];
        return rooms.toArray(roomsArray);
    }

    public static Map<String, Integer> getAllRooms() {
        log.debug("Calling getAllRooms in ShowsDaoUtil");

        Map<String, Integer> rooms = new HashMap<>();

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT id, room FROM rooms ORDER BY id";

            con = CustomDataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                String key = rs.getString("room");
                int value = rs.getInt("id");
                rooms.put(key, value);
            }
        } catch (SQLException throwables) {
            log.error(throwables.getStackTrace());
        } finally {
            DbUtil.close(rs);
            DbUtil.close(st);
            DbUtil.close(con);
        }
        return rooms;
    }

    public static List<Show> mapShows(ResultSet rs) throws SQLException {
        TicketsDAO ticketsDAO=TicketsDAO.getInstance();


        log.debug("Calling mapShows in ShowsDAOUtil");
        List<Show> shows = new ArrayList<>();

        while (rs.next()) {
            Show show = new Show();
            int id = rs.getInt("id");

            show.setId(id);
            show.setSubject(rs.getString("subject"));
            show.setDateBegins(((Timestamp) rs.getObject("date_begins")).toLocalDateTime().toLocalDate());
            show.setDateEnds(((Timestamp) rs.getObject("date_ends")).toLocalDateTime().toLocalDate());
            show.setTimeOpens(LocalTime.parse(rs.getString("time_opens")));
            show.setTimeCloses(LocalTime.parse(rs.getString("time_closes")));

            double price=rs.getDouble("price");
            show.setPrice(price);
            show.setRooms(getShowRooms(id));

            int ticketsSold=0;
            try {
                ticketsSold=ticketsDAO.countTicketsByShowId(id);
                show.setTicketsSold(ticketsSold);
            } catch (DaoException e) {
                log.debug(e.getMessage());
            }
            show.setTotal(price*ticketsSold);

            shows.add(show);
        }
        return shows;
    }

    private static String[] getShowRooms(int id) {
        log.debug("Calling getShowRooms in ShowsDaoUtil");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = CustomDataSource.getConnection();

            String sql = "SELECT r.room\n" +
                    "FROM shows s\n" +
                    "         JOIN shows_rooms s_r ON s.id = s_r.show_id\n" +
                    "         JOIN rooms r ON r.id = s_r.room_id\n" +
                    "WHERE s.id=?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            return mapRooms(rs);

        } catch (SQLException throwables) {
            log.error(throwables.getStackTrace() + "->Message:" + throwables.getMessage());
        } finally {
            DbUtil.close(rs);
            DbUtil.close(ps);
            DbUtil.close(con);
        }
        return null;
    }

    public static String combineSqlForShowSearch(String orderBy, String someDate) {
        log.debug("combineSqlForShowSearch() in ShowsDaoUtil");

        String sql = "SELECT id, subject, date_begins, date_ends, time_opens, time_closes, price FROM shows";
        String orderSql = "";
        String datesSql = "";

        if (orderBy == null) {
            orderSql = " ORDER BY id";
        } else {
            switch (orderBy) {
                case "bySubject":
                    orderSql = " ORDER BY subject";
                    break;
                case "byDate":
                    orderSql = " ORDER BY date_begins";
                    break;
                case "byPrice":
                    orderSql = " ORDER BY price";
                    break;
            }
        }

        if (someDate != null && !"".equals(someDate)) {
            datesSql = " WHERE date_begins<='" + someDate + "' AND date_ends>='" + someDate + "'";
        }
        return sql + datesSql + orderSql;
    }

    public static void bookRooms(Show show, Connection con) {
        PreparedStatement ps = null;

        Map<String, Integer> rooms = getAllRooms();
        String[] showRooms = show.getRooms();

        String sql = "INSERT INTO shows_rooms\n" +
                "VALUES (?, ?)";

        try {
            int showId = show.getId();
            for (String showRoom : showRooms) {
                int roomId = rooms.get(showRoom);

                ps = con.prepareStatement(sql);
                ps.setInt(1, showId);
                ps.setInt(2, roomId);
                ps.executeUpdate();
            }
            log.trace("Show " + show.getSubject() + " rooms were successfully booked");
        } catch (SQLException throwables) {
            log.error(throwables.getStackTrace() + " Message:" + throwables.getMessage());
        } finally {
            DbUtil.close(ps);
        }
    }

    public static void setShowIdByDb(Show show, PreparedStatement ps) {
        try (ResultSet resultSet = ps.getGeneratedKeys()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                show.setId(id);
            }
        } catch (SQLException e) {
            log.error(e.getStackTrace() + " Message:" + e.getMessage());
        }
    }
}
