package org.championship.manager.logic;

import java.util.Properties;

/**
 * The <code>Configuration</code> class contains all information that
 * will be needed by the championship manager to compute game stages
 * and further informations.
 * <p/>
 * User: raedler
 * Date: Jan 6, 2007
 * Time: 11:13:15 PM
 *
 * @author <a href="mailto:roman@raedle.info">Roman R&auml;dle</a>
 * @version $Id$
 */
public class Configuration {

    // The properties contains the all the informations.
    private Properties props;

    /**
     * Create a new <code>Configuration</code> and use
     * a <code>Properties</code> that will be wrapped by
     * this class to provide methods that will be used by
     * the championship manager.
     *
     * @param props The properties file that will be wrapped.
     */
    public Configuration(Properties props) {
        this.props = props;
    }

    /**
     * Returns the class name of the class that implements the
     * <code>GameStageComputable</code> interface and will be
     * used to compute the game stages.
     *
     * @return The class name of the class that implements the
     *         <code>GameStageComputable</code> interface.
     */
    public String getGameStageComputableClassName() {
        return props.getProperty("IGameStageComputable_Class");
    }
}
