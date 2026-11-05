package com.mycompany.taller_8x_tablas_hash.structures.TablaHash;
/**
 *
 * @author ismae
 */
public class HashMultiplicacion implements FuncionHash{
        private static final double A = ( Math.sqrt(5) - 1) / 2;
        
    @Override
    public int hash(int k, int m) {
        double Amod1 = k * A - Math.floor( k * A );
        int h = (int) Math.floor(m * Amod1);
        return h;
    }
}
