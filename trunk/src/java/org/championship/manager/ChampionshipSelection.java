/*
 * ChampionshipSelection.java
 *
 * Created on 2. Januar 2006, 14:03
 */

package org.championship.manager;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.util.List;

import org.championship.manager.domain.Championship;
import org.championship.manager.util.HibernateUtil;
import org.championship.manager.util.ComponentUtilities;
import org.championship.manager.images.png16x16.Icons16;
import org.championship.manager.images.Images;
import org.championship.manager.splashscreen.FadingSplashScreen;

import javax.swing.*;

/**
 * @author Roman Georg R�dle
 */
public class ChampionshipSelection extends javax.swing.JFrame {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        FadingSplashScreen fadingSplashScreen = new FadingSplashScreen(
                Images.class.getResourceAsStream("championship_png24.png"),
                32,
                3) {

            public void doBefore() {
                System.out.println("ChampionshipSelection.doBefore");
                //new Thread(new Runnable() {
                //    public void run() {
                        HibernateUtil.getSessionFactory().openSession().close();
                //    }
                //});
            }
        };
		fadingSplashScreen.setVisible(true);
		fadingSplashScreen.setFinishedCallback(new Runnable() {
			public void run() {
                new ChampionshipSelection();
            }
		});
		//fadingSplashScreen.startFading();
        fadingSplashScreen.start();
    }

    private javax.swing.JButton exitB;
    private javax.swing.JButton newChampionship;
    private javax.swing.JButton selectB;
    private javax.swing.JComboBox selectionCB;
    private javax.swing.JLabel selectionL;

    /**
     *
     */
    public ChampionshipSelection() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(Icons16.class.getResource("environment.png")));
        setTitle("Championship Auswahl");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
        initLayout();
        initListener();

        pack();

        ComponentUtilities.centerComponentOnScreen(this);

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
        newChampionship = new javax.swing.JButton();

        selectionL.setText("Championship Auswahl");

        reload();

        selectB.setText("Ausw\u00e4hlen");

        exitB.setText("Beenden");

        newChampionship.setText("Neuer Championship");
    }

    /**
     *
     */
    private void initLayout() {
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(selectionCB, 0, 464, Short.MAX_VALUE)
                                .add(selectionL)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(newChampionship)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(selectB)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 59, Short.MAX_VALUE)
                                .add(exitB)))
                        .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[]{exitB, newChampionship, selectB}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(selectionL)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(selectionCB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(exitB)
                                .add(newChampionship)
                                .add(selectB))
                        .addContainerGap())
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
                exit();
            }
        });

        newChampionship.addActionListener(new ActionListener() {

            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                new SetupDialog(ChampionshipSelection.this, "Neuer Championship", true);
            }
        });
    }

    public void reload() {

        selectionCB.removeAllItems();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        List championships = session.createQuery("from " + Championship.class.getName() + " as cs order by cs.name").list();
        trx.commit();
        session.close();

        if (championships.size() == 0) {
            new SetupDialog(this, "Neuer Championship", true);
        }

        for (Object o : championships) {
            selectionCB.addItem(o);
        }
    }

    public void exit() {
        ChampionshipSelection.this.dispose();
        HibernateUtil.shutdown();
        System.exit(0);
    }
}
