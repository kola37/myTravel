package com.mytravel.service;


import com.mytravel.service.impl.TourServiceImpl;
import com.mytravel.service.impl.UserServiceImpl;
import com.mytravel.service.impl.HotelServiceImpl;
import com.mytravel.service.impl.OrderServiceImpl;

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
