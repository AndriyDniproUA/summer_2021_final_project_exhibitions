package ua.dp.exhibitions.web.cabinet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dp.exhibitions.dao.UserDAO;
import ua.dp.exhibitions.daoUtil.UserDaoUtil;
import ua.dp.exhibitions.entities.User;
import ua.dp.exhibitions.exceptions.DaoException;
import ua.dp.exhibitions.utils.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * PersonalCabinetUpdateUserServlet handles personal cabinet update page
 */
public class PersonalCabinetUpdateUserServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(PersonalCabinetUpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.trace("In PersonalCabinetUpdateUserServlet before redirect to personal_cabinet_update_user.jsp");
        request.getRequestDispatcher("jsp/cabinet/personal_cabinet_update_user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
