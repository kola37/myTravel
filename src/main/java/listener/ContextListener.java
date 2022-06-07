package listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DBUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String path = ctx.getRealPath("/WEB-INF/logs/log4j2.log");
        System.setProperty("logFile", path);

        final Logger LOG = LogManager.getLogger(ContextListener.class);
        LOG.debug("path = " + path);

        try {
            Connection con = DBUtils.getInstance().getConnection();
            con.close();
        } catch (SQLException ex) {
            LOG.error("Cannot obtain a connection from DB!", ex);
            throw new IllegalStateException("Cannot obtain a connection from DB!", ex);
        }
    }
}
