package service;


import service.impl.HotelServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.TourServiceImpl;
import service.impl.UserServiceImpl;

public class ServiceFactory {

    private static ServiceFactory instance;

    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    private ServiceFactory() {
    }

    public UserService getUserService(){
        return new UserServiceImpl();
    }
    public TourService getTourService(){
        return new TourServiceImpl();
    }
    public HotelService getHotelService(){
        return new HotelServiceImpl();
    }
    public OrderService getOrderService(){
        return new OrderServiceImpl();
    }

}
