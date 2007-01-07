package org.championship.manager.ui;

/**
 * TODO: document me!!!
 * <p/>
 * <code>Changable</code>.
 * <p/>
 * User: raedler
 * Date: Jan 7, 2007
 * Time: 12:17:07 AM
 *
 * @author <a href="mailto:roman@raedle.info">Roman R&auml;dle</a>
 * @version $Id$
 */
public interface Changable {

    /**
     * Returns true whether something has been changed or
     * false if nothing was changed.
     *
     * @return True whether something has been changed or
     * false if nothing was changed.
     */
    public boolean hasChanged();
}
