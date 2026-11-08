package com.mycompany.taller_8x_tablas_hash.models;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class leerCSVascii {

    // Calcula la llave entera k a partir de un texto sumando los códigos ASCII de cada carácter
    public static int calcularLlave(String texto) {
        int k = 0;
        for (int i = 0; i < texto.length(); i++) {
            k =(k * 2) + (int) texto.charAt(i);
        }
        return k;
    }
    
    
    public static void main(String[] args) {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));   // Filtro para mostrar solo archivos CSV
        
        if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File archivo = fc.getSelectedFile();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;   // leer y omitir líneas vacías

                String[] columnas = linea.split(";"); 
                String plate = columnas[0].trim();  // Obtiene la placa y elimina espacios en blanco

                int k = calcularLlave(plate);   // Calcula la llave entera k a partir de la placa
                
                System.out.println("Placa: " + plate + " -> Llave (k): " + k);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }
    }
}
