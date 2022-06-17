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
    public static final String PAGE_ORDER = "/WEB-INF/views/order.jsp";
    public static final String PAGE_LOGIN_SUCCESS = "/";
    public static final String PAGE_LOGOUT_SUCCESS = "/";
    public static final String PAGE_REGISTER_SUCCESS = "/";

    // commands
//    public static final String COMMAND_LOGIN_SUCCESS = "my-travel?command=home";
//    public static final String COMMAND_LIST_ORDERS = "/controller?command=listOrders";
//    public static final String COMMAND_LIST_MENU = "/controller?command=listMenu";
}
