/*
 * ChampionshipSelection.java
 *
 * Created on 2. Januar 2006, 14:03
 */

package championship.manager;

/**
 *
 * @author  Roman Georg R�dle
 */
public class ChampionshipSelection extends javax.swing.JPanel {
    
    /** Creates new form ChampionshipSelection */
    public ChampionshipSelection() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        selectionL = new javax.swing.JLabel();
        selectionCB = new javax.swing.JComboBox();
        selectB = new javax.swing.JButton();
        exitB = new javax.swing.JButton();

        selectionL.setText("Championship Auswahl");

        selectionCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        selectB.setText("Ausw\u00e4hlen");

        exitB.setText("Beenden");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(selectionCB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(selectionL))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .add(selectB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(exitB)
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {exitB, selectB}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(selectionL)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(selectionCB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(exitB)
                    .add(selectB))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitB;
    private javax.swing.JButton selectB;
    private javax.swing.JComboBox selectionCB;
    private javax.swing.JLabel selectionL;
    // End of variables declaration//GEN-END:variables
    
}
