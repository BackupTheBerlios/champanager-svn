/*
 * SettupPanel.java
 *
 * Created on 30. Dezember 2005, 20:26
 */

package championship.manager;

import championship.manager.util.ComponentUtil;
import championship.manager.util.HibernateUtil;
import championship.manager.domain.Championship;
import championship.manager.domain.Group;
import championship.manager.domain.Team;
import championship.manager.domain.Table;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author Roman Georg Rädle
 */
public class SetupDialog extends JDialog {

    public MainFrame mainFrame;
    public ChampionshipSelection championshipSelection;

    private javax.swing.JButton applyB;
    private javax.swing.JButton cancelB;
    private championship.manager.ChampionshipSetupPanel championshipSetupP;
    private championship.manager.TeamLinkingPanel finalLinkingP;
    private championship.manager.GroupSetupPanel groupSetupP;
    private championship.manager.GroupSetupPanel intermediateStageGroupSetupP;
    private championship.manager.TeamLinkingPanel intermediateStageLinkingP;
    private javax.swing.JButton okB;
    private championship.manager.TeamLinkingPanel quarterFinalLinkingP;
    private championship.manager.TeamLinkingPanel semiFinalLinkingP;
    private javax.swing.JTabbedPane setupTP;
    private championship.manager.TeamLinkingPanel thirdPlaceGameLinkingP;

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

        ComponentUtil.centerComponentOnScreen(this);

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

        ComponentUtil.centerComponentOnScreen(this);

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
        championshipSetupP = new championship.manager.ChampionshipSetupPanel(mainFrame);
        groupSetupP = new championship.manager.GroupSetupPanel(mainFrame, "preliminary_groups");
        intermediateStageGroupSetupP = new championship.manager.GroupSetupPanel(mainFrame, "intermediate_groups");
        intermediateStageLinkingP = new championship.manager.TeamLinkingPanel(mainFrame, "intermediate_stage", 24, 61);
        quarterFinalLinkingP = new championship.manager.TeamLinkingPanel(mainFrame, "quarter_final", 4, 85);
        semiFinalLinkingP = new championship.manager.TeamLinkingPanel(mainFrame, "semi_final", 2, 89);
        thirdPlaceGameLinkingP = new championship.manager.TeamLinkingPanel(mainFrame, "third_place_game", 1, 91);
        finalLinkingP = new championship.manager.TeamLinkingPanel(mainFrame, "final", 1, 92);

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
                                .add(setupTP)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(okB)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(cancelB)
                                .add(10, 10, 10)
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
                SetupDialog.this.dispose();
            }
        });
    }

    /**
     *
     */
    private void initValues() {
        championshipSetupP.initValues();
    }

    /**
     *
     */
    private void saveOrUpdateChampionship() {

        Championship championship = null;
        if (mainFrame != null) {
            championship = mainFrame.getChampionship();
        }

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
        else {
            championshipSelection.reload();
        }
    }
}
