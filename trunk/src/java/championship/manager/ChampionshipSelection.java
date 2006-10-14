/*
 * ChampionshipSelection.java
 *
 * Created on 2. Januar 2006, 14:03
 */

package championship.manager;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import championship.manager.domain.Championship;
import championship.manager.util.HibernateUtil;
import championship.manager.util.ComponentUtil;

import javax.swing.*;

/**
 *
 * @author  Roman Georg Rädle
 */
public class ChampionshipSelection extends javax.swing.JFrame {

    public static void main(String[] args) {
        new ChampionshipSelection();
    }

    private javax.swing.JButton exitB;
    private javax.swing.JButton selectB;
    private javax.swing.JComboBox selectionCB;
    private javax.swing.JLabel selectionL;

    /**
     *
     */
    public ChampionshipSelection() {

        initComponents();
        initLayout();
        initListener();

        setTitle("Championship Auswahl");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();

        ComponentUtil.centerComponentOnScreen(this);

        setVisible(true);
    }

    /**
     *
     */
    private void initComponents() {
        selectionL = new javax.swing.JLabel();
        selectionCB = new javax.swing.JComboBox();
        selectB = new javax.swing.JButton();
        exitB = new javax.swing.JButton();

        selectionL.setText("Championship Auswahl");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        List championships = session.createQuery("from " + Championship.class.getName()).list();
        trx.commit();
        session.close();

        if (championships.size() == 0) {
            new SetupDialog(this, "Neuer Championship", true);
        }

        selectionCB.setModel(new javax.swing.DefaultComboBoxModel(championships.toArray()));

        selectB.setText("Ausw\u00e4hlen");

        exitB.setText("Beenden");
    }

    /**
     *
     */
    private void initLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.LEADING)
                    .add(selectionCB, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                    .add(selectionL))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .add(selectB)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(exitB)
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {exitB, selectB}, GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(selectionL)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(selectionCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(exitB)
                    .add(selectB))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    private void initListener() {

        selectB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                Championship championship = (Championship) selectionCB.getSelectedItem();

                new MainFrame(championship);

                ChampionshipSelection.this.dispose();
            }
        });

        exitB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                ChampionshipSelection.this.dispose();
                System.exit(0);
            }
        });
    }

    public void reload() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        List championships = session.createQuery("from " + Championship.class.getName()).list();
        trx.commit();
        session.close();

        System.out.println("SIZE: " + championships.size());

        if (championships.size() == 0) {
            new SetupDialog(this, "Neuer Championship", true);
        }

        selectionCB.setModel(new javax.swing.DefaultComboBoxModel(championships.toArray()));
    }
}
