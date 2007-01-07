package org.championship.manager.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;

// TODO: document me!!!

/**
 * HibernateUtil.
 * <p/>
 * User: rro
 * Date: 27.12.2005
 * Time: 08:52:12
 *
 * @author Roman R&auml;dle
 * @version $Id: HibernateUtil.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class HibernateUtil {

    private static final Configuration cfg;

    private static final SessionFactory sessionFactory;

    static {
        try {
            cfg = new Configuration().configure();

            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = cfg.buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {

        String driverClass = cfg.getProperty("hibernate.connection.driver_class");

        if (driverClass != null && driverClass.contains("org.hsqldb")) {
            Session session = sessionFactory.openSession();
            Connection connection = session.connection();
            try {
                connection.createStatement().execute("shutdown");
                connection.commit();
                connection.close();
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            session.close();
        }
    }
}
