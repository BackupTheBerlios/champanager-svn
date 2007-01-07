package org.championship.manager.logic;

import org.championship.manager.domain.Championship;

/**
 * TODO: document me!!!
 * <p/>
 * <code>IGameStageComputable</code>.
 * <p/>
 * User: raedler
 * Date: Jan 6, 2007
 * Time: 10:43:14 PM
 *
 * @author <a href="mailto:roman@raedle.info">Roman R&auml;dle</a>
 * @version $Id$
 */
public interface IGameStageComputable {

    /**
     * Calculates the intermediate stages for the <code>Championship</code>
     * and adds all intermediate games to the championship. After that work
     * is done the championship will be returned.
     *
     * @param championship The <code>Championship</code> contains the information
     *                     about the intermediate stage linking and gets the
     *                     result of the computation.
     * @return The <code>Championship</code> that contains the calculated
     *         intermediate stage games.
     */
    public Championship computeIntermediateStages(Championship championship);
}
