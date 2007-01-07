package org.championship.manager.util;

import java.awt.*;

// TODO: document me!!!

/**
 * ComponentUtilities.
 * <p/>
 * User: rro
 * Date: 30.12.2005
 * Time: 22:53:05
 *
 * @author Roman R&auml;dle
 * @version $Id: ComponentUtilities.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class ComponentUtilities {

    /**
     * TODO: document me!!!
     *
     * @param c
     */
    public static void centerComponentOnScreen(Component c) {

        // Gr��e der eingestellten Bildschirmaufl�sung.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        // Gr��e des <code>JFrame</code>.
        Dimension size = c.getSize();

        width -= size.getWidth();
        height -= size.getHeight();

        c.setLocation((int) width / 2, (int) height / 2);
    }
}
