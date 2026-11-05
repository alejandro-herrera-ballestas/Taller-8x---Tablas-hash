package com.mycompany.taller_8x_tablas_hash.structures.TablaHash;
import com.mycompany.taller_8x_tablas_hash.structures.Lista;

public class OperacionesHash {
    private final int m;
    private FuncionHash funcion;
    private Lista[] slots;
    
    public OperacionesHash() {
        this.m = 256;
        this.slots = new Lista[256];
        for (int i = 0; i < 256; i++) {
            this.slots[i] = new Lista(); 
        }
    }

    public OperacionesHash(int m, FuncionHash funcion) {
        this.m = m;
        this.funcion = funcion;
        this.slots = new Lista[m];
        for (int i = 0; i < m; i++) {
            slots[i] = new Lista();
        }
    }

    public void setFuncion(FuncionHash f) { this.funcion = f; }
    public int getM() { return m; }
    public Lista[] getSlots() {
        return slots;
    }
}
