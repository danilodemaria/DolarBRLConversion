/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dolar;

import com.google.gson.Gson;
import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class Tela extends javax.swing.JFrame {

    double valorAtual = 0;

    public Tela() throws NoSuchFieldException, FileNotFoundException, ParseException {
        AplicaNimbusLookAndFeel.pegaNimbus();
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        iniciar();
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fechar();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
    }

    public boolean fechar() {
        JOptionPane.showMessageDialog(null, "Fechando aplicação. Clique em 'Ok' para continuar.");
        System.exit(0);
        return true;
    }

    public void iniciar() throws NoSuchFieldException, FileNotFoundException, ParseException {
        String aux;
        String data = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        dataLabel.setText(data);
        buscaDadosAPI();
        aux = String.valueOf(valorAtual);
        dolarAtual.setText(String.format("%.2f", valorAtual));
        dolarCambio.setText(String.format("%.2f", (valorAtual - 0.05)));
        reais.addKeyListener(new ValorMasc(reais, 7, 2));
        dolar.addKeyListener(new ValorMasc(dolar, 7, 2));
        reais.addKeyListener(listener);
        dolar.addKeyListener(listDolar);
        Color minhaCor = new Color(204, 255, 204);
        this.getContentPane().setBackground(minhaCor);
    }

    KeyListener listener = new KeyListener() {

        @Override
        public void keyPressed(KeyEvent event) {
        }

        @Override
        public void keyReleased(KeyEvent event) {
            if (event.getKeyCode() == 17) {
                reais.setText(null);
                dolar.setText(null);
            } else {
                calculateMouseClicked(null);
            }
        }

        @Override
        public void keyTyped(KeyEvent event) {
        }
    };

    KeyListener listDolar = new KeyListener() {

        @Override
        public void keyPressed(KeyEvent event) {
        }

        @Override
        public void keyReleased(KeyEvent event) {
            if (event.getKeyCode() == 17) {
                dolar.setText(null);
                reais.setText(null);
            } else {
                cambio();
            }
        }

        @Override
        public void keyTyped(KeyEvent event) {
        }
    };

    public void cambio() {
        double aux, aux1;
        DecimalFormat df = new DecimalFormat("####.##");
        aux = converteValor(dolar.getText());
        aux1 = Double.parseDouble(dolarCambio.getText().replace(",", "."));
        aux = aux * (aux1);
        reais.setText(String.valueOf(df.format(aux)));
    }

    public void buscaDados() throws NoSuchFieldException, FileNotFoundException, ParseException {

        String retorno = null;
        ConexaoHttp a = new ConexaoHttp();

        String url = "http://economia.awesomeapi.com.br/USD-BRL/1";

        try {
            retorno = a.sendGet(url);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar valor do dolar.");
            System.exit(0);
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }

        Gson g = new Gson();
        RequestJson[] p = g.fromJson(retorno, RequestJson[].class);
        //retorno = retorno.substring(68, 73);
        //retorno = retorno.replace("valor\":", "");
        valorAtual = Double.parseDouble(p[0].getHigh());

        String aux = String.valueOf(p[0].getCreate_date());

        String dataEmUmFormato = aux;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        Date data = formato.parse(dataEmUmFormato);
        formato.applyPattern("dd/MM/yyyy HH:mm:SS");
        String dataFormatada = formato.format(data);

    }
    
    public void buscaDadosAPI() throws ParseException{
        String retorno = null;
        ConexaoHttp a = new ConexaoHttp();
        Gson g = new Gson();
        String dolar, peso, ibovespa, euro,aux;
        
        String url = "https://api.hgbrasil.com/finance/quotations?format=json&key=f2baf3d6";

        try {
            retorno = a.sendGet(url);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar valor do dolar.");
            System.exit(0);
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        dolar = retorno.substring(102, 106);        
        euro = retorno.substring(165,174);
        euro = euro.replaceAll("[^0-9.]", "");
        euro = euro.substring(0, 4);
        peso = retorno.substring(310,326);
        peso = peso.replaceAll("[^0-9.]", "");
        
        peso = peso.substring(0, 4);
        ibovespa = retorno.substring(510,534);
        System.out.println(ibovespa);
        valorAtual = Double.parseDouble(dolar);
        
        aux = retorno.substring(510,540);
        aux = aux.replaceAll("[^0-9.]", "");
        
        textEuro.setText("R$ "+euro);
        textPeso.setText("R$ "+peso);
        textIbovespa.setText(aux+" pontos");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        image = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        dolarAtual = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dolarCambio = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        reais = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dolar = new javax.swing.JTextField();
        calculate = new javax.swing.JButton();
        cleanFields = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        dolarAtual1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dataLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        textEuro = new javax.swing.JLabel();
        textPeso = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        textIbovespa = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hotel Marin Château - Conversão de Dolar");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dolar/Guarda-sol.jpg"))); // NOI18N
        getContentPane().add(image, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 463, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Dolar Atual: R$");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 97, -1, -1));

        dolarAtual.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dolarAtual.setForeground(new java.awt.Color(255, 0, 0));
        dolarAtual.setText("jLabel2");
        getContentPane().add(dolarAtual, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 97, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Dolar Câmbio: R$");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 97, -1, -1));

        dolarCambio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dolarCambio.setForeground(new java.awt.Color(255, 0, 0));
        dolarCambio.setText("jLabel2");
        getContentPane().add(dolarCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(308, 97, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Valor em R$ ");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 159, -1, -1));

        reais.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        reais.setForeground(new java.awt.Color(0, 0, 255));
        reais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reaisActionPerformed(evt);
            }
        });
        getContentPane().add(reais, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 156, 166, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Valor em US$");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 186, -1, -1));

        dolar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dolar.setForeground(new java.awt.Color(0, 0, 255));
        dolar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dolarActionPerformed(evt);
            }
        });
        getContentPane().add(dolar, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 183, 166, -1));

        calculate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dolar/shuffle.png"))); // NOI18N
        calculate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calculateMouseClicked(evt);
            }
        });
        getContentPane().add(calculate, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 297, -1, -1));

        cleanFields.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cleanFields.setText("Limpar Campos");
        cleanFields.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cleanFieldsMouseClicked(evt);
            }
        });
        getContentPane().add(cleanFields, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 299, -1, -1));

        refresh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        refresh.setText("Atualizar");
        refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshMouseClicked(evt);
            }
        });
        getContentPane().add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 297, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Taxa de Câmbio:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 118, -1, -1));

        dolarAtual1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dolarAtual1.setForeground(new java.awt.Color(255, 0, 0));
        dolarAtual1.setText("R$ 0,05");
        getContentPane().add(dolarAtual1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 118, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setText("Data:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 118, -1, -1));

        dataLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dataLabel.setForeground(new java.awt.Color(255, 0, 0));
        dataLabel.setText("R$ 0,05");
        getContentPane().add(dataLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 118, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("*Desenvolvido por Danilo de Maria");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 340, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setText("Euro");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 234, -1, -1));

        textEuro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        textEuro.setForeground(new java.awt.Color(255, 0, 0));
        textEuro.setText("jLabel2");
        getContentPane().add(textEuro, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 234, -1, -1));

        textPeso.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        textPeso.setForeground(new java.awt.Color(255, 0, 0));
        textPeso.setText("jLabel2");
        getContentPane().add(textPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 255, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("Peso");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 255, -1, -1));

        textIbovespa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        textIbovespa.setForeground(new java.awt.Color(255, 0, 0));
        textIbovespa.setText("jLabel2");
        getContentPane().add(textIbovespa, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 276, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 255));
        jLabel11.setText("Ibovespa");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 276, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reaisActionPerformed
        // TODO add your handling code here:
        dolar.requestFocus();
    }//GEN-LAST:event_reaisActionPerformed

    private void cleanFieldsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cleanFieldsMouseClicked
        // TODO add your handling code here:
        reais.setText(null);
        dolar.setText(null);
        reais.requestFocus();
    }//GEN-LAST:event_cleanFieldsMouseClicked

    private void refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshMouseClicked

        try {
            // TODO add your handling code here:
            iniciar();
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(null, "Valores atualizados com sucesso!");
        reais.requestFocus();
    }//GEN-LAST:event_refreshMouseClicked

    private void dolarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dolarActionPerformed
        // TODO add your handling code here:
        reais.requestFocus();
    }//GEN-LAST:event_dolarActionPerformed

    private void calculateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calculateMouseClicked
        // TODO add your handling code here:
        double aux, aux1;
        DecimalFormat df = new DecimalFormat("####.##");
        aux = converteValor(reais.getText());
        aux1 = Double.parseDouble(dolarCambio.getText().replace(",", "."));
        aux = aux / (aux1);
        dolar.setText(String.valueOf(df.format(aux)));
    }//GEN-LAST:event_calculateMouseClicked

    public double converteValor(String aux) {

        aux = aux.replace(".", "");
        aux = aux.replace(",", ".");
        return Double.parseDouble(aux);
    }

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Tela().setVisible(true);
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculate;
    private javax.swing.JButton cleanFields;
    private javax.swing.JLabel dataLabel;
    private javax.swing.JTextField dolar;
    private javax.swing.JLabel dolarAtual;
    private javax.swing.JLabel dolarAtual1;
    private javax.swing.JLabel dolarCambio;
    private javax.swing.JLabel image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField reais;
    private javax.swing.JButton refresh;
    private javax.swing.JLabel textEuro;
    private javax.swing.JLabel textIbovespa;
    private javax.swing.JLabel textPeso;
    // End of variables declaration//GEN-END:variables
}
