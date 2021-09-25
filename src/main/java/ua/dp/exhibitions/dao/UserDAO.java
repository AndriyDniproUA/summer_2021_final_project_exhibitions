package ua.dp.exhibitions.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.datasource.CustomDataSource;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;
import ua.dp.exhibitions.utils.DbUtil;
import ua.dp.exhibitions.daoUtil.UserDaoUtil;

import java.sql.*;
import java.util.List;
import java.util.Map;

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

    public List<User> getAllUsers () throws DaoException {
        log.debug("Calling getAllUsers in UserDAO");

        List<User> users;

        Connection con=null;
        Statement st=null;
        ResultSet rs=null;

        try {
            String sql = "SELECT u.id, u.login, u.password, r.role FROM users u\n" +
                    "JOIN roles r ON u.role=r.id";

            con = CustomDataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            users = UserDaoUtil.mapUsers(rs);

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Unable to extract users from UserDAO!",e);

        } finally {
            DbUtil.close(rs);
            DbUtil.close(st);
            DbUtil.close(con);
        }
        return users;
    }


    public User getUser(String login) throws DaoException {
        User user = null;

        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            String sql = "SELECT u.id, u.login, u.password, r.role FROM users u JOIN roles r ON u.role=r.id WHERE u.login = ?";

            con = CustomDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, login);
            rs = ps.executeQuery();

            List<User> users = UserDaoUtil.mapUsers(rs);
            if (users.size()!=0) {
                user = users.get(0);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Unable to extract user: "+login+" from UserDAO!",e);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(ps);
            DbUtil.close(con);
        }
        return user;
    }


    public void addUser(User user) throws DaoException{
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {

            String sql = "INSERT INTO users (login, password, role) VALUES (?, ?, 2)";
            con = CustomDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
            log.trace("User " + user.getLogin()+" added to the users table");

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("User: "+user.getLogin()+" already exists!",e);

        } finally {
            DbUtil.close(rs);
            DbUtil.close(ps);
            DbUtil.close(con);
        }
    }

    public boolean updateUser(String userLogin, Map<String, String> params) throws DaoException{
        Connection con=null;
        PreparedStatement ps=null;

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

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Unable to update user: "+userLogin+" in the database!",e);
//            return false;
        } finally {
            DbUtil.close(ps);
            DbUtil.close(con);
        }
        return true;
    }

    public void deleteUser(String login) throws DaoException {
        Connection con=null;
        PreparedStatement ps=null;

        try {
            String sql = "DELETE FROM users WHERE login=?";
            con = CustomDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ps.execute();

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Unable to delete user: "+login+" in the database!",e);
//            return false;
        } finally {
            DbUtil.close(ps);
            DbUtil.close(con);
        }
        log.trace("User " + login+" was deleted from the users table");
//        return true;
    }
}
