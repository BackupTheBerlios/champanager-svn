package championship.manager;

import championship.manager.domain.*;
import championship.manager.util.HibernateUtil;
import championship.manager.util.VectorUtils;
import championship.manager.util.ValueObjectItem;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

// TODO: document me!!!

/**
 * GamesPanel.
 * <p/>
 * User: rro
 * Date: 30.12.2005
 * Time: 22:59:37
 *
 * @author Roman R&auml;dle
 * @version $Id: GamesPanel.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class GamesPanel extends javax.swing.JPanel {

    private MainFrame mainFrame;
    private String gameType;

    private String selectedResult;

    private javax.swing.JScrollPane gamesSP;
    private javax.swing.JTable gamesT;
    private DefaultTableModel gamesTM;
    private Vector columnIdentifiers;
    private JButton calculateB;
    private JButton saveB;
    private JButton loadB;

    /**
     *
     */
    public GamesPanel(MainFrame mainFrame, String gameType) {

        this.mainFrame = mainFrame;
        this.gameType = gameType;

        initComponents();
        initLayout();
    }

    /**
     *
     */
    private void initComponents() {
        gamesSP = new javax.swing.JScrollPane();
        gamesTM = new DefaultTableModel() {

            /**
             * @see DefaultTableModel#getColumnClass(int)
             */
            public Class<?> getColumnClass(int columnIndex) {
                try {
                    return getValueAt(0, columnIndex).getClass();
                }
                catch (Exception e) {
                    return super.getColumnClass(columnIndex);
                }
            }
        };
        columnIdentifiers = new Vector();
        gamesT = new javax.swing.JTable(gamesTM);
        calculateB = new JButton();
        calculateB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                if ("intermediate_stage".equals(gameType)) {

                    Championship championship = mainFrame.getChampionship();
                    Set<Game> games = championship.getPreliminaryRoundGames();

                    String result = "";
                    for (Game game : games) {
                        if (game.getResult() == null || "".equals(game.getResult()) || game.getResult().indexOf(":") == -1) {
                            result += game.getGamePosition() + ", ";
                        }
                    }

                    if (!"".equals(result)) {
                        JOptionPane.showMessageDialog(mainFrame, "Die Zwischenrunde kann erst berechnet werden wenn das/die Spiel(e)\n" +
                                                                 result + "\n" +
                                                                 "ausgetragen worden sind.", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    calculateIntermediateStageGroups();
                    calculateIntermediateStageGames();
                }
                else if ("quarter_final".equals(gameType)) {

                    Championship championship = mainFrame.getChampionship();
                    Set<Game> games = championship.getIntermediateStageGames();

                    String result = "";
                    for (Game game : games) {
                        if (game.getResult() == null || "".equals(game.getResult()) || game.getResult().indexOf(":") == -1) {
                            result += game.getGamePosition() + ", ";
                        }
                    }

                    if (!"".equals(result)) {
                        JOptionPane.showMessageDialog(mainFrame, "Das Viertelfinale kann erst berechnet werden wenn das/die Spiel(e)\n" +
                                                                 result + "\n" +
                                                                 "ausgetragen worden sind.", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    calculateQuarterFinalGames();
                }
                else if ("semi_final".equals(gameType)) {

                    Championship championship = mainFrame.getChampionship();
                    Set<Game> games = championship.getFinalGames("quarter_final");

                    String result = "";
                    for (Game game : games) {
                        if (game.getResult() == null || "".equals(game.getResult()) || game.getResult().indexOf(":") == -1) {
                            result += game.getGamePosition() + ", ";
                        }
                    }

                    if (!"".equals(result)) {
                        JOptionPane.showMessageDialog(mainFrame, "Das Halbfinale kann erst berechnet werden wenn das/die Spiel(e)\n" +
                                                                 result + "\n" +
                                                                 "ausgetragen worden sind.", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    calculateSemiFinalGames();
                }
                else if ("third_place_game".equals(gameType)) {

                    Championship championship = mainFrame.getChampionship();
                    Set<Game> games = championship.getFinalGames("semi_final");

                    String result = "";
                    for (Game game : games) {
                        if (game.getResult() == null || "".equals(game.getResult()) || game.getResult().indexOf(":") == -1) {
                            result += game.getGamePosition() + ", ";
                        }
                    }

                    if (!"".equals(result)) {
                        JOptionPane.showMessageDialog(mainFrame, "Das Spiel um Platz 3 kann erst berechnet werden wenn das Spiel\n" +
                                                                 result + "\n" +
                                                                 "ausgetragen worden sind.", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    calculateThirdPlaceGames();
                }
                else if ("final".equals(gameType)) {

                    Championship championship = mainFrame.getChampionship();
                    Set<Game> games = championship.getFinalGames("semi_final");

                    String result = "";
                    for (Game game : games) {
                        if (game.getResult() == null || "".equals(game.getResult()) || game.getResult().indexOf(":") == -1) {
                            result += game.getGamePosition() + ", ";
                        }
                    }

                    if (!"".equals(result)) {
                        JOptionPane.showMessageDialog(mainFrame, "Das Finale kann erst berechnet werden wenn das Spiel\n" +
                                                                 result + "\n" +
                                                                 "ausgetragen worden sind.", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    calculateFinalGames();
                }
            }
        });

        saveB = new JButton();
        saveB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                saveTableToGames();
            }
        });

        loadB = new JButton();
        loadB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                loadGamesToTable();
            }
        });

        columnIdentifiers.add("Spiel-Nr.");
        columnIdentifiers.add("Zeit");
        columnIdentifiers.add("Gruppe");
        columnIdentifiers.add("Spiel");
        columnIdentifiers.add("Heim");
        columnIdentifiers.add("Gast");
        columnIdentifiers.add("Ergebnis");

        gamesTM.setColumnIdentifiers(columnIdentifiers);

        gamesT.addMouseListener(new MouseAdapter() {

            /**
             * @see MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
             */
            public void mouseClicked(MouseEvent e) {

                int row = gamesT.getSelectedRow();

                selectedResult = (String) gamesTM.getValueAt(row, 6);
            }
        });

        gamesTM.addTableModelListener(new TableModelListener() {

            /**
             * @see TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
             */
            public void tableChanged(TableModelEvent e) {

                if (e.getColumn() == 4 || e.getColumn() == 5) {

                    int row = e.getFirstRow();

                    Team hometeam = (Team) gamesTM.getValueAt(row, 4);
                    Team awayteam = (Team) gamesTM.getValueAt(row, 5);

                    if (hometeam != null && awayteam != null) {

                        if (!hometeam.getGroupName().equals(awayteam.getGroupName())) {
                            JOptionPane.showMessageDialog(mainFrame, "Diese Paarung ist nicht möglich", "Konfigurationsfehler", JOptionPane.ERROR_MESSAGE);

                            gamesTM.setValueAt(null, row, e.getColumn());

                            return;
                        }

                        //char groupName = (char) (Integer.parseInt(hometeam.getGroupName()) + 64);
                        String groupName = hometeam.getGroupName();
                        String gameVsState = hometeam.getGroupPosition() + " - " + awayteam.getGroupPosition();

                        gamesTM.setValueAt(String.valueOf(groupName), row, 2);
                        gamesTM.setValueAt(gameVsState, row, 3);
                    }
                }
                else if (e.getColumn() == 6) {

                    int row = e.getFirstRow();

                    String result = ((String) gamesTM.getValueAt(row, 6)).trim();

                    if (result.indexOf(":") == -1) {
                        JOptionPane.showMessageDialog(mainFrame, "Es wurde ein falsches Ergebnisformat [Zahl:Zahl] eingetragen.", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Championship championship = mainFrame.getChampionship();

                    Group group = championship.getGroup((String) gamesTM.getValueAt(row, 2));

                    Table table = group.getGroupTable();

                    if (table == null) {
                        table = new Table();
                        group.setGroupTable(table);
                        table.setGroup(group);
                    }

                    Team hometeam = (Team) gamesTM.getValueAt(row, 4);
                    Team awayteam = (Team) gamesTM.getValueAt(row, 5);

                    if ("".equals(selectedResult)) {
                        selectedResult = null;
                    }

                    table.addResult(hometeam, selectedResult, result, true);
                    table.addResult(awayteam, selectedResult, result, false);
                }
            }
        });

        gamesSP.setViewportView(gamesT);

        if ("preliminary_round".equals(gameType)) calculateB.setText("Vorrunde berechnen");
        else if ("intermediate_stage".equals(gameType)) calculateB.setText("Zwischenrunde berechnen");
        else if ("quarter_final".equals(gameType)) calculateB.setText("Viertelfinale berechnen");
        else if ("semi_final".equals(gameType)) calculateB.setText("Halbfinale berechnen");
        else if ("third_place_game".equals(gameType)) calculateB.setText("Spiel um Platz 3 berechnen");
        else if ("final".equals(gameType)) calculateB.setText("Finale berechnen");

        saveB.setText("Speichern");

        loadB.setText("Laden");
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
                                .add(gamesSP, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(calculateB)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(saveB)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(loadB)))
                        .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[]{loadB, calculateB, saveB}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(calculateB)
                                .add(saveB)
                                .add(loadB))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(gamesSP, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                        .addContainerGap())
        );
    }

    /**
     *
     */
    private void saveTableToGames() {

        Championship championship = mainFrame.getChampionship();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Vector dataVector = gamesTM.getDataVector();
        for (Object o : dataVector) {

            try {
                if (o instanceof Vector) {

                    Vector line = (Vector) o;

                    if (line.get(2) != null && !"".equals(line.get(2))) {

                        ValueObjectItem<Integer, Game> valueObjectItem = (ValueObjectItem<Integer, Game>) line.get(0);

                        Game game = valueObjectItem.getObject();

                        if (line.get(1) == null || "".equals(line.get(1))) {
                            JOptionPane.showMessageDialog(mainFrame, "Es muss bei Spiel-Nr. " + valueObjectItem.getValue() + " noch eine Zeit eingegeben werden.", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        game.setTime(dateFormat.parse((String) line.get(1)));
                        game.setGroupName((String) line.get(2));
                        game.setGameVsState((String) line.get(3));
                        game.setHometeam((Team) line.get(4));
                        game.setAwayteam((Team) line.get(5));
                        game.setResult((String) line.get(6));

                        if ("preliminary_round".equals(gameType) ||
                                "intermediate_stage".equals(gameType)) {
                            championship.addGameToGroup(game);
                        }
                        else {
                            championship.addFinalGame(game);
                        }
                    }
                }
            }
            catch (Exception
                    e) {
                e.printStackTrace();
            }
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.saveOrUpdate(championship);
        trx.commit();
        session.close();
    }

    private void loadGamesToTable() {

        Vector dataVector = new Vector();

        Championship championship = mainFrame.getChampionship();

        Set games = null;
        if (championship != null) {

            if ("preliminary_round".equals(gameType)) {
                games = championship.getPreliminaryRoundGames();
            }
            else if ("intermediate_stage".equals(gameType)) {
                games = championship.getIntermediateStageGames();
            }
            else {
                games = championship.getFinalGames(gameType);
            }
        }

        if (games == null) {
            games = Collections.EMPTY_SET;
        }

        int added = 0;
        for (Object o : games) {
            added++;
            dataVector.add(createGameLine((Game) o));
        }

        int gamesCount = 0;

        if ("preliminary_round".equals(gameType)) {
            int teams = championship.getTeamsPerGroup();
            for (String groupName : championship.getPreliminaryRoundGroupDefinitions()) {
                for (int i = 1; i <= teams; i++) {
                    for (int j = i + 1; j <= teams; j++) {
                        ++gamesCount;
                    }
                }
            }
        }
        else if ("intermediate_stage".equals(gameType)) {
            gamesCount += championship.getIntermediateStageGroupCount() * championship.getQualifyingTeams();
        }
        else if ("quarter_final".equals(gameType)) {
            gamesCount += 4;
        }
        else if ("semi_final".equals(gameType)) {
            gamesCount += 2;
        }
        else if ("third_place_game".equals(gameType)) {
            gamesCount += 1;
        }
        else if ("final".equals(gameType)) {
            gamesCount += 1;
        }

        for (; added < gamesCount; added++) {
            Vector line = new Vector();
            Game game = new Game();
            game.setGamePosition(added + 1);
            line.add(new ValueObjectItem<Integer, Game>(added + 1, game));
            dataVector.add(line);
        }

        gamesTM.setDataVector(dataVector, columnIdentifiers);

        if ("preliminary_round".equals(gameType)) {
            Vector selection = VectorUtils.collectionToVector(mainFrame.getChampionship().getAllTeams());
            gamesT.getColumn("Heim").setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(selection))));
            gamesT.getColumn("Gast").setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(selection))));
        }

        gamesT.updateUI();
    }

    private Vector createGameLine(Game game) {

        Vector line = new Vector();

        Date time = game.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        line.add(new ValueObjectItem<Integer, Game>(game.getGamePosition(), game));
        if (time != null) {
            line.add(dateFormat.format(time));
        }
        else {
            line.add("");
        }
        line.add(game.getGroupName());
        line.add(game.getGameVsState());
        line.add(game.getHometeam());
        line.add(game.getAwayteam());
        line.add(game.getResult());

        return line;
    }

    private void calculateIntermediateStageGroups() {

        Championship championship = mainFrame.getChampionship();

        for (String groupName : championship.getIntermediateStageGroupDefinitions()) {
            Group group = championship.getGroup(groupName);

            for (Team tmpTeam : group.getTeams()) {

                String tmpTeamName = tmpTeam.getName();
                String tmpGroupName = tmpTeamName.substring(0, 1);
                String tmpPlacing = tmpTeamName.substring(1, 2);

                Group tmpGroup = championship.getGroup(tmpGroupName);

                Table groupTable = tmpGroup.getGroupTable();
                Team origTeam = groupTable.getTeamAtPlacing(Integer.parseInt(tmpPlacing));

                System.out.println(tmpPlacing + ". " + origTeam.getName());
            }
        }
    }

    private void calculateIntermediateStageGames() {

        Championship championship = mainFrame.getChampionship();
        Set<Linking> linkings = championship.getLinkings(gameType);

        for (Linking linking : linkings) {

            String link1 = linking.getLink1();
            String link2 = linking.getLink2();

            String groupName1 = link1.substring(0, 1);
            String groupName2 = link2.substring(0, 1);

            String placing1 = link1.substring(1, 2);
            String placing2 = link2.substring(1, 2);

            Group group1 = championship.getGroup(groupName1);
            Group group2 = championship.getGroup(groupName2);

            group1.setGroupTable(null);

            Team tmpTeam1 = group1.getTeam(Integer.parseInt(placing1));
            Team tmpTeam2 = group2.getTeam(Integer.parseInt(placing2));

            String tmpTeamName1 = tmpTeam1.getName();
            String tmpTeamName2 = tmpTeam2.getName();

            String tmpGroupName1 = tmpTeamName1.substring(0, 1);
            String tmpGroupName2 = tmpTeamName2.substring(0, 1);

            String tmpPlacing1 = tmpTeamName1.substring(1, 2);
            String tmpPlacing2 = tmpTeamName2.substring(1, 2);

            Group tmpGroup1 = championship.getGroup(tmpGroupName1);
            Group tmpGroup2 = championship.getGroup(tmpGroupName2);

            Team team1 = tmpGroup1.getGroupTable().getTeamAtPlacing(Integer.parseInt(tmpPlacing1));
            Team team2 = tmpGroup2.getGroupTable().getTeamAtPlacing(Integer.parseInt(tmpPlacing2));

            Game game = new Game();
            game.setGamePosition(linking.getGamePosition());
            game.setGameVsState(link1 + " - " + link2);
            game.setHometeam(team1);
            game.setAwayteam(team2);
            game.setGroupName(groupName1);

            championship.addGameToGroup(game);
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.saveOrUpdate(championship);
        trx.commit();
        session.close();

        loadGamesToTable();
    }

    private void calculateQuarterFinalGames() {

        Championship championship = mainFrame.getChampionship();

        int size = championship.getPreliminaryRoundGames().size() + championship.getIntermediateStageGames().size();

        Set<Linking> linkings = championship.getLinkings(gameType);

        for (Linking linking : linkings) {

            String link1 = linking.getLink1();
            String link2 = linking.getLink2();

            String groupName1 = link1.substring(0, 1);
            String groupName2 = link2.substring(0, 1);

            String placing1 = link1.substring(1, 2);
            String placing2 = link2.substring(1, 2);

            Group group1 = championship.getGroup(groupName1);
            Group group2 = championship.getGroup(groupName2);

            Team hometeam = group1.getGroupTable().getTeamAtPlacing(Integer.parseInt(placing1));
            Team awayteam = group2.getGroupTable().getTeamAtPlacing(Integer.parseInt(placing2));

            Game game = new Game();
            game.setGamePosition(linking.getGamePosition());
            game.setGameVsState(link1 + " - " + link2);
            game.setHometeam(hometeam);
            game.setAwayteam(awayteam);
            game.setType(gameType);
            game.setGroupName("V" + (linking.getGamePosition() - size));

            championship.addFinalGame(game);
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.saveOrUpdate(championship);
        trx.commit();
        session.close();

        loadGamesToTable();
    }

    private void calculateSemiFinalGames() {

        Championship championship = mainFrame.getChampionship();

        int size = championship.getPreliminaryRoundGames().size() + championship.getIntermediateStageGames().size() + 4;

        Set<Linking> linkings = championship.getLinkings(gameType);

        for (Linking linking : linkings) {

            String link1 = linking.getLink1();
            String link2 = linking.getLink2();

            Game game1 = championship.getGame(link1);
            Game game2 = championship.getGame(link2);

            String result1 = game1.getResult();
            String result2 = game2.getResult();

            StringTokenizer tokenizer1 = new StringTokenizer(result1, ":");
            int home1 = Integer.parseInt(tokenizer1.nextToken());
            int away1 = Integer.parseInt(tokenizer1.nextToken());

            StringTokenizer tokenizer2 = new StringTokenizer(result2, ":");
            int home2 = Integer.parseInt(tokenizer2.nextToken());
            int away2 = Integer.parseInt(tokenizer2.nextToken());

            Team hometeam;
            if (home1 > away1) {
                hometeam = game1.getHometeam();
            }
            else {
                hometeam = game1.getAwayteam();
            }

            Team awayteam;
            if (home2 > away2) {
                awayteam = game2.getHometeam();
            }
            else {
                awayteam = game2.getAwayteam();
            }

            Game game = new Game();
            game.setGamePosition(linking.getGamePosition());
            game.setGameVsState(link1 + " - " + link2);
            game.setHometeam(hometeam);
            game.setAwayteam(awayteam);
            game.setType(gameType);
            game.setGroupName("H" + (linking.getGamePosition() - size));

            championship.addFinalGame(game);
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.saveOrUpdate(championship);
        trx.commit();
        session.close();

        loadGamesToTable();
    }

    private void calculateThirdPlaceGames() {

        Championship championship = mainFrame.getChampionship();

        int size = championship.getPreliminaryRoundGames().size() + championship.getIntermediateStageGames().size() + 4;

        Set<Linking> linkings = championship.getLinkings(gameType);

        for (Linking linking : linkings) {

            String link1 = linking.getLink1();
            String link2 = linking.getLink2();

            Game game1 = championship.getGame(link1);
            Game game2 = championship.getGame(link2);

            String result1 = game1.getResult();
            String result2 = game2.getResult();

            StringTokenizer tokenizer1 = new StringTokenizer(result1, ":");
            int home1 = Integer.parseInt(tokenizer1.nextToken());
            int away1 = Integer.parseInt(tokenizer1.nextToken());

            StringTokenizer tokenizer2 = new StringTokenizer(result2, ":");
            int home2 = Integer.parseInt(tokenizer2.nextToken());
            int away2 = Integer.parseInt(tokenizer2.nextToken());

            Team hometeam;
            if (home1 < away1) {
                hometeam = game1.getHometeam();
            }
            else {
                hometeam = game1.getAwayteam();
            }

            Team awayteam;
            if (home2 < away2) {
                awayteam = game2.getHometeam();
            }
            else {
                awayteam = game2.getAwayteam();
            }

            Game game = new Game();
            game.setGamePosition(linking.getGamePosition());
            game.setGameVsState(link1 + " - " + link2);
            game.setHometeam(hometeam);
            game.setAwayteam(awayteam);
            game.setType(gameType);
            game.setGroupName("PL3" + (linking.getGamePosition() - size));

            championship.addFinalGame(game);
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.saveOrUpdate(championship);
        trx.commit();
        session.close();

        loadGamesToTable();
    }

    private void calculateFinalGames() {

        Championship championship = mainFrame.getChampionship();

        int size = championship.getPreliminaryRoundGames().size() + championship.getIntermediateStageGames().size() + 4;

        Set<Linking> linkings = championship.getLinkings(gameType);

        for (Linking linking : linkings) {

            String link1 = linking.getLink1();
            String link2 = linking.getLink2();

            Game game1 = championship.getGame(link1);
            Game game2 = championship.getGame(link2);

            String result1 = game1.getResult();
            String result2 = game2.getResult();

            StringTokenizer tokenizer1 = new StringTokenizer(result1, ":");
            int home1 = Integer.parseInt(tokenizer1.nextToken());
            int away1 = Integer.parseInt(tokenizer1.nextToken());

            StringTokenizer tokenizer2 = new StringTokenizer(result2, ":");
            int home2 = Integer.parseInt(tokenizer2.nextToken());
            int away2 = Integer.parseInt(tokenizer2.nextToken());

            Team hometeam;
            if (home1 > away1) {
                hometeam = game1.getHometeam();
            }
            else {
                hometeam = game1.getAwayteam();
            }

            Team awayteam;
            if (home2 > away2) {
                awayteam = game2.getHometeam();
            }
            else {
                awayteam = game2.getAwayteam();
            }

            Game game = new Game();
            game.setGamePosition(linking.getGamePosition());
            game.setGameVsState(link1 + " - " + link2);
            game.setHometeam(hometeam);
            game.setAwayteam(awayteam);
            game.setType(gameType);
            game.setGroupName("F" + (linking.getGamePosition() - size));

            championship.addFinalGame(game);
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.saveOrUpdate(championship);
        trx.commit();
        session.close();

        loadGamesToTable();
    }
}