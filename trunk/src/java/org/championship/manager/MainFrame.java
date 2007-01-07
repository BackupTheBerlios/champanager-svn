package org.championship.manager;

import org.championship.manager.util.ComponentUtilities;
import org.championship.manager.util.HibernateUtil;
import org.championship.manager.domain.Championship;
import org.championship.manager.ui.ObserverFrame;
import org.championship.manager.ui.AboutPanel;
import org.championship.manager.images.png16x16.Icons16;
import org.championship.manager.license.LicenseDialog;
import org.championship.manager.logic.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

// TODO: document me!!!

/**
 * MainFrame.
 * <p/>
 * User: rro
 * Date: 30.12.2005
 * Time: 22:48:14
 *
 * @author Roman R&auml;dle
 * @version $Id: MainFrame.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class MainFrame extends JFrame {

    private Configuration cfg;

    private Championship championship;

    private JTabbedPane sectionPane;

    private ShowGameTablePanel showGameTablePanel;

    private long countingWatchTime = 840000;
    private long countingWatchDecrement = 1000;

    private JCheckBoxMenuItem observer;

    public void setObserverCheckBox(boolean selected) {
        observer.setSelected(selected);
    }

    // The observer frame to get a view only for observers.
    private ObserverFrame observerFrame = new ObserverFrame(MainFrame.this);

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    public MainFrame(Championship championship) throws HeadlessException {
        super((championship != null ? championship.getName() : "<LEER>") + " - " + ApplicationInfo.getApplicationName() + " " + ApplicationInfo.getVersion() + " (#build: " + ApplicationInfo.getBuildtime() + ")");
        setIconImage(Toolkit.getDefaultToolkit().getImage(Icons16.class.getResource("environment.png")));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setSize(new Dimension(800, 600));

        // Loads the configuration out of a properties file.
        cfg = loadConfiguration();

        this.championship = championship;

        this.showGameTablePanel = new ShowGameTablePanel(this);
        showGameTablePanel.setObserverFrame(observerFrame);

        initMenuBar();
        initComponents();
        initLayout();

        WindowListener wl = new WindowAdapter() {

            /**
             * {@inheritDoc}
             */
            public void windowClosing(WindowEvent e) {
                HibernateUtil.shutdown();
            }
        };
        addWindowListener(wl);
        observerFrame.addWindowListener(wl);

        ComponentUtilities.centerComponentOnScreen(this);

        setVisible(true);
    }

    /**
     * Loads the championship manager configuration out of
     * a properties file and wraps this file within a class
     * called <code>Configuration</code> that provides some
     * useful methods.
     *
     * @return The <code>Configuration</code> that wraps a
     *         properties file.
     */
    private Configuration loadConfiguration() {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("org/championship/manager/configuration.properties");

        Properties cfg = new Properties();

        try {
            cfg.load(inputStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return new Configuration(cfg);
    }

    /**
     * Returns the <code>Configuration</code> that contains
     * informations about the championship manager configuration.
     *
     * @return The championship manager configuration.
     */
    public Configuration getConfiguration() {
        return cfg;
    }

    private void initMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu program = new JMenu("Programm");

        JMenuItem newChampionship = new JMenuItem("Neu");
        newChampionship.setAccelerator(KeyStroke.getKeyStroke('N', Event.CTRL_MASK));
        newChampionship.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                championship = new Championship();
                new SetupDialog(MainFrame.this, "Neuer Championship", true);
            }
        });
        program.add(newChampionship);
        program.addSeparator();

        JMenuItem exit = new JMenuItem("Beenden");
        exit.setAccelerator(KeyStroke.getKeyStroke('Q', Event.ALT_MASK));
        exit.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                HibernateUtil.shutdown();
                MainFrame.this.dispose();
                System.exit(0);
            }
        });

        program.add(exit);

        JMenu settings = new JMenu("Einstellungen");

        JMenuItem setup = new JMenuItem("Championship Setup");
        setup.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new SetupDialog(MainFrame.this, "Konfiguration des Turniers", true);
            }
        });
        settings.add(setup);

        observer = new JCheckBoxMenuItem("Observer Frame");
        observer.setAccelerator(KeyStroke.getKeyStroke('O', Event.ALT_MASK));
        observer.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                if (observer.isSelected()) {
                    observerFrame.setVisible(true);
                    MainFrame.this.requestFocus();
                } else if (observerFrame != null) {
                    observerFrame.setVisible(false);
                    MainFrame.this.requestFocus();
                }
            }
        });
        settings.add(observer);

        JMenuItem countingWatchTime = new JMenuItem("Stoppuhr");
        countingWatchTime.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                String timeString = JOptionPane.showInputDialog(MainFrame.this, "Stoppuhr - Zeit in Sekunden", "Stoppuhr", JOptionPane.PLAIN_MESSAGE);

                if (timeString != null) {
                    try {
                        MainFrame.this.countingWatchTime = Long.parseLong(timeString) * 1000;
                        showGameTablePanel.renewCountingWatch();
                    }
                    catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }
            }
        });
        settings.add(countingWatchTime);

        JMenu questionMark = new JMenu("?");

        JMenuItem license = new JMenuItem("Lizenz");
        license.addActionListener(new ActionListener() {

            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                new LicenseDialog(MainFrame.this);
            }
        });

        JMenuItem about = new JMenuItem("Über");
        about.addActionListener(new ActionListener() {

            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                new AboutPanel(MainFrame.this);
            }
        });

        questionMark.add(license);
        questionMark.addSeparator();
        questionMark.add(about);

        menuBar.add(program);
        menuBar.add(settings);
        menuBar.add(questionMark);

        setJMenuBar(menuBar);
    }

    private void initComponents() {
        sectionPane = new JTabbedPane();
    }

    private void initLayout() {
        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(sectionPane, BorderLayout.CENTER);

        sectionPane.addTab("Vorrunde", new GamesPanel(this, "preliminary_round"));
        sectionPane.addTab("Zwischenrunde", new GamesPanel(this, "intermediate_stage"));
        sectionPane.addTab("Viertelfinale", new GamesPanel(this, "quarter_final"));
        sectionPane.addTab("Halbfinale", new GamesPanel(this, "semi_final"));
        sectionPane.addTab("Spiel um Platz 3", new GamesPanel(this, "third_place_game"));
        sectionPane.addTab("Finale", new GamesPanel(this, "final"));
        sectionPane.addTab("Aktuelles Spiel", showGameTablePanel);
    }

    public long getCountingWatchTime() {
        return countingWatchTime;
    }

    public void setCountingWatchTime(long countingWatchTime) {
        this.countingWatchTime = countingWatchTime;
    }

    public long getCountingWatchDecrement() {
        return countingWatchDecrement;
    }

    public void setCountingWatchDecrement(long countingWatchDecrement) {
        this.countingWatchDecrement = countingWatchDecrement;
    }
}
