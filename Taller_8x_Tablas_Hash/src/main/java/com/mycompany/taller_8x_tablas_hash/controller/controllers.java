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
    private void abrirArchivoCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
        
        int seleccion = fileChooser.showOpenDialog(vista);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            vista.getLblRutaArchivo().setText(" Ruta: " + ruta);
            
            // Usamos invokeLater para que la ventana del explorador se cierre al instante
            javax.swing.SwingUtilities.invokeLater(() -> {
                try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(ruta))) {
                    String linea;
                    modelo.reiniciarContadores(); // Limpiar la memoria antes de cargar nuevos datos
                    
                    while ((linea = br.readLine()) != null) {
                        if (linea.trim().isEmpty()) continue;
                        
                        String[] columnas = linea.split(";");
                        if (columnas.length == 0) continue;
                        
                        String plate = columnas[0].trim();
                        
                        // Calculamos la llave llamando a la clase de tu compañero
                        int k = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(plate);
                        
                        // Insertamos en la Tabla Hash
                        modelo.insertarElemento(k); 
                    }
                    
                    // Actualizamos la pantalla con los resultados
                    actualizarVistaTabla();
                    javax.swing.JOptionPane.showMessageDialog(vista, "Archivo cargado exitosamente.", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Error leyendo el CSV: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    // Método para actualizar los números y el recuadro "T"
    private void actualizarVistaTabla() {
        StringBuilder textoVisual = new StringBuilder();
        int colisionesTotales = 0;
        // Verificamos qué función matemática está seleccionada en los Radio Buttons
        if (vista.getRadioDivision().isSelected()) {
            colisionesTotales = modelo.getContadorColisionesDivision();
            textoVisual.append("-- Mostrando Memoria (Método: División) --\n\n");
        } else {
            colisionesTotales = modelo.getContadorColisionesMultiplicacion();
            textoVisual.append("-- Mostrando Memoria (Método: Multiplicación) --\n\n");
        }
        //Actualizar colicione
        vista.getLblNumColisiones().setText(String.valueOf(colisionesTotales));
        //Imprimimos un texto que indica que hay datos cargados
        textoVisual.append("Los datos han sido cargados en la memoria (").append(modelo.getM()).append(" slots).\n");
        textoVisual.append("Total de colisiones generadas: ").append(colisionesTotales).append("\n\n");
        textoVisual.append("(Nota: Falta pintar los nodos exactos aquí hasta analizar la clase Lista)");
        
        vista.getTxtAreaTabla().setText(textoVisual.toString());
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

    //funcion para insertar un elemento en la tabla hash (div)
    private void insertarElementoDivHash(int k) {
        modelo.insertarElementoDiv(k);
        actualizarVistaTabla();
    }

    // funcion para insertar un elemento en la tabla hash (multiplicacion)
    private void insertarElementoMultHash(int k) {
        modelo.insertarElementoMult(k);
        actualizarVistaTabla();
    }
}
