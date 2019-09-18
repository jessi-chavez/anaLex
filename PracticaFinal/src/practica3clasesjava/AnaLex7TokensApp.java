package practica3clasesjava;

import javax.swing.table.DefaultTableModel;

public class AnaLex7TokensApp extends javax.swing.JFrame {

    //Objeto de tipo Lexico
    Lexico anaLex = new Lexico();

    public AnaLex7TokensApp() {
        initComponents();
        //Le damos las dimenciones al frame
        this.setSize(966, 536);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaProgFuente = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblParejasTL = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnAnaLex = new javax.swing.JButton();
        lblResult = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("PROGRAMA FUENTE");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 10, 136, 17);

        txaProgFuente.setColumns(20);
        txaProgFuente.setLineWrap(true);
        txaProgFuente.setRows(5);
        jScrollPane1.setViewportView(txaProgFuente);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 530, 320);

        tblParejasTL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TOKENS", "LEXEMAS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblParejasTL);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(560, 30, 370, 320);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("PAREJAS TOKENS-LEXEMAS ");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(560, 10, 200, 17);

        btnAnaLex.setText("Análisis Lexico");
        btnAnaLex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnaLexActionPerformed(evt);
            }
        });
        getContentPane().add(btnAnaLex);
        btnAnaLex.setBounds(10, 370, 150, 30);

        lblResult.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblResult.setText("Resultado del Análisis Lexico");
        getContentPane().add(lblResult);
        lblResult.setBounds(170, 380, 200, 17);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Boton Analisis Lexico
    private void btnAnaLexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnaLexActionPerformed
        //Iniciamos el analizador
        anaLex.Inicia();
        
        //Condicion para saber si al analizar el texto, sale correcto o si dio error
        if (anaLex.Analiza(txaProgFuente.getText() + " " + "\n") && !"".equals(txaProgFuente.getText())) {
            lblResult.setText("Lexical correct");
        } else {
            lblResult.setText("Lexical error");
        }
        
        //Modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tblParejasTL.getModel();
        //Definimos la rabla con 0 renglones
        modelo.setRowCount(0);
        
        //Llenamos la tabla con los tokens y lexemas
        for (int i = 0; i < anaLex.NoTokens(); i++) {
            Object o[] = {anaLex.Tokens()[i], anaLex.Lexemas()[i]};
            modelo.addRow(o);
        }

    }//GEN-LAST:event_btnAnaLexActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AnaLex7TokensApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnaLex7TokensApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnaLex7TokensApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnaLex7TokensApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnaLex7TokensApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnaLex;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblResult;
    private javax.swing.JTable tblParejasTL;
    private javax.swing.JTextArea txaProgFuente;
    // End of variables declaration//GEN-END:variables

}
