package dao;

import dao.impl.*;

public class DaoFactory {

    private static DaoFactory instance;

//    private final UserDao userDao = new UserDaoImpl();
//    private final Dao<Tour> tourDao = new TourDaoImpl();
//    private final Dao<Hotel> hotelDao = new HotelDaoImpl();
//    private final OrderDao orderDao = new OrderDaoImpl();

    private DaoFactory() {
    }

    public static synchronized DaoFactory getInstance(){
        if(instance == null){
            instance = new DaoFactory();
        }
        return instance;
    }

    public UserDao getUserDao(){
        return new UserDaoImpl();
    }

    public TourDao getTourDao(){
        return new TourDaoImpl();
    }

    public HotelDao getHotelDao(){
        return new HotelDaoImpl();
    }

    public OrderDao getOrderDao(){
        return new OrderDaoImpl();
    }
}
