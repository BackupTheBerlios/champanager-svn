package championship.manager;

import championship.manager.util.ComponentUtil;
import championship.manager.util.HibernateUtil;
import championship.manager.domain.Championship;
import championship.manager.domain.Team;
import championship.manager.domain.Group;

import javax.swing.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.HashMap;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.hibernate.Session;
import org.hibernate.Transaction;

// TODO: document me!!!

/**
 * GroupSetupPanel.
 * <p/>
 * User: rro
 * Date: 30.12.2005
 * Time: 22:59:37
 *
 * @author Roman R&auml;dle
 * @version $Id: GroupSetupPanel.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class GroupSetupPanel extends JPanel {

    private MainFrame mainFrame;

    private String groupType;

    private JLabel[] teamPositions;
    private Map<String, JTextField[]> teamNamesMap = new HashMap<String, JTextField[]>();
    private JTextField[] teamNamesTF;

    private javax.swing.JComboBox groupCB;
    private javax.swing.JLabel groupL;
    private javax.swing.JPanel teamsP;
    private javax.swing.JScrollPane teamsSP;

    /**
     *
     */
    public GroupSetupPanel(MainFrame mainFrame, String groupType) {

        this.mainFrame = mainFrame;
        this.groupType = groupType;

        initComponents();
        initValues();
        initLayout();

        if (mainFrame != null) {
            Championship championship = mainFrame.getChampionship();

            if ("preliminary_groups".equals(groupType)) {
                for (String groupName : championship.getPreliminaryRoundGroupDefinitions()) {
                    Group group = championship.getGroup(groupName);

                    initGroup(group != null ? group : new Group(groupName));
                }
            }
            else if ("intermediate_groups".equals(groupType)) {
                for (String groupName : championship.getIntermediateStageGroupDefinitions()) {
                    Group group = championship.getGroup(groupName);

                    initGroup(group != null ? group : new Group(groupName));
                }
            }

            Group group = championship.getGroup(groupCB.getSelectedItem().toString());
            initTeamInput(group != null ? group : new Group(groupCB.getSelectedItem().toString()));
        }
    }

    /**
     *
     */
    private void initComponents() {
        groupL = new javax.swing.JLabel();
        teamsSP = new javax.swing.JScrollPane();
        teamsP = new javax.swing.JPanel();
        groupCB = new javax.swing.JComboBox();
        groupCB.addItemListener(new ItemListener() {

            /**
             * @see ItemListener#itemStateChanged(java.awt.event.ItemEvent)
             */
            public void itemStateChanged(ItemEvent e) {

                if (teamPositions != null) {
                    Championship championship = mainFrame.getChampionship();
                    Group group = championship.getGroup(groupCB.getSelectedItem().toString());
                    initTeamInput(group != null ? group : new Group(groupCB.getSelectedItem().toString()));
                }
            }
        });

        groupL.setText("Gruppe");
    }

    /**
     *
     */
    private void initLayout() {

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(teamsSP, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                .add(GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(groupL)
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(groupCB, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(GroupLayout.BASELINE)
                                .add(groupL)
                                .add(groupCB, GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(teamsSP, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                        .addContainerGap())
        );
    }

    /**
     *
     */
    private void initValues() {

        if (mainFrame != null) {
            Championship championship = mainFrame.getChampionship();

            if (championship == null) {
                return;
            }

            if ("preliminary_groups".equals(groupType)) {
                DefaultComboBoxModel model = new DefaultComboBoxModel(championship.getPreliminaryRoundGroupDefinitions().toArray());
                groupCB.setModel(model);
            }
            else if ("intermediate_groups".equals(groupType)) {
                DefaultComboBoxModel model = new DefaultComboBoxModel(championship.getIntermediateStageGroupDefinitions().toArray());
                groupCB.setModel(model);
            }
        }
    }

    /**
     *
     */
    private void initGroup(Group group) {

        Championship championship = mainFrame.getChampionship();

        int count = 0;
        if ("preliminary_groups".equals(groupType)) {
            count = championship.getTeamsPerGroup();
        }
        else if ("intermediate_groups".equals(groupType)) {
            count = championship.getQualifyingTeams();
        }

        if (teamPositions == null) {
            teamPositions = new JLabel[count];
        }
        teamNamesTF = teamNamesMap.get(group.getName());

        if (teamNamesTF == null) {
            teamNamesTF = new JTextField[count];
        }
        for (int i = 0; i < count; i++) {
            teamPositions[i] = new JLabel();
            teamPositions[i].setText((i + 1) + ".");

            teamNamesTF[i] = new JTextField();

            Team team = null;
            if (group != null) team = group.getTeam(i + 1);

            teamNamesTF[i].setText(team != null ? team.getName() : "");
        }

        teamNamesMap.put(group.getName(), teamNamesTF);
    }

    /**
     *
     */
    private void initTeamInput(Group group) {

        teamsP.removeAll();

        GroupLayout.ParallelGroup parallelGroup;
        GroupLayout.SequentialGroup sequentialGroup;

        GroupLayout teamsPLayout = new GroupLayout(teamsP);
        teamsP.setLayout(teamsPLayout);
        teamsPLayout.setHorizontalGroup(
                parallelGroup = teamsPLayout.createParallelGroup(GroupLayout.LEADING));
        teamsPLayout.setVerticalGroup(
                teamsPLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, sequentialGroup = teamsPLayout.createSequentialGroup()));

        sequentialGroup.addContainerGap();

        JTextField[] teamNamesTF = teamNamesMap.get(group.getName());

        int i = 0;
        int count = teamPositions.length;
        for (JLabel label : teamPositions) {

            JTextField field = teamNamesTF[i++];

            parallelGroup.add(GroupLayout.LEADING, teamsPLayout.createSequentialGroup().addContainerGap()
                    .add(label)
                    .addPreferredGap(LayoutStyle.RELATED)
                    .add(field, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addContainerGap());
            sequentialGroup.add(teamsPLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(label)
                    .add(field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));

            if (i < count) {
                sequentialGroup.addPreferredGap(LayoutStyle.RELATED);
            }
        }

        sequentialGroup.addContainerGap(87, Short.MAX_VALUE);

        teamsSP.setViewportView(teamsP);
        teamsSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    public String getSelectedGroupName() {
        return groupCB.getSelectedItem().toString();
    }

    public Map<String, JTextField[]> getTeamNames() {
        return teamNamesMap;
    }
}
