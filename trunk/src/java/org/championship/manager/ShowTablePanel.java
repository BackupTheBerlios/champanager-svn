/*
 * TablePanel.java
 *
 * Created on 2. Januar 2006, 17:14
 */

package org.championship.manager;

import org.championship.manager.domain.Group;
import org.championship.manager.domain.Table;
import org.championship.manager.domain.TableEntry;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.util.Vector;
import java.util.List;
import java.awt.*;

/**
 * @author Roman Georg Rädle
 */
public class ShowTablePanel extends javax.swing.JPanel {

    private MainFrame mainFrame;

    private javax.swing.JLabel groupL;
    private javax.swing.JScrollPane tableSP;
    private javax.swing.JTable tableT;
    private DefaultTableModel tableTM;
    private Vector columnIdentifiers;

    /**
     *
     */
    public ShowTablePanel(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        initComponents();
        initLayout();
    }

    /**
     *
     */
    private void initComponents() {
        tableSP = new javax.swing.JScrollPane();
        tableTM = new DefaultTableModel();
        tableT = new javax.swing.JTable(tableTM);
        groupL = new javax.swing.JLabel();
        columnIdentifiers = new Vector();

        columnIdentifiers.add("Platz");
        columnIdentifiers.add("Verein");
        columnIdentifiers.add("Spiele");
        columnIdentifiers.add("Torverh.");
        columnIdentifiers.add("Diff.");
        columnIdentifiers.add("Punkte");

        tableTM.setColumnIdentifiers(columnIdentifiers);

        tableT.getColumn("Platz").setMinWidth(30);
        tableT.getColumn("Verein").setMinWidth(450);
        tableT.getColumn("Spiele").setMinWidth(50);
        tableT.getColumn("Torverh.").setMinWidth(100);
        tableT.getColumn("Diff.").setMinWidth(60);
        tableT.getColumn("Punkte").setMinWidth(50);
        tableT.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        tableT.getColumnModel().setColumnMargin(10);

        tableT.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel c = new JLabel();
                c.setFont(new Font("Tahoma", 1, 34));
                c.setText(value.toString());

                if (column == 1) c.setHorizontalAlignment(LEFT);
                else if (column == 2 || column == 3) c.setHorizontalAlignment(CENTER);
                else c.setHorizontalAlignment(RIGHT);

                return c;
            }
        });

        tableSP.setViewportView(tableT);

        tableT.setFont(new java.awt.Font("Tahoma", 1, 34));
        tableT.setRowMargin(0);
        tableT.setRowHeight(41);
        tableT.setRowSelectionAllowed(false);
        tableT.setShowGrid(false);
        tableT.setDragEnabled(false);
        tableT.getColumnModel().setColumnSelectionAllowed(false);
        tableT.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));

        groupL.setFont(new java.awt.Font("Tahoma", 1, 30));
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
                                .add(groupL)
                                .add(tableSP, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(groupL)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tableSP, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 258, Short.MAX_VALUE)
                        .addContainerGap())
        );
    }

    /**
     * @param groupName
     */
    public void setGroupName(String groupName) {
        groupL.setText("Aktuelle Tabelle Gruppe " + groupName);

        Group group = mainFrame.getChampionship().getGroup(groupName);

        Vector dataVector = tableTM.getDataVector();
        dataVector.removeAllElements();

        Table table = null;
        if (group != null) {
            table = group.getGroupTable();
        }

        if (table != null) {

            tableSP.setVisible(true);
            groupL.setVisible(true);

            int i = 1;
            List<TableEntry> entries = table.getEntries();
            for (TableEntry entry : entries) {
                Vector line = new Vector();

                line.add(i++);
                line.add(entry.getTeam().getName());
                line.add(entry.getGameCount());
                line.add(entry.getGoals() + ":" + entry.getGoalsAgainst());
                line.add(entry.getGoals() - entry.getGoalsAgainst());
                line.add(entry.getPoints());

                dataVector.add(line);
            }

        }
        else {
            tableSP.setVisible(false);
            groupL.setVisible(false);
        }

        //tableT.setColumnModel(columnModel);

        tableT.updateUI();
    }

    public void setTablePanelColor(Color color) {
        this.setBackground(color);
        tableT.setBackground(color);
        tableSP.setBackground(color);
        tableSP.getViewport().setBackground(color);
    }

    public JTable getTable() {
        return tableT;
    }
}
