package championship.manager.util;

import java.util.Comparator;

// TODO: document me!!!

/**
 * GamePositionComparator.
 * <p/>
 * User: rro
 * Date: 31.12.2005
 * Time: 18:04:21
 *
 * @author Roman R&auml;dle
 * @version $Id: GamePositionComparator.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class GamePositionComparator implements Comparator {

    /**
     * @see Comparator#compare(Object, Object)
     */
    public int compare(Object o1, Object o2) {

        if (o1 instanceof GamePosition && o2 instanceof GamePosition) {

            GamePosition gamePosition1 = (GamePosition) o1;
            GamePosition gamePosition2 = (GamePosition) o2;

            if (gamePosition1.getGamePosition() != null && gamePosition2.getGamePosition() != null) {
                return gamePosition1.getGamePosition().compareTo(gamePosition2.getGamePosition());
            }
        }

        return 0;
    }
}
