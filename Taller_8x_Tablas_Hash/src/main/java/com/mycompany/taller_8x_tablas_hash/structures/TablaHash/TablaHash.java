package com.mycompany.taller_8x_tablas_hash.structures.TablaHash;

import co.edu.unicartagena.list.Lista;
import co.edu.unicartagena.list.Nodo;

/**
 *
 * @author ismae
 */
public class TablaHash {
    private final int m;
    private FuncionHash funcion;
    private Lista<Nodo>[] slots;

    public TablaHash(int m, FuncionHash funcion) {
        this.m = m;
        this.funcion = funcion;
        this.slots = new Lista[m];
    }

    public static int calcularLlave(String placa) {
        int k = 0;
        for (int i = 0; i < placa.length(); i++) {
            k = k * 2 + placa.charAt(i);   
        }
        return k;
    }

    public void setFuncion(FuncionHash f) { this.funcion = f; }
    public int getM() { return m; }


}
