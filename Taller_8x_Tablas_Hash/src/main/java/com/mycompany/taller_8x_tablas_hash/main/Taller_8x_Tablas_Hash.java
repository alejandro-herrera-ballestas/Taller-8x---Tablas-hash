package com.mycompany.taller_8x_tablas_hash.main;

import com.mycompany.taller_8x_tablas_hash.wiew.VentanaPrincipal;
import com.mycompany.taller_8x_tablas_hash.structures.TablaHash.OperacionesHash; 
import com.mycompany.taller_8x_tablas_hash.controller.controllers;

public class Taller_8x_Tablas_Hash {

    public static void main(String[] args) {
        VentanaPrincipal vista = new VentanaPrincipal();
        OperacionesHash modelo = new OperacionesHash(256); // 256 porque así lo pide el taller
        
        controllers controlador = new controllers(vista, modelo);
        
        vista.setVisible(true);    
    }
}
