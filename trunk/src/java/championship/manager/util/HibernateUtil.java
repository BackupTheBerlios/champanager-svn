package championship.manager.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

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

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from xmlcrusherdb.cfg.xml
            sessionFactory = new Configuration().configure("sve-osc.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
