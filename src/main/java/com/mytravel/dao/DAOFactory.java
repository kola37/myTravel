package com.mytravel.dao;

import com.mytravel.dao.impl.HotelDAOImpl;
import com.mytravel.dao.impl.OrderDAOImpl;
import com.mytravel.dao.impl.TourDAOImpl;
import com.mytravel.dao.impl.UserDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DAOFactory {

    private static final Logger LOG = LogManager.getLogger(DAOFactory.class);

    private static DAOFactory instance;
//    private DataSource ds;

//    private final UserDao userDao = new UserDaoImpl();
//    private final Dao<Tour> tourDao = new TourDaoImpl();
//    private final Dao<Hotel> hotelDao = new HotelDaoImpl();
//    private final OrderDao orderDao = new OrderDaoImpl();


    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }


    private DAOFactory() {
//        try {
//            Context initContext = new InitialContext();
//            Context envContext = (Context) initContext.lookup("java:/comp/env");
//            ds = (DataSource) envContext.lookup("jdbc/TravelAgencyDB");
//        } catch (NamingException ex) {
//            throw new IllegalStateException("Cannot obtain a data source", ex);
//        }
    }


//    public Connection getConnection() {
//        Connection con = null;
//        try {
//            con = ds.getConnection();
//        } catch (SQLException ex) {
//            throw new IllegalStateException("Cannot obtain a connection", ex);
//        }
//        return con;
//    }


    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    public TourDAO getTourDAO() {
        return new TourDAOImpl();
    }

    public HotelDAO getHotelDAO() {
        return new HotelDAOImpl();
    }

    public OrderDAO getOrderDAO() {
        return new OrderDAOImpl();
    }
}
