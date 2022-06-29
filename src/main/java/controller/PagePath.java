package controller;

import controller.command.CommandResult;

/**
 * Page's path holder (jsp pages, controller commands).
 *
 * @author Anatolii Koliaka
 *
 */
public class PagePath {
    private PagePath() {
    }

    public static final String PAGE_ERROR = "/WEB-INF/views/error.jsp";
    public static final String PAGE_HOME = "/WEB-INF/views/home.jsp";
    public static final String PAGE_LOGIN = "/WEB-INF/views/loginRegister.jsp";
    public static final String PAGE_TOUR = "/WEB-INF/views/tour.jsp";
    public static final String PAGE_ORDER = "/WEB-INF/views/order.jsp";
    public static final String PAGE_USER_CABINET = "/WEB-INF/views/userCabinet.jsp";
    public static final String PAGE_ADMIN_ORDER_EDITOR = "/WEB-INF/views/orderEditor.jsp";
    public static final String PAGE_ADMIN_TOUR_EDITOR = "/WEB-INF/views/tourEditor.jsp";
    public static final String PAGE_ADMIN_USER_EDITOR = "/WEB-INF/views/userEditor.jsp";
    public static final String PAGE_ADD_TOUR = "/WEB-INF/views/addTour.jsp";
    public static final String PAGE_LOGIN_SUCCESS = "/";
    public static final String PAGE_LOGOUT_SUCCESS = "/";
    public static final String PAGE_REGISTER_SUCCESS = "/";
    public static final String PAGE_ORDER_CONFIRMED = "/";
    public static final String PAGE_SUCCESS = "/";

}
