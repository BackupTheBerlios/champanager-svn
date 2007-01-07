/*
 * Copyright 2005-2006 Championship Manager development team.
 *
 * This file is part of Championship Manager.
 *
 * Championship Manager is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * Please see COPYING for the complete licence.
 */
package org.championship.manager.ui;

import org.championship.manager.util.ComponentUtilities;
import org.championship.manager.images.Images;

import javax.swing.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * <code>AboutPanel</code>.
 * <p/>
 * User: raedler
 * Date: 17.10.2006
 * Time: 19:34:02
 *
 * @author Roman R&auml;dle
 * @version $Id$
 * @since Championship Manager 0.1
 */
public class AboutPanel extends JDialog {

    public AboutPanel(Frame owner) throws HeadlessException {
        super(owner, "Über", true);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("About", new JLabel(new ImageIcon(Images.class.getResource("championship_png24.png"))));
        tabbedPane.addTab("Detail", new ApplicationData());

        add(tabbedPane);

        pack();

        ComponentUtilities.centerComponentOnScreen(this);

        setVisible(true);
    }
}
