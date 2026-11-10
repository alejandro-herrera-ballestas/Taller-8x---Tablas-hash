package com.mycompany.taller_8x_tablas_hash.main;
import java.io.PrintStream;
import com.mycompany.taller_8x_tablas_hash.wiew.VentanaPrincipal;
import com.mycompany.taller_8x_tablas_hash.structures.TablaHash.OperacionesHash; 
import com.mycompany.taller_8x_tablas_hash.controller.controllers;

public class Taller_8x_Tablas_Hash {

    public static void main(String[] args) {
        
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            System.out.println("Error configurando UTF-8");
        }
        
        VentanaPrincipal vista = new VentanaPrincipal();
        OperacionesHash modelo = new OperacionesHash(256); // 256 porque así lo pide el taller
        
        controllers controlador = new controllers(vista, modelo);
        
        vista.setVisible(true);    
    }
}
