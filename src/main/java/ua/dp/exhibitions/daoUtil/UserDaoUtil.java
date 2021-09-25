package ua.dp.exhibitions.daoUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.entities.User;

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

            users.add(user);
        }
        return users;
    }
}
