package com.mycompany.taller_8x_tablas_hash.structures.TablaHash;
import com.mycompany.taller_8x_tablas_hash.structures.Lista;

public class OperacionesHash {

    private static final double A = (Math.sqrt(5) - 1) / 2;
    private final int m;
    private FuncionHash funcion;
    private Lista[] slots;

    public OperacionesHash() {
        this.m = 256;
        this.slots = new Lista[256];
        for (int i = 0; i < 256; i++) {
            this.slots[i] = new Lista();
        }
        this.funcion = this::hashDivision;  // Inicializa la función hash por defecto a hashDivision para evitar NullPointerException
    }

    public OperacionesHash(int m, FuncionHash funcion) {
        this.m = m;
        this.funcion = funcion;
        this.slots = new Lista[m];
        for (int i = 0; i < m; i++) {
            slots[i] = new Lista();
        }
    }

    // Utiliza el atributo 'm' de la clase
    public int hashDivision(int k, int m) {
        return Math.abs(k % m);
    }

    public int hashMultiplicacion(int k, int m) {
        double Amod1 = k * A - Math.floor(k * A);
        return (int) Math.floor(m * Amod1);
    }

    // Método principal para calcular la posición en la tabla usando la función activa
    public int calcularHash(int k) {
        if (funcion == null) {
            throw new IllegalStateException("La función Hash no ha sido inicializada.");
        }
        return funcion.hash(k, this.m);
    }

    public static int stringToAsciiKey(String texto) {
    int k = 0;
    for (int i = 0; i < texto.length(); i++) {
        k += (int) texto.charAt(i); // Suma los códigos ASCII de cada carácter
    }
    return k;
}

    public void setFuncion(FuncionHash f) { this.funcion = f; }
    public int getM() { return m; }
    public Lista[] getSlots() { return slots; }
}
