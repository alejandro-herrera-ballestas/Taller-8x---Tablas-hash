package com.mycompany.taller_8x_tablas_hash.controller;

import com.mycompany.taller_8x_tablas_hash.wiew.VentanaPrincipal;
import com.mycompany.taller_8x_tablas_hash.structures.TablaHash.OperacionesHash;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class controllers implements ActionListener{
    
    private VentanaPrincipal vista;
    private OperacionesHash modelo;

    public controllers(VentanaPrincipal vista, OperacionesHash modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        //Las acciones que realizan los botones del controlador
        this.vista.getBtnOpen().addActionListener(this);
        this.vista.getBtnOpen().addActionListener(this);
        this.vista.getBtnInsert().addActionListener(this);
        this.vista.getBtnDelete().addActionListener(this);
        this.vista.getBtnSearch().addActionListener(this);
        this.vista.getBtnSave().addActionListener(this);
        this.vista.getBtnClose().addActionListener(this);
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
    }
    
    //ventana de apertura
    private void abrirArchivoCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo MOCK_DATA.csv");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        
        int seleccion = fileChooser.showOpenDialog(vista);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            // Actualizamos la etiqueta de la ventana con la ruta[cite: 2]
            vista.getLblRutaArchivo().setText("Ruta: " + ruta);
            
            //Aquí llamaremos al método que lee el archivo y llena la tabla hash
            System.out.println("Archivo seleccionado: " + ruta);
        }
    }
    
}
