package championship.manager.util;

import java.util.Vector;
import java.util.List;
import java.util.Collection;

// @todo document me!

/**
 * VectorUtils.
 * <p/>
 * User: rro
 * Date: 31.07.2005
 * Time: 18:20:27
 *
 * @author Roman R&auml;dle
 * @version $Id: VectorUtils.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class VectorUtils {

    public static Vector<Object> collectionToVector(Collection list) {

        Vector<Object> vector = new Vector<Object>();

        for (Object o : list) {
            vector.add(o);
        }

        return vector;
    }
}
