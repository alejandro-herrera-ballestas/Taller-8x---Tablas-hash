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
        //para pasar de division a multiplicacion
        this.vista.getRadioDivision().addActionListener(e -> actualizarVistaTabla());
        this.vista.getRadioMultiplicacion().addActionListener(e -> actualizarVistaTabla());
    }
    
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        
        //Open
        if (e.getSource() == vista.getBtnOpen()) {
            abrirArchivoCSV();
        }
        
        //Close
        else if (e.getSource() == vista.getBtnClose()) {
            System.exit(0); // 
        }
        
        //Save
        else if (e.getSource() == vista.getBtnSave()) {
            String nombreArchivo = javax.swing.JOptionPane.showInputDialog(vista, "Ingresa el nombre del archivo (ej. exportacion.csv):", "Guardar CSV", javax.swing.JOptionPane.QUESTION_MESSAGE);
            if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                if (!nombreArchivo.endsWith(".csv")) nombreArchivo += ".csv"; 
                
                if (vista.getRadioDivision().isSelected()) {
                    modelo.exportarTablaDivisionCSV(nombreArchivo);
                } else {
                    modelo.exportarTablaMultiplicacionCSV(nombreArchivo);
                }
                javax.swing.JOptionPane.showMessageDialog(vista, "Archivo " + nombreArchivo + " guardado exitosamente.");
            }
        }
        
        //Insert
        else if (e.getSource() == vista.getBtnInsert()) {
            String nuevaPlaca = javax.swing.JOptionPane.showInputDialog(vista, "Ingresa la nueva placa a insertar (ej. 12-3456789):");
            if (nuevaPlaca != null && !nuevaPlaca.trim().isEmpty()) {
                int k = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(nuevaPlaca.trim());
                modelo.insertarElemento(k, nuevaPlaca.trim());
                actualizarVistaTabla(); // Refresca la T visual
                javax.swing.JOptionPane.showMessageDialog(vista, "Placa insertada correctamente.");
            }
        }
        
        //Delete
        else if (e.getSource() == vista.getBtnDelete()) {
            String placaBorrar = javax.swing.JOptionPane.showInputDialog(vista, "Ingresa la placa exacta que deseas eliminar:");
            if (placaBorrar != null && !placaBorrar.trim().isEmpty()) {
                int k = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(placaBorrar.trim());
                modelo.borrarElemento(k, placaBorrar.trim());
                actualizarVistaTabla(); // Refresca la T visual
                javax.swing.JOptionPane.showMessageDialog(vista, "Comando de eliminación ejecutado.\nRevisa la consola para ver detalles del Slot.");
            }
        }
    }
    
    private void abrirArchivoCSV() {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));
        
        if (fileChooser.showOpenDialog(vista) == javax.swing.JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            vista.getLblRutaArchivo().setText(" Ruta: " + ruta);
            
            javax.swing.SwingUtilities.invokeLater(() -> {
                try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(ruta))) {
                    String linea;
                    modelo.reiniciarContadores(); // Limpiamos memoria
                    
                    while ((linea = br.readLine()) != null) {
                        if (linea.trim().isEmpty()) continue;
                        
                        String[] columnas = linea.split(";");
                        if (columnas.length == 0) continue;
                        
                        String plate = columnas[0].trim(); // Extraemos la placa
                        
                        // (Usando el Radix-2 que actualizaste)
                        int k = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(plate);
                        
                        // Insertamos pasando LA LLAVE y LA PLACA
                        modelo.insertarElemento(k, plate); 
                    }
                    
                    actualizarVistaTabla();
                    javax.swing.JOptionPane.showMessageDialog(vista, "Datos cargados y procesados en la Tabla Hash.", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

       // Método para actualizar los números y el recuadro "T"
        private void actualizarVistaTabla() {
        StringBuilder textoVisual = new StringBuilder();
        com.mycompany.taller_8x_tablas_hash.structures.Lista[] slotsSeleccionados;
        int colisionesTotales = 0;
        
        if (vista.getRadioDivision().isSelected()) {
            slotsSeleccionados = modelo.getSlotsDivision();
            colisionesTotales = modelo.getContadorColisionesDivision();
        } else {
            slotsSeleccionados = modelo.getSlotsMultiplicacion();
            colisionesTotales = modelo.getContadorColisionesMultiplicacion();
        }
        
        // Actualizar contador 
        vista.getLblNumColisiones().setText(String.valueOf(colisionesTotales));
        
        // Dibujar la tabla
        for (int i = 0; i < slotsSeleccionados.length; i++) {
            com.mycompany.taller_8x_tablas_hash.structures.Lista listaActual = slotsSeleccionados[i];
            
            if (!listaActual.estaVacia()) {
                textoVisual.append(String.format("[ Slot %3d ]", i));
                
                String datosCSV = listaActual.aCSV(); // Devuelve algo como ";05-9165610;05-1234567"
                String[] nodos = datosCSV.split(";"); // Lo separamos en un arreglo
                
                // Recorremos el arreglo (empezando en 1 porque el índice 0 queda vacío por el primer ';')
                for (int j = 1; j < nodos.length; j++) {
                    textoVisual.append(" -> [ ").append(nodos[j]).append(" ]");
                }
                textoVisual.append("\n"); // Salto de línea
            }
        }
        
        if (textoVisual.length() == 0) {
            textoVisual.append("Tabla Hash Vacía.");
        }
        
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
}
