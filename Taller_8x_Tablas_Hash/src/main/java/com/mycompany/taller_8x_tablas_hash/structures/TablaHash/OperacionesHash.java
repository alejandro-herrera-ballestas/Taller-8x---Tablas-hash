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

    // Contadores de colisiones
    private int contadorColisionesDivision;
    private int contadorColisionesMultiplicacion;
    
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
        this.contadorColisionesDivision = 0;
        this.contadorColisionesMultiplicacion = 0;

        // Cada posición inicia con su propia lista enlazada vacía
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
        return hashDivision(k, m);
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

    // verificar colisiones, true si hay colisión, false si no hay colisión
    public boolean verificarColisionDivision(int posDiv) {
        return !slotsDivision[posDiv].estaVacia();
    }
    public boolean verificarColisionMultiplicacion(int posMult) {
        return !slotsMultiplicacion[posMult].estaVacia();
    }

    public void insertarElemento(int k) {
        int posDiv = hashDivision(k, this.m);
        int posMult = hashMultiplicacion(k, this.m);

        // verificar colisiones y actualizar contadores
        if (verificarColisionDivision(posDiv)) {
            contadorColisionesDivision++; 
            System.out.println("[División] ¡COLISIÓN DETECTADA! Slot: " + posDiv + 
                               " | Llave: " + k + 
                               " | Total colisiones Div: " + contadorColisionesDivision);
        } else {
            System.out.println("[División] Sin colisión. Elemento insertado en slot libre: " + posDiv);
        }
        // adicionamos al final de la lista del slot posDiv
        slotsDivision[posDiv].adicionar(new Nodo<>(k));

        if (verificarColisionMultiplicacion(posMult)) {
            contadorColisionesMultiplicacion++; 
            System.out.println("[Multiplicación] ¡COLISIÓN DETECTADA! Slot: " + posMult + 
                               " | Llave: " + k + 
                               " | Total colisiones Mult: " + contadorColisionesMultiplicacion);
        } else {
            System.out.println("[Multiplicación] Sin colisión. Elemento insertado en slot libre: " + posMult);
        }
        // adicionamos al final de la lista del slot posMult
        slotsMultiplicacion[posMult].adicionar(new Nodo<>(k));
    
    }

    public void reiniciarContadores() {
        this.contadorColisionesDivision = 0;
        this.contadorColisionesMultiplicacion = 0;
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
