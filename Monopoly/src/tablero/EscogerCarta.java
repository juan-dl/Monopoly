/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablero;

import casilla.Accion;
import excepcion.JugadorException;
import java.awt.Point;
import javax.swing.ImageIcon;
import jugador.Avatar;

/**
 *
 * @author david
 */
public class EscogerCarta extends javax.swing.JFrame {

    private int valor;
    private Point localizacionInicial;
    private ImageIcon suerte, comunidad;
    private EnunciadoCarta enunciado;
    private VentanaJuego juego;
    private boolean tipo;       //True comunidad - False suerte
    
    /**
     * Creates new form EscogerCarta
     */
    public EscogerCarta(VentanaJuego juego) 
    {
        initComponents();
        setLocationRelativeTo(null);
        localizacionInicial = getLocation();
        this.juego = juego;
        
        suerte = new ImageIcon(getClass().getResource("imagenes/cartaSuerte.png"));
        comunidad = new ImageIcon(getClass().getResource("imagenes/cartaComunidad.png"));
        
        enunciado = new EnunciadoCarta(this);
    }
    
    public void setTipo(boolean tipo)
    {
        if(tipo)
        {
            jLabel1.setIcon(comunidad);
            jLabel2.setIcon(comunidad);
            jLabel3.setIcon(comunidad);
            jLabel4.setIcon(comunidad);
            jLabel5.setIcon(comunidad);
            jLabel6.setIcon(comunidad);      
        }
        else
        {
            jLabel1.setIcon(suerte);
            jLabel2.setIcon(suerte);
            jLabel3.setIcon(suerte);
            jLabel4.setIcon(suerte);
            jLabel5.setIcon(suerte);
            jLabel6.setIcon(suerte);
        }
    }

    public EnunciadoCarta getEnunciado() 
    {
        return enunciado;
    }
    
    public VentanaJuego getJuego() 
    {
        return juego;
    }

    public int getValor()
    {
        return valor;
    }
    
    public void eventoCarta(int n)
    {
        Avatar f = juego.getPartida().getFiguras().get(juego.getPartida().getOrdenTurnos().get(juego.getPartida().getTurnoActual()));
        valor = n;
        
        try
        {
            ((Accion)f.getUbicacion()).aplicarAccion(f.getJugador());
        }
        catch(JugadorException excepcion)
        {
            juego.getPartida().resolucionJugadorException(excepcion);
        }
        
        enunciado.setVisible(true);
        setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(640, 460));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(253, 238, 120));

        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 237, 59), 4, true));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 237, 59), 4, true));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 237, 59), 4, true));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 237, 59), 4, true));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 237, 59), 4, true));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 237, 59), 4, true));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("URW Chancery L", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(254, 102, 34));
        jLabel7.setText("Escoja una carta");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(56, 56, 56)
                        .addComponent(jLabel2)
                        .addGap(58, 58, 58)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(56, 56, 56)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(58, 58, 58)
                                .addComponent(jLabel6)))))
                .addContainerGap(296, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(68, 68, 68)
                .addComponent(jLabel7)
                .addGap(64, 64, 64)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap(253, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        juego.getPartida().getConfirmacion().setVisible(true);
        juego.getPartida().getConfirmacion().setMensaje("Desea cerrar la aplicacion? :(");
    }//GEN-LAST:event_formWindowClosing

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        eventoCarta(3);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        eventoCarta(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        eventoCarta(1);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        eventoCarta(2);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        eventoCarta(5);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        eventoCarta(4);
    }//GEN-LAST:event_jLabel5MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
