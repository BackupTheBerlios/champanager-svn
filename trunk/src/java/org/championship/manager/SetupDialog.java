/*
 * SettupPanel.java
 *
 * Created on 30. Dezember 2005, 20:26
 */

package org.championship.manager;

import org.championship.manager.util.ComponentUtilities;
import org.championship.manager.util.HibernateUtil;
import org.championship.manager.domain.Championship;
import org.championship.manager.domain.Group;
import org.championship.manager.domain.Team;
import org.championship.manager.ui.setup.GroupSetupPanel;
import org.championship.manager.ui.setup.TeamLinkingPanel;
import org.championship.manager.ui.setup.ChampionshipSetupPanel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * @author Roman Georg R�dle
 */
public class SetupDialog extends JDialog {

    public MainFrame mainFrame;
    public ChampionshipSelection championshipSelection;

    // Whether the client made changes on that dialog.
    private boolean hasChanged;

    private javax.swing.JButton applyB;
    private javax.swing.JButton cancelB;
    private ChampionshipSetupPanel championshipSetupP;
    private TeamLinkingPanel finalLinkingP;
    private GroupSetupPanel groupSetupP;
    private GroupSetupPanel intermediateStageGroupSetupP;
    private TeamLinkingPanel intermediateStageLinkingP;
    private javax.swing.JButton okB;
    private TeamLinkingPanel quarterFinalLinkingP;
    private TeamLinkingPanel semiFinalLinkingP;
    private javax.swing.JTabbedPane setupTP;
    private TeamLinkingPanel thirdPlaceGameLinkingP;

    public SetupDialog(ChampionshipSelection championshipSelection, String title, boolean modal) {
        super(championshipSelection, title, modal);

        this.championshipSelection = championshipSelection;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
        initLayout();
        initListener();

        initValues();

        pack();

        ComponentUtilities.centerComponentOnScreen(this);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    /**
     * @param mainFrame
     * @param title
     * @param modal
     */
    public SetupDialog(MainFrame mainFrame, String title, boolean modal) {
        super(mainFrame, title, modal);

        this.mainFrame = mainFrame;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
        initLayout();
        initListener();

        initValues();

        pack();

        ComponentUtilities.centerComponentOnScreen(this);

        setVisible(true);
    }

    /**
     *
     */
    private void initComponents() {
        okB = new javax.swing.JButton();
        cancelB = new javax.swing.JButton();
        applyB = new javax.swing.JButton();
        setupTP = new javax.swing.JTabbedPane();
        championshipSetupP = new ChampionshipSetupPanel(mainFrame);
        groupSetupP = new GroupSetupPanel(mainFrame, "preliminary_groups");
        intermediateStageGroupSetupP = new GroupSetupPanel(mainFrame, "intermediate_groups");
        intermediateStageLinkingP = new TeamLinkingPanel(mainFrame, "intermediate_stage", 24, 61);
        quarterFinalLinkingP = new TeamLinkingPanel(mainFrame, "quarter_final", 4, 85);
        semiFinalLinkingP = new TeamLinkingPanel(mainFrame, "semi_final", 2, 89);
        thirdPlaceGameLinkingP = new TeamLinkingPanel(mainFrame, "third_place_game", 1, 91);
        finalLinkingP = new TeamLinkingPanel(mainFrame, "final", 1, 92);

        okB.setText("OK");

        cancelB.setText("Abbrechen");

        applyB.setText("\u00dcbernehmen");

        setupTP.addTab("Turnier", championshipSetupP);

        setupTP.addTab("Gruppen", groupSetupP);

        setupTP.addTab("Gruppen Zwischenrunde", intermediateStageGroupSetupP);

        setupTP.addTab("Zwischenrunde", intermediateStageLinkingP);

        setupTP.addTab("Viertelfinale", quarterFinalLinkingP);

        setupTP.addTab("Halbfinale", semiFinalLinkingP);

        setupTP.addTab("Spiel um Platz 3", thirdPlaceGameLinkingP);

        setupTP.addTab("Finale", finalLinkingP);
    }

    /**
     *
     */
    private void initLayout() {
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(setupTP, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                                .add(layout.createSequentialGroup()
                                .add(okB)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(cancelB)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(applyB)))
                        .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[]{applyB, cancelB, okB}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(setupTP, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 334, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(36, 36, 36)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(applyB)
                                .add(cancelB)
                                .add(okB))
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    /**
     *
     */
    private void initListener() {

        applyB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                saveOrUpdateChampionship();
            }
        });

        okB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                saveOrUpdateChampionship();
                SetupDialog.this.dispose();
            }
        });

        cancelB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                if (championshipSetupP.hasChanged()) {

                    int option = JOptionPane.showConfirmDialog(SetupDialog.this, "Möchten Sie die Änderungen übernehmen?", "Änderungen übernehmen?", JOptionPane.YES_NO_CANCEL_OPTION);

                    if (JOptionPane.YES_OPTION == option) {
                        saveOrUpdateChampionship();
                    }
                    else if (JOptionPane.CANCEL_OPTION == option) {
                        return; // Do nothing if client press cancel button.
                    }
                }
                SetupDialog.this.dispose();
            }
        });
    }

    /**
     *
     */
    private void initValues
            () {
        championshipSetupP.initValues();
    }

    /**
     *
     */
    private void saveOrUpdateChampionship
            () {

        // Get the current <code>Championship</code>.
        Championship championship = null;
        if (mainFrame != null) {
            championship = mainFrame.getChampionship();
        }

        // Check whether a <code>Championship</code> exists with the
        // current name in the database.
        if (championship == null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            championship = (Championship) session.createQuery("from " + Championship.class.getName() + " as cs where cs.name = '" + championshipSetupP.getChampionshipName() + "'").uniqueResult();
            session.close();
        }

        // If there isn't a <code>Championship</code> just create a
        // new one.
        if (championship == null) {
            championship = new Championship();
        }

        championship.setName(championshipSetupP.getChampionshipName());
        championship.setGroupCount(championshipSetupP.getGroupCount());
        championship.setTeamsPerGroup(championshipSetupP.getTeamsPerCount());
        championship.setIntermediateStage(championshipSetupP.isIntermediatStage());
        championship.setIntermediateStageGroupCount(championshipSetupP.getIntermediateStageGroupCount());
        championship.setQualifyingTeams(championshipSetupP.getQualifyingTeams());
        championship.setQuarterFinal(championshipSetupP.isQuarterFinal());
        championship.setThirdPlaceGame(championshipSetupP.isThirdPlaceGame());

        if (mainFrame != null) {
            for (String groupName : groupSetupP.getTeamNames().keySet()) {

                Group group = championship.getGroup(groupName);
                if (group == null) {
                    group = new Group();
                    group.setName(groupName);
                    championship.addGroup(groupName, group);
                }

                Team team;
                int i = 1;
                for (JTextField field : groupSetupP.getTeamNames().get(groupName)) {

                    team = new Team();
                    team.setName(field.getText());
                    team.setGroupPosition(i++);

                    group.addTeam(team);
                }
            }

            for (String groupName : intermediateStageGroupSetupP.getTeamNames().keySet()) {

                Group group = championship.getGroup(groupName);
                if (group == null) {
                    group = new Group();
                    group.setName(groupName);
                    championship.addGroup(groupName, group);
                }

                Team team;
                int i = 1;
                for (JTextField field : intermediateStageGroupSetupP.getTeamNames().get(groupName)) {

                    team = new Team();
                    team.setName(field.getText());
                    team.setGroupPosition(i++);

                    group.addTeam(team);
                }
            }

            championship.addLinkings(intermediateStageLinkingP.getLinkings());
            championship.addLinkings(quarterFinalLinkingP.getLinkings());
            championship.addLinkings(semiFinalLinkingP.getLinkings());
            championship.addLinkings(thirdPlaceGameLinkingP.getLinkings());
            championship.addLinkings(semiFinalLinkingP.getLinkings());
            championship.addLinkings(finalLinkingP.getLinkings());
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.saveOrUpdate(championship);
        trx.commit();
        session.close();

        if (mainFrame != null) {
            mainFrame.setChampionship(championship);
        }

        if (championshipSelection != null) {
            championshipSelection.reload();
        }
    }
}
