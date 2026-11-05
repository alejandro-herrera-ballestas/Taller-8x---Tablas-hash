package com.mycompany.taller_8x_tablas_hash.wiew;
import javax.swing.*; 


public class VentanaPrincipal extends javax.swing.JFrame {
    
    //Constructor de los componentes
    public VentanaPrincipal() {
        initComponents(); 
        this.setLocationRelativeTo(null);
        this.setTitle("Tabla Hash - Estructura de Datos");
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {
    }

    // Getters de  File
    public JButton getBtnOpen() { return btnOpen; }
    public JButton getBtnSave() { return btnSave; }
    public JButton getBtnClose() { return btnClose; }

    // Getters de  Operations
    public JButton getBtnInsert() { return btnInsert; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnSearch() { return btnSearch; }
    // Getters de Hash
    public JRadioButton getRadioDivision() { return radioDivision; }
    public JRadioButton getRadioMultiplicacion() { return radioMultiplicacion; }
    // Getters de visualización
    public JLabel getLblNumColisiones() { return lblNumColisiones; }
    public JLabel getLblRutaArchivo() { return lblRutaArchivo; }
    public JTextArea getTxtAreaTabla() { return txtAreaTabla; }

    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel lblNumColisiones;
    private javax.swing.JLabel lblRutaArchivo;
    private javax.swing.JRadioButton radioDivision;
    private javax.swing.JRadioButton radioMultiplicacion;
    private javax.swing.JTextArea txtAreaTabla;
}
