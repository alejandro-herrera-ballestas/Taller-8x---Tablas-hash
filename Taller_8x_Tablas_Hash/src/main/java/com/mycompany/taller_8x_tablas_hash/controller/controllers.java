package com.mycompany.taller_8x_tablas_hash.controller;

import com.mycompany.taller_8x_tablas_hash.wiew.VentanaPrincipal;
import com.mycompany.taller_8x_tablas_hash.structures.TablaHash.OperacionesHash;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class controllers implements ActionListener{
    
    private VentanaPrincipal vista;
    private OperacionesHash modelo;

    public controllers(VentanaPrincipal vista, OperacionesHash modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        //Las acciones que realizan los botones del controlador
        this.vista.getBtnOpen().addActionListener(this);
        this.vista.getBtnInsert().addActionListener(this);
        this.vista.getBtnDelete().addActionListener(this);
        this.vista.getBtnSearch().addActionListener(this);
        this.vista.getBtnSave().addActionListener(this);
        this.vista.getBtnClose().addActionListener(this);
        this.vista.getBtnInfo().addActionListener(this); 
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //Abrir csv
        if (e.getSource() == vista.getBtnOpen()) {
            abrirArchivoCSV();
        }
        //cerrar csv
        if (e.getSource() == vista.getBtnClose()) {
            System.exit(0); 
        }   
        //mostrar ficha de info
        if (e.getSource() == vista.getBtnInfo()) {
           mostrarInfoTecnica();
        } 
    }
    
    //ventana de apertura
     private void abrirArchivoCSV() {
           JFileChooser fileChooser = new JFileChooser();
           fileChooser.setDialogTitle("Seleccionar archivo MOCK_DATA.csv");
           fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        
           int seleccion = fileChooser.showOpenDialog(vista);
        
           if (seleccion == JFileChooser.APPROVE_OPTION) {
               String ruta = fileChooser.getSelectedFile().getAbsolutePath();
               vista.getLblRutaArchivo().setText(" Ruta: " + ruta);
            
               // Usamos invokeLater para separar la lógica de carga de la interfaz visual
               SwingUtilities.invokeLater(() -> {
                   try {
                       // --- SIMULACIÓN VISUAL (Mientras conectamos la lógica real) ---
                       StringBuilder textoVisual = new StringBuilder();
                       textoVisual.append("[ Slot 299 ] -> [ 05-9165610 ] -> [ XX-XXXXXXX ]\n");
                       textoVisual.append("[ Slot 300 ] -> [ Libre ]\n");
                       textoVisual.append("[ Slot 301 ] -> [ 05-1234567 ]\n");
                    
                       vista.getTxtAreaTabla().setText(textoVisual.toString());
                       vista.getLblNumColisiones().setText("1");
                    
                   } catch (Exception ex) {
                       JOptionPane.showMessageDialog(vista, "Error procesando el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                   }
               });
           }
    }
       //Funcion de ficha tecnica, es un simple agregado del problema
        private void mostrarInfoTecnica() {
        String mensaje = "<html><body style='width: 250px; font-family: Segoe UI;'>"
                       + "<h2 style='color: #8A2BE2;'>Tabla Hash - Specs</h2>"
                       + "<p><b>Arquitectura:</b> Modelo-Vista-Controlador (MVC)</p>"
                       + "<p><b>Algoritmo Key:</b> Método de Horner (Radix-2) para conversión de cadenas ASCII.</p>"
                       + "<p><b>Resolución de Colisiones:</b> Chaining mediante Listas Simplemente Enlazadas.</p>"
                       + "<p><b>Capacidad (m):</b> 256 Slots predefinidos.</p>"
                       + "<hr>"
                       + "<p style='font-size: 10px; color: gray;'>Proyecto Taller 8x</p>"
                       + "</body></html>";
                       
        JOptionPane.showMessageDialog(vista, mensaje, "Información Técnica", JOptionPane.INFORMATION_MESSAGE);
    }
}
