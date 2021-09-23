package ua.dp.exhibitions.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.datasource.CustomDataSource;
import ua.dp.exhibitions.entities.Show;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public List<Show> getShows(String orderBy, String dateBegins, String dateEnds) {
        log.debug("Calling getShows(with parameters) in ShowsDAO");
        List<Show> shows = new ArrayList<>();

        String sql = combineSqlForShowSearch(orderBy, dateBegins, dateEnds);


        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = CustomDataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            shows = mapShows(rs);

        } catch (SQLException throwables) {
            log.error(throwables.getStackTrace()+" Message:"+throwables.getMessage());
        } finally {
            close(rs);
            close(st);
            close(con);
        }
        return shows;
    }


    private String combineSqlForShowSearch(String orderBy, String dateBegins, String dateEnds) {
        log.debug("combineSqlForShowSearch() in ShowsDAO");
        //**************************************************************************
        System.out.println(orderBy);
        System.out.println(dateBegins);
        System.out.println(dateEnds);
        //**************************************************************************




        String sql = "SELECT id, subject, date_begins, date_ends, time_opens, time_closes, price FROM shows";
        String orderSql = "";
        String datesSql="";

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


        if(dateBegins!=null && dateEnds!=null){
            if (!"".equals(dateBegins) && "".equals(dateEnds)){
                datesSql =" WHERE date_begins='"+dateBegins+"'";
            }

            if ("".equals(dateBegins) && !"".equals(dateEnds)){
                datesSql =" WHERE date_ends='"+dateEnds+"'";
            }

            if (!"".equals(dateBegins) && !"".equals(dateEnds)){
                datesSql =" WHERE date_begins>='"+dateBegins+"' AND date_ends<='"+dateEnds+"'";
            }
        }


        //*****************************************
        System.out.println(sql+datesSql+orderSql);
        //******************************************
        return sql+datesSql+orderSql;
    }




    public List<Show> getShows() {
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
            shows = mapShows(rs);

        } catch (SQLException throwables) {
            log.error(throwables.getStackTrace()+" Message:"+throwables.getMessage());
        } finally {
            close(rs);
            close(st);
            close(con);
        }
        return shows;
    }

    public Map<String, Integer> getAllRooms() {
        log.debug("Calling getAllRooms in ShowsDAO");

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
            close(rs);
            close(st);
            close(con);
        }
        return rooms;
    }




        public List<String> addShow (Show show){
            Connection con = null;
            PreparedStatement ps = null;

            List<String> validationFeedback = validateShowRoomsAvailability(show);


            if (validationFeedback.size() == 0) {
                try {
                    con = CustomDataSource.getConnection();
                    con.setAutoCommit(false);

                    String sql = "INSERT INTO shows\n" +
                            "VALUES (DEFAULT, ?,?,?,?,?,?)";

                    ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, show.getSubject());
//                ps.setString(2, Util.convertLocalDateToString(show.getDateBegins()));
//                ps.setString(3, Util.convertLocalDateToString(show.getDateEnds()));

                    ps.setObject(2, show.getDateBegins());
                    ps.setObject(3, show.getDateEnds());
                    ps.setString(4, show.getTimeOpens().toString());
                    ps.setString(5, show.getTimeCloses().toString());
                    ps.setDouble(6, show.getPrice());
                    ps.executeUpdate();

                    //Getting id from the database after show  addition
                    setShowIdByDb(show, ps);

                    //Booking rooms by creating entries in SHOWS_ROOMS table
                    bookRooms(show, con);

                    con.commit();
                    log.trace("Show " + show.getSubject() + " added to the shows table");
                } catch (SQLException throwables) {
                    log.error(throwables.getStackTrace() + " Message:" + throwables.getMessage());
                    if (con != null) {
                        try {
                            con.rollback();
                        } catch (SQLException e) {
                            log.error(e.getStackTrace() + " Message:" + e.getMessage());
                        }
                    }
                } finally {
                    if (con != null) {
                        try {
                            con.setAutoCommit(true);
                        } catch (SQLException throwables) {
                            log.error(throwables.getStackTrace() + " Message:" + throwables.getMessage());
                        }
                    }
                    close(ps);
                    close(con);
                }
            }
            return validationFeedback;
        }


        private void setShowIdByDb (Show show, PreparedStatement ps){
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    show.setId(id);
                }
            } catch (SQLException e) {
                log.error(e.getStackTrace() + " Message:" + e.getMessage());
            }
        }

        private void bookRooms (Show show, Connection con){
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
                close(ps);
            }
        }


        private List<String> validateShowRoomsAvailability (Show show){
            List<String> validationFeedback = new ArrayList<>();

            List<Show> existingShows = getShows();

            LocalDate newShowBegins = show.getDateBegins();
            LocalDate newShowEnds = show.getDateEnds();
            String[] newShowRooms = show.getRooms();

            for (Show existingShow : existingShows) {
                LocalDate exShowBegins = existingShow.getDateBegins();
                LocalDate exShowEnds = existingShow.getDateEnds();
                String[] exShowRooms = existingShow.getRooms();

                if (haveCommonRooms(newShowRooms, exShowRooms) &&
                        shareCommonDates(newShowBegins, newShowEnds, exShowBegins, exShowEnds)) {
                    String message = "Conflict with exhibition " + existingShow.getSubject();
                    validationFeedback.add(message);
                }
            }
            return validationFeedback;
        }

        private boolean haveCommonRooms (String[]newShowRooms, String[]exShowRooms){
            for (String newShowRoom : newShowRooms) {
                for (String exShowRoom : exShowRooms) {
                    if (newShowRoom.equals(exShowRoom)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean shareCommonDates (LocalDate nShowBeg, LocalDate nShowEnd,
                LocalDate exShowBeg, LocalDate exShowEnd){
            // new show begins during existing show period
            if (nShowBeg.isAfter(exShowBeg) && nShowBeg.isBefore(exShowEnd)) {
                return true;
            }
            //new show ends during existing show period
            if (nShowEnd.isAfter(exShowBeg) && nShowEnd.isBefore(exShowEnd)) {
                return true;
            }
            //new show begins at the exact date with existing show beginning or end
            if (nShowBeg.isEqual(exShowBeg) || nShowBeg.isEqual(exShowEnd)) {
                return true;
            }
            //new show ends at the exact date with existing show beginning or end
            if (nShowEnd.isEqual(exShowBeg) || nShowEnd.isEqual(exShowEnd)) {
                return true;
            }
            return false;
        }


//    public User getUser(String login) {
//        User user = null;
//
//        Connection con=null;
//        PreparedStatement ps=null;
//        ResultSet rs=null;
//
//        try {
//            String sql = "SELECT u.id, u.login, u.password, r.role FROM users u JOIN roles r ON u.role=r.id WHERE u.login = ?";
//            con = CustomDataSource.getConnection();
//            ps = con.prepareStatement(sql);
//            ps.setString(1, login);
//            ps.execute();
//
//            rs = ps.executeQuery();
//            System.out.println(rs);
//
//            List<User> users = mapUsers(rs);
//            if (users.size()!=0) {
//                user = users.get(0);
//            }
//        } catch (SQLException throwables) {
//            log.error(throwables.getStackTrace());
//        } finally {
//            close(rs);
//            close(ps);
//            close(con);
//        }
//        return user;
//    }


//    public boolean updateUser(String userLogin, Map<String, String> params) {
//        Connection con=null;
//        PreparedStatement ps=null;
//        ResultSet rs=null;
//
//
//
//        try {
//            String sql="UPDATE users SET login=?, password=?, role=? WHERE login = ?";;
//            con = CustomDataSource.getConnection();
//
//            ps = con.prepareStatement(sql);
//            ps.setString(1, params.get("login"));
//            ps.setString(2, params.get("password"));
//            ps.setInt(3, Integer.parseInt(params.get("role")));
//            ps.setString(4, userLogin);
//
//
//
//
//            ps.execute();
//
//
//            log.trace("User " + userLogin+" successfully updated");
//
//        } catch (SQLException throwables) {
//            log.error(throwables.getStackTrace()+" Message:"+throwables.getMessage());
//            return false;
//        } finally {
//            close(rs);
//            close(ps);
//            close(con);
//        }
//        return true;
//    }


        public boolean deleteShow ( int id){
            Connection con = null;
            PreparedStatement ps = null;


            try {
                String sql = "DELETE FROM shows WHERE id=?";
                con = CustomDataSource.getConnection();
                ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.execute();

            } catch (SQLException throwables) {
                log.error(throwables.getStackTrace() + " Message:" + throwables.getMessage());
                return false;
            } finally {
                close(ps);
                close(con);
            }
            log.trace("Show id=" + id + " was deleted from the users table");
            return true;
        }


        private List<Show> mapShows (ResultSet rs) throws SQLException {
            log.debug("Calling mapShows in ShowsDAO");

            List<Show> shows = new ArrayList<>();

            while (rs.next()) {
                Show show = new Show();
                int id = rs.getInt("id");

                show.setId(id);
                show.setSubject(rs.getString("subject"));
//            show.setDateBegins(Util.convertStringToLocalDate(rs.getString("date_begins")));
//            show.setDateEnds(Util.convertStringToLocalDate(rs.getString("date_ends")));
                show.setDateBegins(((Timestamp) rs.getObject("date_begins")).toLocalDateTime().toLocalDate());
                show.setDateEnds(((Timestamp) rs.getObject("date_ends")).toLocalDateTime().toLocalDate());
                show.setTimeOpens(LocalTime.parse(rs.getString("time_opens")));
                show.setTimeCloses(LocalTime.parse(rs.getString("time_closes")));
                show.setPrice(rs.getDouble("price"));
                show.setRooms(getShowRooms(id));

                shows.add(show);
            }
            return shows;
        }

        private String[] getShowRooms ( int id){
            log.debug("Calling getShowRooms in ShowsDAO");

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
                close(rs);
                close(ps);
                close(con);
            }
            return null;
        }

        private String[] mapRooms (ResultSet rs) throws SQLException {
            log.debug("Calling mapRooms in ShowsDAO");

            List<String> rooms = new ArrayList<>();

            while (rs.next()) {
                rooms.add(rs.getString("room"));
            }

            String[] roomsArray = new String[rooms.size()];
            return rooms.toArray(roomsArray);
        }

        private void close (AutoCloseable ac){
            try {
                if (ac != null) {
                    ac.close();
                }
            } catch (Exception e) {
                log.error(e.getStackTrace());
            }
        }
    }
