import dao.Dao;
import dao.DaoFactory;
import dao.UserDao;
import dao.HotelDao;
import dao.impl.UserDaoImpl;
import entity.User;
import exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class Main {
private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {

        Connection con = DBManager.getInstance().getConnection();
        System.out.println(con);

//        int i =0;
//        while(i++ < 10) {
//            LOG.info("This is INFO log");
//            LOG.debug("This is DEBUG log");
//            LOG.warn("This is WARN log");
//            LOG.error("This is ERROR log");
//        }
//        UserDao userDao = DaoFactory.getInstance().getUserDao();
////        Connection con = DriverManager.getConnection("");
//        try {
//            userDao.insert(con, new User());
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//        Optional<User> u = null;
//        try {
//            u = user.findByIdNotCon(0);
//        } catch (DaoException e) {
//            LOG.error("This is ERROR log");
//            e.printStackTrace();
//        }

//        HotelDao hotel = DaoFactory.getInstance().getHotelDao();

//        System.out.println(user);
//        System.out.println(u);
//        System.out.println(hotel);
    }
}
