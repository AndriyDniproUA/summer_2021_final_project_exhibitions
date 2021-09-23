package ua.dp.exhibitions.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.datasource.CustomDataSource;
import ua.dp.exhibitions.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDAO {
    private static final Logger log = LogManager.getLogger(UserDAO.class);
    private static UserDAO instance;

    private UserDAO(){}

    public static UserDAO getInstance(){
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public List<User> getAllUsers() {
        log.debug("Calling getAllUsers in UserDAO");

        List<User> users = new ArrayList<>();

        Connection con=null;
        Statement st=null;
        ResultSet rs=null;

        try {
            String sql = "SELECT u.id, u.login, u.password, r.role FROM users u\n" +
                    "JOIN roles r ON u.role=r.id";

            con = CustomDataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            users = mapUsers(rs);

        } catch (SQLException throwables) {
            log.error(throwables.getStackTrace());
        } finally {
            close(rs);
            close(st);
            close(con);
        }
        return users;
    }




    public User getUser(String login) {
        User user = null;

        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            String sql = "SELECT u.id, u.login, u.password, r.role FROM users u JOIN roles r ON u.role=r.id WHERE u.login = ?";
            con = CustomDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ps.execute();

            rs = ps.executeQuery();
            System.out.println(rs);

            List<User> users = mapUsers(rs);
            if (users.size()!=0) {
                user = users.get(0);
            }
        } catch (SQLException throwables) {
            log.error(throwables.getStackTrace());
        } finally {
            close(rs);
            close(ps);
            close(con);
        }
        return user;
    }


    public void addUser(User user) {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            String sql = "INSERT INTO users (login, password, role) VALUES (?, ?, 2)";
            con = CustomDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.execute();

            //rs = ps.executeQuery();
            log.trace("User " + user.getLogin()+" added to the users table");



        } catch (SQLException throwables) {
            log.error(throwables.getStackTrace()+" Message:"+throwables.getMessage());
        } finally {
            close(rs);
            close(ps);
            close(con);
        }
    }


    public boolean updateUser(String userLogin, Map<String, String> params) {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;



        try {
            String sql="UPDATE users SET login=?, password=?, role=? WHERE login = ?";;
            con = CustomDataSource.getConnection();

            ps = con.prepareStatement(sql);
            ps.setString(1, params.get("login"));
            ps.setString(2, params.get("password"));
            ps.setInt(3, Integer.parseInt(params.get("role")));
            ps.setString(4, userLogin);

            ps.execute();
            log.trace("User " + userLogin+" successfully updated");

        } catch (SQLException throwables) {
            log.error(throwables.getStackTrace()+" Message:"+throwables.getMessage());
            return false;
        } finally {
            close(rs);
            close(ps);
            close(con);
        }
        return true;
    }





    public boolean deleteUser(String login) {
        Connection con=null;
        PreparedStatement ps=null;


        try {
            String sql = "DELETE FROM users WHERE login=?";
            con = CustomDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ps.execute();

        } catch (SQLException throwables) {
            log.error(throwables.getStackTrace()+" Message:"+throwables.getMessage());
            return false;
        } finally {
            close(ps);
            close(con);
        }
        log.trace("User " + login+" was deleted from the users table");
        return true;
    }




    private List<User> mapUsers(ResultSet rs) throws SQLException {
        log.debug("Calling mapUsers in UserDAO");

        List<User> users = new ArrayList<>();

        while (rs.next()) {
            User user = new User();

            user.setId(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));

            users.add(user);
        }
        return users;
    }

    private void close(AutoCloseable ac){
        try {
            if (ac!=null) {
                ac.close();
            }
        } catch (Exception e) {
            log.error(e.getStackTrace());
        }

    }




}
