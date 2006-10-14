package championship.manager;

import championship.manager.util.ComponentUtil;
import championship.manager.domain.Championship;
import championship.manager.ui.ObserverFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

    private Championship championship;

    private JTabbedPane sectionPane;

    private ShowGameTablePanel showGameTablePanel;

    private long countingWatchTime = 840000;
    private long countingWatchDecrement = 1000;

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    public MainFrame(Championship championship) throws HeadlessException {

        this.championship = championship;

        this.showGameTablePanel = new ShowGameTablePanel(this);

        initMenuBar();
        initComponents();
        initLayout();

        setTitle((championship != null ? championship.getName() : "<LEER>") + " - Championship Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1024, 768));

        ComponentUtil.centerComponentOnScreen(this);

        setVisible(true);
    }

    private void initMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu program = new JMenu("Programm");

        JMenuItem exit = new JMenuItem("Beenden");
        exit.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
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

        JMenuItem observer = new JMenuItem("Observer Frame");
        observer.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                showGameTablePanel.setObserverFrame(new ObserverFrame(MainFrame.this, countingWatchTime, countingWatchDecrement));
            }
        });

        JMenuItem countingWatchTime = new JMenuItem("Stoppuhr");
        countingWatchTime.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                String timeString = JOptionPane.showInputDialog(MainFrame.this, "Stoppuhr - Zeit in Sekunden", "Stoppuhr", JOptionPane.PLAIN_MESSAGE);

                try {
                    MainFrame.this.countingWatchTime = Long.parseLong(timeString) * 1000;
                    showGameTablePanel.renewCountingWatch();
                }
                catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
            }
        });

        settings.add(setup);
        settings.add(observer);
        settings.add(countingWatchTime);

        menuBar.add(program);
        menuBar.add(settings);

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
