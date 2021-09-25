package ua.dp.exhibitions.daoUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.datasource.CustomDataSource;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;
import ua.dp.exhibitions.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoUtil {
    private static final Logger log = LogManager.getLogger(UserDaoUtil.class);

    public static List<User> mapUsers(ResultSet rs) throws SQLException {
        log.debug("Calling mapUsers in UserDaoUtil");

        List<User> users = new ArrayList<>();

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setBalance(rs.getDouble("balance"));

            users.add(user);
        }
        return users;
    }

    public static int getRoleIdByRoleName(String roleName) throws DaoException {
        Integer roleId=null;

        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            String sql = "SELECT id FROM roles WHERE role = ?";

            con = CustomDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, roleName);
            rs = ps.executeQuery();

            if (rs.next()) {
                roleId = rs.getInt("id");
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("Unable to extract role: "+roleName+" by UserDAO!",e);
        } finally {
            DbUtil.close(rs);
            DbUtil.close(ps);
            DbUtil.close(con);
        }
        return roleId;
    }


}
