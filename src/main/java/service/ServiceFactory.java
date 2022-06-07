package service;


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

    public UserService getUserServiceImpl(){
        return new UserServiceImpl();
    }

}
