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
        //para pasar de division a multiplicacion
        this.vista.getRadioDivision().addActionListener(e -> actualizarVistaTabla());
        this.vista.getRadioMultiplicacion().addActionListener(e -> actualizarVistaTabla());
        //Para pasar de lista a arbol
        this.vista.getRadioListas().addActionListener(e -> actualizarVistaTabla());
        this.vista.getRadioArboles().addActionListener(e -> actualizarVistaTabla());
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
                javax.swing.JOptionPane.showMessageDialog(vista, "Placa insertada correctamente." + 
                    " En el slot " + 
                    (vista.getRadioDivision().isSelected() ? modelo.buscarElemento(k, nuevaPlaca.trim())[0] : modelo.buscarElemento(k, nuevaPlaca.trim())[1])
                );
            }
        }
        
        //Delete
        else if (e.getSource() == vista.getBtnDelete()) {
            
            //Validamos si la tabla está vacía antes de hacer cualquier cosa
            if (vista.getTxtAreaTabla().getText().contains("Tabla Hash Vacía")) {
                javax.swing.JOptionPane.showMessageDialog(vista, 
                    "No se puede realizar esta acción ya que la Tabla Hash se encuentra vacía.", 
                    "Acción denegada", 
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            } 
            else {
                String placaBorrar = javax.swing.JOptionPane.showInputDialog(vista, "Ingresa la placa exacta que deseas eliminar:");
                if (placaBorrar != null && !placaBorrar.trim().isEmpty()) {
                    int k = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(placaBorrar.trim());
                    modelo.borrarElemento(k, placaBorrar.trim());
                    actualizarVistaTabla(); // Refresca la T visual
                    javax.swing.JOptionPane.showMessageDialog(vista, "Comando de eliminación ejecutado.\nRevisa la consola para ver detalles del Slot.");
                }
            }
        }
        
       
        // Search
else if (e.getSource() == vista.getBtnSearch()) {
    
    // 1. Validamos si la tabla está vacía antes de buscar
    if (vista.getTxtAreaTabla().getText().contains("Tabla Hash Vacía")) {
        javax.swing.JOptionPane.showMessageDialog(vista, 
            "No se puede realizar esta acción ya que la Tabla Hash se encuentra vacía.", 
            "Acción denegada", 
            javax.swing.JOptionPane.WARNING_MESSAGE);
    } 
    // 2. Si tiene datos, procedemos con la búsqueda según el modo activo
    else {
        String placaBuscar = javax.swing.JOptionPane.showInputDialog(vista, "Ingresa la placa exacta que deseas buscar:");
        if (placaBuscar != null && !placaBuscar.trim().isEmpty()) {
            String placaLimpia = placaBuscar.trim();
            int k = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(placaLimpia);
            
            // Recibimos los slots donde se encuentra según Hash
            int[] slotsEncontrados = modelo.buscarElemento(k, placaLimpia);
            
            // Evaluamos cuál Radio Button de estructura está seleccionado
            boolean modoArbol = vista.getRadioArboles() != null && vista.getRadioArboles().isSelected();
            boolean esDivision = vista.getRadioDivision().isSelected();

            if (!modoArbol) {
                // --- MODO LISTAS ENLAZADAS (HASH COMPLETO) ---
                if (slotsEncontrados[0] != -1 || slotsEncontrados[1] != -1) {
                    String mensaje = "Búsqueda Exitosa (Listas Enlazadas)\n\n"
                                   + "Placa: " + placaLimpia + "\n"
                                   + "Llave (k): " + k + "\n\n"
                                   + "Ubicación en División: Slot " + (slotsEncontrados[0] != -1 ? slotsEncontrados[0] : "No está") + "\n"
                                   + "Ubicación en Multiplicación: Slot " + (slotsEncontrados[1] != -1 ? slotsEncontrados[1] : "No está");
                    
                    javax.swing.JOptionPane.showMessageDialog(vista, mensaje, "Resultado de Búsqueda", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(vista, "La placa " + placaLimpia + " NO existe en la memoria.", "No encontrada", javax.swing.JOptionPane.WARNING_MESSAGE);
                }
            } else {
                // --- MODO ÁRBOLES BINARIOS ---
                // Seleccionamos el slot dependiendo de si la vista actual usa División o Multiplicación
                int slotActual = esDivision ? slotsEncontrados[0] : slotsEncontrados[1];
                String metodoNombre = esDivision ? "División" : "Multiplicación";

                if (slotActual != -1) {
                    String mensajeArbol = "Búsqueda Exitosa en Árbol Binario\n\n"
                                        + "Placa: " + placaLimpia + "\n"
                                        + "Llave (k): " + k + "\n"
                                        + "Método activo: " + metodoNombre + "\n"
                                        + "Encontrada en el Árbol del Slot: " + slotActual;

                    javax.swing.JOptionPane.showMessageDialog(vista, mensajeArbol, "Resultado de Búsqueda en Árbol", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(vista, "La placa " + placaLimpia + " NO existe en el árbol del método " + metodoNombre + ".", "No encontrada", javax.swing.JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
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
                        
                        // Insertamos pasando la llave y la placa
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
        
        //Que método matemático está activo?? (División o Multiplicación?)
        if (vista.getRadioDivision().isSelected()) {
            slotsSeleccionados = modelo.getSlotsDivision();
            colisionesTotales = modelo.getContadorColisionesDivision();
        } else {
            slotsSeleccionados = modelo.getSlotsMultiplicacion();
            colisionesTotales = modelo.getContadorColisionesMultiplicacion();
        }
        
        vista.getLblNumColisiones().setText(String.valueOf(colisionesTotales));
        
        //visualización elegido por el usuario
        boolean modoArbol = vista.getRadioArboles() != null && vista.getRadioArboles().isSelected();
        
        for (int i = 0; i < slotsSeleccionados.length; i++) {
            com.mycompany.taller_8x_tablas_hash.structures.Lista listaActual = slotsSeleccionados[i];
            
            if (!listaActual.estaVacia()) {
                textoVisual.append(String.format("[ Slot %3d ]", i));
                
                if (!modoArbol) {
                    // Lista enlazada
                    String datosCSV = listaActual.aCSV(); 
                    String[] nodos = datosCSV.split(";"); 
                    for (int j = 1; j < nodos.length; j++) {
                        textoVisual.append(" -> [ ").append(nodos[j]).append(" ]");
                    }
                    textoVisual.append("\n");
                } else {
                    // Arbol binario
                    textoVisual.append("\n");
                    
                    //árbol temporal para visualización
                    com.mycompany.taller_8x_tablas_hash.structures.BinaryTree miniArbol = new com.mycompany.taller_8x_tablas_hash.structures.BinaryTree();
                    
                    String datosCSV = listaActual.aCSV(); 
                    String[] nodos = datosCSV.split(";"); 
                    
                    for (int j = 1; j < nodos.length; j++) {
                        String placa = nodos[j];
                        // Pasamos la placa a llave 'k' para el nodo del árbol
                        long llaveK = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(placa);
                        miniArbol.adicionar(llaveK, miniArbol.getRaiz(), 1);
                    }
                    
                    
                    textoVisual.append(miniArbol.aStringVisual());
                    textoVisual.append("   -----------------------------------\n");
                }
            }
        }
        
        if (textoVisual.length() == 0) {
            textoVisual.append("Tabla Hash Vacía.");
        }
        
        vista.getTxtAreaTabla().setText(textoVisual.toString());
    }           
}