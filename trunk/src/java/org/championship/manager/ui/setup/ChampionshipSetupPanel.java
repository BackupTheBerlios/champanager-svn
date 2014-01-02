package org.championship.manager.ui.setup;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import org.championship.manager.MainFrame;
import org.championship.manager.domain.Championship;
import org.championship.manager.ui.Changable;

// TODO: document me!!!

/**
 * ChampionshipSetupPanel.
 * <p/>
 * User: rro
 * Date: 30.12.2005
 * Time: 22:59:37
 *
 * @author Roman R&auml;dle
 * @version $Id: ChampionshipSetupPanel.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class ChampionshipSetupPanel extends JPanel implements Changable {

    private MainFrame mainFrame;

    private boolean hasChanged;

    private JLabel championshipL;
    private JTextField championshipTF;
    private JLabel groupL;
    private JSpinner groupSp;
    private JCheckBox intermediatStageCB;
    private JLabel intermediateStageGroupL;
    private JSpinner intermediateStageGroupSp;
    private JLabel qualifyingL;
    private JSpinner qualifyingSp;
    private JCheckBox quarterFinalCB;
    private JLabel teamL;
    private JSpinner teamSp;
    private JCheckBox thirdPlaceGameCB;

    /**
     *
     */
    public ChampionshipSetupPanel(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        initComponents();
        initLayout();

        initValues();
    }

    /**
     *
     */
    private void initComponents() {
        championshipL = new javax.swing.JLabel();
        championshipTF = new javax.swing.JTextField();
        groupL = new javax.swing.JLabel();
        groupSp = new javax.swing.JSpinner();
        teamL = new javax.swing.JLabel();
        teamSp = new javax.swing.JSpinner();
        intermediatStageCB = new javax.swing.JCheckBox();
        qualifyingL = new javax.swing.JLabel();
        qualifyingSp = new javax.swing.JSpinner();
        intermediateStageGroupL = new javax.swing.JLabel();
        intermediateStageGroupSp = new javax.swing.JSpinner();
        quarterFinalCB = new javax.swing.JCheckBox();
        thirdPlaceGameCB = new javax.swing.JCheckBox();

        championshipL.setText("Name des Turnieres");

        groupL.setText("Wie viele Gruppen?");
        teamL.setText("Wie viele Mannschaften pro Gruppe?");

        intermediatStageCB.setText("Hat das Turnier eine Zwischenrunde?");
        intermediatStageCB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        intermediatStageCB.setMargin(new java.awt.Insets(0, 0, 0, 0));

        qualifyingL.setText("Wie viele Mannschaften pro Gruppe qualifizieren sich?");

        intermediateStageGroupL.setText("Wie viele Gruppen gibt es in der Zwischenrunde?");

        quarterFinalCB.setText("Hat das Turnier ein Viertelfinale?");
        quarterFinalCB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        quarterFinalCB.setMargin(new java.awt.Insets(0, 0, 0, 0));

        thirdPlaceGameCB.setText("Hat das Turnier ein Spiel um Platz 3?");
        thirdPlaceGameCB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        thirdPlaceGameCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
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
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(championshipTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(championshipL)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(intermediatStageCB)
                        .addContainerGap(185, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, intermediateStageGroupL)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, qualifyingL)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                        .add(groupL)
                                        .add(216, 216, 216))
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                        .add(teamL, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                        .add(151, 151, 151)))
                                .add(25, 25, 25)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(qualifyingSp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(teamSp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(groupSp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(intermediateStageGroupSp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(quarterFinalCB)
                        .addContainerGap(207, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(thirdPlaceGameCB)
                        .addContainerGap(189, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(championshipL)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(championshipTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(30, 30, 30)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(groupL)
                    .add(groupSp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(teamL)
                    .add(teamSp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(29, 29, 29)
                .add(intermediatStageCB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(qualifyingL)
                    .add(qualifyingSp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(intermediateStageGroupL)
                    .add(intermediateStageGroupSp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(29, 29, 29)
                .add(quarterFinalCB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(thirdPlaceGameCB)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    /**
     *
     */
    public void initValues() {

        if (mainFrame != null) {
            Championship championship = mainFrame.getChampionship();
            if (championship == null) {
                return;
            }

            championshipTF.setText(championship.getName());
            groupSp.setValue(championship.getGroupCount());
            teamSp.setValue(championship.getTeamsPerGroup());
            intermediatStageCB.setSelected(championship.getIntermediateStage());
            qualifyingSp.setValue(championship.getQualifyingTeams());
            intermediateStageGroupSp.setValue(championship.getIntermediateStageGroupCount());
            quarterFinalCB.setSelected(championship.getQuarterFinal());
            thirdPlaceGameCB.setSelected(championship.getThirdPlaceGame());
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasChanged() {

        Championship championship = mainFrame.getChampionship();

        if (!championshipTF.getText().equals(championship.getName())) return true;
        else if (!groupSp.getValue().equals(championship.getGroupCount())) return true;
        else if (!teamSp.getValue().equals(championship.getTeamsPerGroup())) return true;
        else if (intermediatStageCB.isSelected() != championship.getIntermediateStage()) return true;
        else if (!qualifyingSp.getValue().equals(championship.getQualifyingTeams())) return true;
        else if (!intermediateStageGroupSp.getValue().equals(championship.getIntermediateStageGroupCount())) return true;
        else if (quarterFinalCB.isSelected() != championship.getQuarterFinal()) return true;
        else if (thirdPlaceGameCB.isSelected() != championship.getThirdPlaceGame()) return true;

        return false;
    }

    public String getChampionshipName() {
        return championshipTF.getText();
    }

    public Integer getGroupCount() {
        return (Integer) groupSp.getValue();
    }

    public Boolean isIntermediatStage() {
        return intermediatStageCB.isSelected();
    }

    public Integer getIntermediateStageGroupCount() {
        return (Integer) intermediateStageGroupSp.getValue();
    }

    public Integer getQualifyingTeams() {
        return (Integer) qualifyingSp.getValue();
    }

    public Boolean isQuarterFinal() {
        return quarterFinalCB.isSelected();
    }

    public Integer getTeamsPerCount() {
        return (Integer) teamSp.getValue();
    }

    public Boolean isThirdPlaceGame() {
        return thirdPlaceGameCB.isSelected();
    }
}
