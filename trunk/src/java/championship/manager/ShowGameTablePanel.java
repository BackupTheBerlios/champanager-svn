/*
 * ShowGameTablePanel.java
 *
 * Created on 2. Januar 2006, 17:13
 */

package championship.manager;

import championship.manager.domain.*;
import championship.manager.util.HibernateUtil;
import championship.manager.timer.CountingWatchPanel;
import championship.manager.ui.ObserverFrame;
import org.hibernate.Transaction;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.util.StringTokenizer;
import java.util.prefs.Preferences;

/**
 * @author Roman Georg Rädle
 */
public class ShowGameTablePanel extends javax.swing.JPanel {

    private MainFrame mainFrame;

    private Preferences prefs = Preferences.userRoot().node("Championship Manager");

    private Color defaultColor = this.getBackground();
    private Color color = defaultColor;

    private int gameCount = 0;
    private int currentGame = 1;

    private ObserverFrame observerFrame;

    private javax.swing.JLabel awayteamL;
    private javax.swing.JButton awayteamMB;
    private javax.swing.JButton awayteamPB;
    private javax.swing.JLabel currentAwayteamL;
    //private javax.swing.JLabel currentGameL;
    private CountingWatchPanel countingWatchP;
    private javax.swing.JLabel currentHometeamL;
    private javax.swing.JLabel gameL;
    private javax.swing.JLabel hometeamL;
    private javax.swing.JButton hometeamMB;
    private javax.swing.JButton hometeamPB;
    private javax.swing.JButton lastB;
    private javax.swing.JButton nextB;
    private championship.manager.ShowTablePanel showTableP;
    private javax.swing.JLabel sizeL;
    private javax.swing.JButton sizeMB;
    private javax.swing.JButton sizePB;
    private javax.swing.JLabel countingWatchL;
    private javax.swing.JButton startCountingWatchB;
    private javax.swing.JButton stopCountingWatchB;
    private javax.swing.JButton resetCountingWatchB;

    /**
     * @param mainFrame
     */
    public ShowGameTablePanel(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        Championship championship = mainFrame.getChampionship();
        gameCount = championship.getAllGamesCount();

        currentGame = prefs.getInt("currentGame", 1);

        initComponents();
        initLayout();
        initListener();
        initValues();
    }

    /**
     *
     */
    private void initComponents() {

        countingWatchP = new CountingWatchPanel(mainFrame.getCountingWatchTime(), mainFrame.getCountingWatchDecrement());

        showTableP = new championship.manager.ShowTablePanel(mainFrame);
        lastB = new javax.swing.JButton();
        nextB = new javax.swing.JButton();
        hometeamL = new javax.swing.JLabel();
        hometeamPB = new javax.swing.JButton();
        hometeamMB = new javax.swing.JButton();
        awayteamL = new javax.swing.JLabel();
        awayteamPB = new javax.swing.JButton();
        awayteamMB = new javax.swing.JButton();
        gameL = new javax.swing.JLabel();
        //currentGameL = new javax.swing.JLabel();
        currentHometeamL = new javax.swing.JLabel();
        currentAwayteamL = new javax.swing.JLabel();
        sizeL = new javax.swing.JLabel();
        sizePB = new javax.swing.JButton();
        sizeMB = new javax.swing.JButton();
        countingWatchL = new javax.swing.JLabel();
        startCountingWatchB = new javax.swing.JButton();
        stopCountingWatchB = new javax.swing.JButton();
        resetCountingWatchB = new javax.swing.JButton();

        //currentGameL.setFont(new java.awt.Font("Tahoma", Font.BOLD, 36));

        lastB.setIcon(new ImageIcon("./images/arrow_left.png"));
        nextB.setIcon(new ImageIcon("./images/arrow_right.png"));

        hometeamPB.setIcon(new ImageIcon("./images/plus.png"));
        hometeamMB.setIcon(new ImageIcon("./images/minus.png"));

        awayteamPB.setIcon(new ImageIcon("./images/plus.png"));
        awayteamMB.setIcon(new ImageIcon("./images/minus.png"));

        sizeL.setText("Schriftgr\u00f6\u00dfe");
        sizePB.setIcon(new ImageIcon("./images/plus.png"));
        sizeMB.setIcon(new ImageIcon("./images/minus.png"));

        gameL.setText("Spiel");

        currentHometeamL.setFont(new java.awt.Font("Tahoma", 1, 35));
        currentAwayteamL.setFont(new java.awt.Font("Tahoma", 1, 35));

        countingWatchL.setText("Stoppuhr");
        startCountingWatchB.setIcon(new ImageIcon("./images/clock_run.png"));
        stopCountingWatchB.setIcon(new ImageIcon("./images/clock_stop.png"));
        resetCountingWatchB.setIcon(new ImageIcon("./images/clock_refresh.png"));
    }

    /**
     *
     */
    private void initLayout() {
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(showTableP, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                                .add(currentAwayteamL, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                                .add(currentHometeamL, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                        .add(org.jdesktop.layout.GroupLayout.TRAILING, lastB, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(org.jdesktop.layout.GroupLayout.TRAILING, nextB, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(gameL, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .add(45, 45, 45)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(hometeamPB)
                                        .add(hometeamL)
                                        .add(hometeamMB))
                                .add(56, 56, 56)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(awayteamMB)
                                        .add(awayteamPB)
                                        .add(awayteamL))
                                .add(58, 58, 58)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(sizeL)
                                        .add(sizePB)
                                        .add(sizeMB))
                                .add(73, 73, 73)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(countingWatchL)
                                        .add(startCountingWatchB)
                                        .add(stopCountingWatchB)
                                        .add(resetCountingWatchB))
                                .add(41, 41, 41)
                                .add(countingWatchP)))
                        .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[]{sizeMB, sizePB}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.linkSize(new java.awt.Component[]{awayteamMB,
                                                 awayteamPB,
                                                 hometeamMB,
                                                 hometeamPB}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.linkSize(new java.awt.Component[]{resetCountingWatchB,
                                                 startCountingWatchB,
                                                 stopCountingWatchB}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                                .add(hometeamL)
                                                                .add(gameL))
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                                                .add(nextB)
                                                                .add(hometeamPB))
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                                        .add(lastB)
                                                        .add(hometeamMB)))
                                                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                                        .add(awayteamL)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(awayteamPB)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(awayteamMB))
                                                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                                        .add(sizeL)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(sizePB)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(sizeMB))
                                                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                                .add(countingWatchL)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(startCountingWatchB)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(stopCountingWatchB)))
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(resetCountingWatchB))
                                .add(countingWatchP))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(currentHometeamL, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(currentAwayteamL, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(showTableP, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                        .addContainerGap())
        );
    }

    private void initListener() {

        startCountingWatchB.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                countingWatchP.startWatch(true);
                if (observerFrame != null) observerFrame.startCountingWatch(true);
            }
        });

        stopCountingWatchB.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                countingWatchP.stopWatch();
                if (observerFrame != null) observerFrame.stopCountingWatch();
            }
        });

        resetCountingWatchB.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                countingWatchP.resetWatch();
                if (observerFrame != null) observerFrame.resetCountingWatch();
            }
        });

        gameL.addMouseListener(new MouseAdapter() {

            /**
             * @see MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
             */
            public void mouseClicked(MouseEvent e) {

                if (color.equals(Color.WHITE)) {
                    color = Color.YELLOW;
                    ShowGameTablePanel.this.setBackground(color);
                    showTableP.setTablePanelColor(color);
                    countingWatchP.setBackground(color);

                    if (observerFrame != null) observerFrame.setObserverColor(color);

                }
                else if (color.equals(Color.YELLOW)) {
                    color = defaultColor;
                    ShowGameTablePanel.this.setBackground(color);
                    showTableP.setTablePanelColor(color);
                    countingWatchP.setBackground(color);

                    if (observerFrame != null) observerFrame.setObserverColor(color);
                }
                else {
                    color = Color.WHITE;
                    ShowGameTablePanel.this.setBackground(color);
                    showTableP.setTablePanelColor(color);
                    countingWatchP.setBackground(color);

                    if (observerFrame != null) observerFrame.setObserverColor(color);
                }
            }
        });

        nextB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                Championship championship = mainFrame.getChampionship();
                gameCount = championship.getAllGamesCount();

                if (gameCount >= currentGame + 1) {

                    currentGame++;
                    prefs.putInt("currentGame", currentGame);
                    initValues();
                }
            }
        });

        lastB.addActionListener(new ActionListener() {
            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                if (currentGame - 1 > 0) {

                    Championship championship = mainFrame.getChampionship();
                    gameCount = championship.getAllGamesCount();

                    currentGame--;

                    prefs.putInt("currentGame", currentGame);

                    initValues();
                }
            }
        });

        sizePB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                if (observerFrame != null) observerFrame.setGameLabellingSize(1);
            }
        });

        sizeMB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                if (observerFrame != null) observerFrame.setGameLabellingSize(-1);
            }
        });

        hometeamPB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                Championship championship = mainFrame.getChampionship();
                Game game = championship.getGame(currentGame);

                String oldResult = game.getResult();
                String result = "0:0";

                if (oldResult != null && !"".equals(oldResult)) {
                    result = oldResult;
                }
                else {
                    oldResult = null;
                }

                if (oldResult != null) {
                    StringTokenizer tokenizer = new StringTokenizer(result, ":");

                    int home = Integer.parseInt(tokenizer.nextToken());
                    int away = Integer.parseInt(tokenizer.nextToken());

                    home++;

                    result = home + ":" + away;
                }

                game.setResult(result);

                Group group = championship.getGroup(game.getGroupName());

                if (group != null) {
                    Table table = group.getGroupTable();

                    if (table == null) {
                        table = new Table();
                        group.setGroupTable(table);
                        table.setGroup(group);
                    }

                    Team hometeam = game.getHometeam();
                    Team awayteam = game.getAwayteam();

                    table.addResult(hometeam, oldResult, result, true);
                    table.addResult(awayteam, oldResult, result, false);

                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction trx = session.beginTransaction();
                    session.saveOrUpdate(group);
                    trx.commit();
                    session.close();
                }
                else {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction trx = session.beginTransaction();
                    session.saveOrUpdate(game);
                    trx.commit();
                    session.close();
                }

                initValues();
            }
        });

        hometeamMB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                Championship championship = mainFrame.getChampionship();
                Game game = championship.getGame(currentGame);

                String oldResult = game.getResult();
                String result = "0:0";

                if (oldResult != null && !"".equals(oldResult)) {
                    result = oldResult;
                }
                else {
                    oldResult = null;
                }

                if (oldResult != null) {
                    StringTokenizer tokenizer = new StringTokenizer(result, ":");

                    int home = Integer.parseInt(tokenizer.nextToken());
                    int away = Integer.parseInt(tokenizer.nextToken());

                    home--;

                    result = home + ":" + away;
                }

                game.setResult(result);

                Group group = championship.getGroup(game.getGroupName());

                if (group != null) {
                    Table table = group.getGroupTable();

                    if (table == null) {
                        table = new Table();
                        group.setGroupTable(table);
                        table.setGroup(group);
                    }

                    Team hometeam = game.getHometeam();
                    Team awayteam = game.getAwayteam();

                    table.addResult(hometeam, oldResult, result, true);
                    table.addResult(awayteam, oldResult, result, false);

                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction trx = session.beginTransaction();
                    session.saveOrUpdate(group);
                    trx.commit();
                    session.close();
                }
                else {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction trx = session.beginTransaction();
                    session.saveOrUpdate(game);
                    trx.commit();
                    session.close();
                }

                initValues();
            }
        });

        awayteamPB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                Championship championship = mainFrame.getChampionship();
                Game game = championship.getGame(currentGame);

                String oldResult = game.getResult();
                String result = "0:0";

                if (oldResult != null && !"".equals(oldResult)) {
                    result = oldResult;
                }
                else {
                    oldResult = null;
                }

                if (oldResult != null) {
                    StringTokenizer tokenizer = new StringTokenizer(result, ":");

                    int home = Integer.parseInt(tokenizer.nextToken());
                    int away = Integer.parseInt(tokenizer.nextToken());

                    away++;

                    result = home + ":" + away;
                }

                game.setResult(result);

                Group group = championship.getGroup(game.getGroupName());

                if (group != null) {
                    Table table = group.getGroupTable();

                    if (table == null) {
                        table = new Table();
                        group.setGroupTable(table);
                        table.setGroup(group);
                    }

                    Team hometeam = game.getHometeam();
                    Team awayteam = game.getAwayteam();

                    table.addResult(hometeam, oldResult, result, true);
                    table.addResult(awayteam, oldResult, result, false);

                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction trx = session.beginTransaction();
                    session.saveOrUpdate(group);
                    trx.commit();
                    session.close();
                }
                else {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction trx = session.beginTransaction();
                    session.saveOrUpdate(game);
                    trx.commit();
                    session.close();
                }

                initValues();
            }
        });

        awayteamMB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                Championship championship = mainFrame.getChampionship();
                Game game = championship.getGame(currentGame);

                String oldResult = game.getResult();
                String result = "0:0";

                if (oldResult != null && !"".equals(oldResult)) {
                    result = oldResult;
                }
                else {
                    oldResult = null;
                }

                if (oldResult != null) {
                    StringTokenizer tokenizer = new StringTokenizer(result, ":");

                    int home = Integer.parseInt(tokenizer.nextToken());
                    int away = Integer.parseInt(tokenizer.nextToken());

                    away--;

                    result = home + ":" + away;
                }

                game.setResult(result);

                Group group = championship.getGroup(game.getGroupName());

                if (group != null) {
                    Table table = group.getGroupTable();

                    if (table == null) {
                        table = new Table();
                        group.setGroupTable(table);
                        table.setGroup(group);
                    }

                    Team hometeam = game.getHometeam();
                    Team awayteam = game.getAwayteam();

                    table.addResult(hometeam, oldResult, result, true);
                    table.addResult(awayteam, oldResult, result, false);

                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction trx = session.beginTransaction();
                    session.saveOrUpdate(group);
                    trx.commit();
                    session.close();
                }
                else {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction trx = session.beginTransaction();
                    session.saveOrUpdate(game);
                    trx.commit();
                    session.close();
                }

                initValues();
            }
        });
    }

    /**
     *
     */
    private void initValues() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        Game game = (Game) session.createQuery("from " + Game.class.getName() + " as game where game.gamePosition = " + currentGame).uniqueResult();
        trx.commit();
        session.close();

        if (observerFrame != null) observerFrame.repaintObserver(game);

        if (game != null) {

            StringTokenizer resultTokenizer = new StringTokenizer(game.getResult() != null ? game.getResult() : "-:-", ":");

            gameL.setText("Spiel-Nr. " + game.getGamePosition());
            currentHometeamL.setText(resultTokenizer.nextToken() + "    " + game.getHometeam().getName());
            currentAwayteamL.setText(resultTokenizer.nextToken() + "    " + game.getAwayteam().getName());

            hometeamL.setText(game.getHometeam().getName());
            awayteamL.setText(game.getAwayteam().getName());

            showTableP.setGroupName(game.getGroupName());
        }
    }

    public void setObserverFrame(ObserverFrame observerFrame) {
        this.observerFrame = observerFrame;

        initValues();
    }

    public void renewCountingWatch() {
        countingWatchP.setTime(mainFrame.getCountingWatchTime());
        countingWatchP.repaint();

        if (observerFrame != null) {
            observerFrame.renewCountingWatch();
        }
    }
}
