package com.mytravel.filter;

import com.mytravel.controller.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Language filter class implements Filter interface and do filter language changes
 *
 * @author Anatolii Koliaka
 */
public class LanguageFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(LanguageFilter.class);
    private static final String PARAM_CURRENT_LOCALE = "currentLocale";
    private static final String ATTR_CURRENT_LOCALE = "currentLocale";
    private static final String ATTR_LOCALE = "locale";

    private String language;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.debug("Filter starts");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String currLoc = req.getParameter(PARAM_CURRENT_LOCALE);

        if (currLoc != null && !currLoc.isEmpty()) {
            LOG.trace("Request language = " + currLoc + ", set language to " + currLoc);
            req.getSession().setAttribute(ATTR_LOCALE, currLoc);
            req.getSession().setAttribute(ATTR_CURRENT_LOCALE, currLoc);
            resp.sendRedirect(req.getContextPath() + PagePath.PAGE_SUCCESS);
            LOG.debug("Filter finished");
        } else {
            LOG.debug("Filter finished");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
        LOG.debug("Filter initialization starts");
        language = fConfig.getInitParameter("language");
        LOG.trace("Language from web.xml --> " + language);
        LOG.debug("Filter initialization finished");
    }
}
