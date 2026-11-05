package com.mycompany.taller_8x_tablas_hash.structures.TablaHash;

import com.mycompany.taller_8x_tablas_hash.structures.Lista;
import com.mycompany.taller_8x_tablas_hash.structures.Nodo;

public class OperacionesHash implements FuncionHash {

    private static final double A = (Math.sqrt(5) - 1) / 2; 
    private final int m; 
    private FuncionHash funcion; 

    // para manejo de colisiones segun tipo de función hash
    private Lista[] slotsDivision;
    private Lista[] slotsMultiplicacion;

    // m = 256 por defecto, y sin función hash activa
    public OperacionesHash() {
        this(256, null);
    }

    public OperacionesHash(int m) {
        this(m, null);
    }

    public OperacionesHash(int m, FuncionHash funcion) {
        this.m = m;
        this.funcion = funcion;
        this.slotsDivision = new Lista[m];
        this.slotsMultiplicacion = new Lista[m];

        // un slot para cada tipo de colisión
        for (int i = 0; i < m; i++) {
            this.slotsDivision[i] = new Lista();
            this.slotsMultiplicacion[i] = new Lista();
        }
    }

    // Método de la interfaz FuncionHash implementado
    @Override
    public int hash(int k, int m) {
        if (this.funcion != null) {
            return this.funcion.hash(k, m);
        }
        return hashDivision(k, m); // Por defecto usa división
    }

    // Función Hash por División
    public int hashDivision(int k, int m) {
        return Math.abs(k % m);
    }

    // Función Hash por Multiplicación
    public int hashMultiplicacion(int k, int m) {
        double Amod1 = k * A - Math.floor(k * A);
        return (int) Math.floor(m * Amod1);
    }

    // verificar colisiones
    public boolean verificarColisionDivision(int posDiv) {
        return !slotsDivision[posDiv].estaVacia();
    }

    public boolean verificarColisionMultiplicacion(int posMult) {
        return !slotsMultiplicacion[posMult].estaVacia();
    }

    public void insertarElemento(int k) {
        int posDiv = hashDivision(k, this.m);
        int posMult = hashMultiplicacion(k, this.m);

        // 1. Inserción y reporte para División
        if (verificarColisionDivision(posDiv)) {
            System.out.println("[División] Colisión detectada en la posición " + posDiv + " para la llave: " + k);
        }
        slotsDivision[posDiv].adicionar(new Nodo<>(k));

        // 2. Inserción y reporte para Multiplicación
        if (verificarColisionMultiplicacion(posMult)) {
            System.out.println("[Multiplicación] Colisión detectada en la posición " + posMult + " para la llave: " + k);
        }
        slotsMultiplicacion[posMult].adicionar(new Nodo<>(k));
    }

    public void setFuncion(FuncionHash f) { 
        this.funcion = f; 
    }

    public FuncionHash getFuncion() {
        return funcion;
    }

    public int getM() { 
        return m; 
    }

    public Lista[] getSlotsDivision() {
        return slotsDivision;
    }

    public Lista[] getSlotsMultiplicacion() {
        return slotsMultiplicacion;
    }
}
