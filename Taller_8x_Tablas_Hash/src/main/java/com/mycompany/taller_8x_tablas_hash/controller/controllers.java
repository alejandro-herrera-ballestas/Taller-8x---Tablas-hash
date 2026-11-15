package com.mycompany.taller_8x_tablas_hash.controller;

import com.mycompany.taller_8x_tablas_hash.wiew.VentanaPrincipal;
import com.mycompany.taller_8x_tablas_hash.structures.TablaHash.OperacionesHash;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class controllers implements ActionListener {

    private VentanaPrincipal vista;
    private OperacionesHash modelo;

    public controllers(VentanaPrincipal vista, OperacionesHash modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // Acciones de los botones
        this.vista.getBtnOpen().addActionListener(this);
        this.vista.getBtnInsert().addActionListener(this);
        this.vista.getBtnDelete().addActionListener(this);
        this.vista.getBtnSearch().addActionListener(this);
        this.vista.getBtnSave().addActionListener(this);
        this.vista.getBtnClose().addActionListener(this);

        // Cambio entre División y Multiplicación
        this.vista.getRadioDivision().addActionListener(e -> actualizarVistaTabla());
        this.vista.getRadioMultiplicacion().addActionListener(e -> actualizarVistaTabla());

        // Cambio entre Lista Enlazada y Árbol Binario
        this.vista.getRadioListas().addActionListener(e -> actualizarVistaTabla());
        this.vista.getRadioArboles().addActionListener(e -> actualizarVistaTabla());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Open
        if (e.getSource() == vista.getBtnOpen()) {
            abrirArchivoCSV();
        } 
        // Close
        else if (e.getSource() == vista.getBtnClose()) {
            System.exit(0);
        } 
        // Save
        else if (e.getSource() == vista.getBtnSave()) {
            String nombreArchivo = JOptionPane.showInputDialog(vista, "Ingresa el nombre del archivo (ej. exportacion.csv):", "Guardar CSV", JOptionPane.QUESTION_MESSAGE);
            if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                if (!nombreArchivo.endsWith(".csv")) {
                    nombreArchivo += ".csv";
                }

                if (vista.getRadioDivision().isSelected()) {
                    modelo.exportarTablaDivisionCSV(nombreArchivo);
                } else {
                    modelo.exportarTablaMultiplicacionCSV(nombreArchivo);
                }
                JOptionPane.showMessageDialog(vista, "Archivo " + nombreArchivo + " guardado exitosamente.");
            }
        } 
        // Insert
        else if (e.getSource() == vista.getBtnInsert()) {
            String nuevaPlaca = JOptionPane.showInputDialog(vista, "Ingresa la nueva placa a insertar (ej. 12-3456789):");
            if (nuevaPlaca != null && !nuevaPlaca.trim().isEmpty()) {
                int k = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(nuevaPlaca.trim());
                modelo.insertarElemento(k, nuevaPlaca.trim());
                actualizarVistaTabla();
                JOptionPane.showMessageDialog(vista, "Placa insertada correctamente."
                        + " En el slot "
                        + (vista.getRadioDivision().isSelected() ? modelo.buscarElemento(k, nuevaPlaca.trim())[0] : modelo.buscarElemento(k, nuevaPlaca.trim())[1])
                );
            }
        } 
        // Delete
        else if (e.getSource() == vista.getBtnDelete()) {
            if (vista.getTxtAreaTabla().getText().contains("Tabla Hash Vacía")) {
                JOptionPane.showMessageDialog(vista,
                        "No se puede realizar esta acción ya que la Tabla Hash se encuentra vacía.",
                        "Acción denegada",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                String placaBorrar = JOptionPane.showInputDialog(vista, "Ingresa la placa exacta que deseas eliminar:");
                if (placaBorrar != null && !placaBorrar.trim().isEmpty()) {
                    int k = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(placaBorrar.trim());
                    modelo.borrarElemento(k, placaBorrar.trim());
                    actualizarVistaTabla();
                    JOptionPane.showMessageDialog(vista, "Comando de eliminación ejecutado.\nRevisa la consola para ver detalles del Slot.");
                }
            }
        } 
        // Search
        else if (e.getSource() == vista.getBtnSearch()) {
            if (vista.getTxtAreaTabla().getText().contains("Tabla Hash Vacía")) {
                JOptionPane.showMessageDialog(vista,
                        "No se puede realizar esta acción ya que la Tabla Hash se encuentra vacía.",
                        "Acción denegada",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                String placaBuscar = JOptionPane.showInputDialog(vista, "Ingresa la placa exacta que deseas buscar:");
                if (placaBuscar != null && !placaBuscar.trim().isEmpty()) {
                    String placaLimpia = placaBuscar.trim();
                    int k = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(placaLimpia);

                    int[] slotsEncontrados = modelo.buscarElemento(k, placaLimpia);
                    boolean modoArbol = vista.getRadioArboles() != null && vista.getRadioArboles().isSelected();
                    boolean esDivision = vista.getRadioDivision().isSelected();

                    if (!modoArbol) {
                        if (slotsEncontrados[0] != -1 || slotsEncontrados[1] != -1) {
                            String mensaje = "Búsqueda Exitosa (Listas Enlazadas)\n\n"
                                    + "Placa: " + placaLimpia + "\n"
                                    + "Llave (k): " + k + "\n\n"
                                    + "Ubicación en División: Slot " + (slotsEncontrados[0] != -1 ? slotsEncontrados[0] : "No está") + "\n"
                                    + "Ubicación en Multiplicación: Slot " + (slotsEncontrados[1] != -1 ? slotsEncontrados[1] : "No está");

                            JOptionPane.showMessageDialog(vista, mensaje, "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(vista, "La placa " + placaLimpia + " NO existe en la memoria.", "No encontrada", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        int slotActual = esDivision ? slotsEncontrados[0] : slotsEncontrados[1];
                        String metodoNombre = esDivision ? "División" : "Multiplicación";

                        if (slotActual != -1) {
                            String mensajeArbol = "Búsqueda Exitosa en Árbol Binario\n\n"
                                    + "Placa: " + placaLimpia + "\n"
                                    + "Llave (k): " + k + "\n"
                                    + "Método activo: " + metodoNombre + "\n"
                                    + "Encontrada en el Árbol del Slot: " + slotActual;

                            JOptionPane.showMessageDialog(vista, mensajeArbol, "Resultado de Búsqueda en Árbol", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(vista, "La placa " + placaLimpia + " NO existe en el árbol del método " + metodoNombre + ".", "No encontrada", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        }
    }

    private void abrirArchivoCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));

        if (fileChooser.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            vista.getLblRutaArchivo().setText(" Ruta: " + ruta);

            SwingUtilities.invokeLater(() -> {
                try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(ruta))) {
                    String linea;
                    modelo.reiniciarContadores();

                    while ((linea = br.readLine()) != null) {
                        if (linea.trim().isEmpty()) continue;

                        String[] columnas = linea.split(";");
                        if (columnas.length == 0) continue;

                        String plate = columnas[0].trim();
                        int k = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(plate);
                        modelo.insertarElemento(k, plate);
                    }

                    actualizarVistaTabla();
                   // com.mycompany.taller_8x_tablas_hash.models.ComparadorRendimiento.comparar(ruta);
                    JOptionPane.showMessageDialog(vista, "Datos cargados y procesados en la Tabla Hash.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

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

        vista.getLblNumColisiones().setText(String.valueOf(colisionesTotales));
        boolean modoArbol = vista.getRadioArboles() != null && vista.getRadioArboles().isSelected();

        for (int i = 0; i < slotsSeleccionados.length; i++) {
            com.mycompany.taller_8x_tablas_hash.structures.Lista listaActual = slotsSeleccionados[i];

            if (!listaActual.estaVacia()) {
                textoVisual.append(String.format("[ Slot %3d ]", i));

                if (!modoArbol) {
                    // Modo Lista Enlazada
                    String datosCSV = listaActual.aCSV();
                    String[] nodos = datosCSV.split(";");
                    for (int j = 1; j < nodos.length; j++) {
                        textoVisual.append(" -> [ ").append(nodos[j]).append(" ]");
                    }
                    textoVisual.append("\n");
                } else {
                    // Modo Árbol Binario (mostrando placas)
                    textoVisual.append("\n");

                    com.mycompany.taller_8x_tablas_hash.structures.BinaryTree miniArbol = new com.mycompany.taller_8x_tablas_hash.structures.BinaryTree();
                    Map<Long, String> mapaPlacas = new HashMap<>();

                    String datosCSV = listaActual.aCSV();
                    String[] nodos = datosCSV.split(";");

                    for (int j = 1; j < nodos.length; j++) {
                        String placa = nodos[j];
                        long llaveK = com.mycompany.taller_8x_tablas_hash.models.leerCSVascii.calcularLlave(placa);
                        mapaPlacas.put(llaveK, placa);
                        miniArbol.adicionar(llaveK, miniArbol.getRaiz(), 1);
                    }

                    textoVisual.append(miniArbol.aStringVisual(mapaPlacas));
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