import dao.DAOFactory;
import dao.UserDAO;
import entity.User;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.impl.UserServiceImpl;
import util.DBUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/test2")
public class Main2 extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(Main2.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection con = DBUtils.getInstance().getConnection();

        UserDAO userDao = DAOFactory.getInstance().getUserDAO();
        User user = null;
        try {
            Optional<User> opUser = userDao.findById(con, 1);
            user = opUser.get();
            LOG.trace("TRACE:User is " + user);
            LOG.debug("DEBUG:User is " + user);
            LOG.info("INFO:User is " + user);
            System.out.println(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        resp.getWriter().println(user);
        req.getSession().setAttribute("errorMessage", "THIS IS MY ERROR MESSAGE inside session! HUUUURRRRAAA!!!");
        req.setAttribute("errorMessage", "THIS IS MY ERROR MESSAGE! HUUUURRRRAAA!!!");
        req.setAttribute("message", "THIS IS PROSTO MESSAGE!");
        LOG.info(req.getAttribute("errorMessage"));

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp");

        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute("errorMessage", "THIS IS MY ERROR MESSAGE! HUUUURRRRAAA!!!");
//        LOG.info(req.getAttribute("errorMessage"));

//        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/error123.jsp");
//
//        dispatcher.forward(req, resp);
//        doGet(req, resp);

    }
}
