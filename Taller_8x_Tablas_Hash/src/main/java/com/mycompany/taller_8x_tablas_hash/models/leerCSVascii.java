/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.taller_8x_tablas_hash.models;

/**
 *
 * @author User
 */

// import para seleccionar y leer archivo
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class leerCSVascii  {

    public static void main(String[] args) {
           JFileChooser fc = new JFileChooser();        // para seleccionar archivo
           fc.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv")); // filtrar solo archivos CSV
           if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
               return;
           }
        File archivo = fc.getSelectedFile();
        if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {   // si no se selecciona un archivo, salir
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {   // empezar a leer el archivo línea por línea desde la primera línea
                if (linea.trim().isEmpty()) continue;   // si la línea está vacía, continuar con la siguiente línea

                String[] columnas = linea.split(";");   
                String plate = columnas[0].trim();       // columna A (plate)

                System.out.println("Plate: " + plate);
                for (int i = 0; i < plate.length(); i++) {  // aqui se convierte en ascii
                    char c = plate.charAt(i);
                    int ascii = (int) c;
                    System.out.println("  '" + c + "' -> " + ascii);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }
    }
}
