package com.mycompany.taller_8x_tablas_hash.structures.TablaHash;
/**
 *
 * @author ismae
 */
public class AppPrueba {
    public static void main(String[] args) {
        int k = TablaHash.calcularLlave("05-9165610");
        System.out.println(k);                                        
        System.out.println(new HashMultiplicacion().hash(k, 256));    
        System.out.println(new HashDivision().hash(k, 256));          
    }
}
