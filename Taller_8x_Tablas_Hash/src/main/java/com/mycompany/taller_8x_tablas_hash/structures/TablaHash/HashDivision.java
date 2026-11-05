package com.mycompany.taller_8x_tablas_hash.structures.TablaHash;
/**
 *
 * @author ismae
 */
public class HashDivision implements FuncionHash{
    @Override
       public int hash(int k, int m){
            return k % m;
       }    
}
