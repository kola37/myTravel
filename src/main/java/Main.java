import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DBManager;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {

        Connection con = DBManager.getInstance().getConnection();
//        Connection con = DBUtils.getInstance().getConnection();
//        System.out.println(UserRole.getRole(1).getName());

//        UserDAO userDAO = DAOFactory.getInstance().getUserDaoImpl();
//        Connection con = DAOFactory.getInstance().getConnection();


//        Connection con2  = DAOFactory().getInstance().getConnection();
//        try {
//           Optional <User> opUser = userDAO.findById(con, 1);
//           User user = opUser.get();
//            System.out.println(user);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }

//        int i =0;
//        while(i++ < 10) {
            LOG.info("This is INFO log");
            LOG.debug("This is DEBUG log");
            LOG.warn("This is WARN log");
            LOG.error("This is ERROR log");
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
