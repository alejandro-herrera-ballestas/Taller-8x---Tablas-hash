package com.mycompany.taller_8x_tablas_hash.structures.TablaHash;
import com.mycompany.taller_8x_tablas_hash.models.leerCSVascii;
/**
 *
 * @author ismae
 */
public class AppPrueba {
    public static void main(String[] args) {
        int k = leerCSVascii.calcularLlave("05-9165610");
        System.out.println("Llave calculada: " + k);       
    }
}
