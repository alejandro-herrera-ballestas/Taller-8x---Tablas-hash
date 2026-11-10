package com.mycompany.taller_8x_tablas_hash.structures.TablaHash;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

    // función hash que delega a la función hash seleccionada o usa la función por defecto (división)
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

    // insertar elemento en la tabla hash y manejar colisiones
    public void insertarElemento(int k, String placa) {
        int posDiv = hashDivision(k, this.m);   
        int posMult = hashMultiplicacion(k, this.m);    

        // div
        if (verificarColisionDivision(posDiv)) {
            contadorColisionesDivision++; 
        } 
        slotsDivision[posDiv].adicionarFinal(new Nodo<>(placa));

        // mul
        if (verificarColisionMultiplicacion(posMult)) {
            contadorColisionesMultiplicacion++; 
        } 
        slotsMultiplicacion[posMult].adicionarFinal(new Nodo<>(placa));
    }
    // borrar elemento de la tabla hash y manejar colisiones
    public void borrarElemento(int k,String placa) {
        int posDiv = hashDivision(k, this.m);
        int posMult = hashMultiplicacion(k, this.m);

        // eliminar de la lista del slot posDiv
        if (slotsDivision[posDiv].eliminar(placa)) {
            System.out.println("[División] Elemento eliminado: " + placa + " del slot: " + posDiv);
        } else {
            System.out.println("[División] Elemento no encontrado: " + placa + " en el slot: " + posDiv);
        }
        verificarColisionDivision(posDiv); // Verifica si aún hay colisiones en el slot posDiv
        if (!verificarColisionDivision(posDiv)) {
            System.out.println("[División] No hay más colisiones en el slot: " + posDiv);
        }else {
            System.out.println("[División] Aún hay colisiones en el slot: " + posDiv);
        }
        contadorColisionesDivision = Math.max(0, contadorColisionesDivision - 1); // Decrementa el contador de colisiones

        // eliminar de la lista del slot posMult
        if (slotsMultiplicacion[posMult].eliminar(placa)) {
            System.out.println("[Multiplicación] Elemento eliminado: " + placa + " del slot: " + posMult);
        } else {
            System.out.println("[Multiplicación] Elemento no encontrado: " + placa + " en el slot: " + posMult);
        }
        verificarColisionMultiplicacion(posMult); // Verifica si aún hay colisiones en el slot posMult
        if (!verificarColisionMultiplicacion(posMult)) {
            System.out.println("[Multiplicación] No hay más colisiones en el slot: " + posMult);
        }else {
            System.out.println("[Multiplicación] Aún hay colisiones en el slot: " + posMult);
        }
        contadorColisionesMultiplicacion = Math.max(0, contadorColisionesMultiplicacion - 1); // Decrementa el contador de colisiones
    }

    public int[] buscarElemento(int k, String placa) {
        int posDiv = hashDivision(k, this.m);
        int posMult = hashMultiplicacion(k, this.m);
        
        Object resultadoDiv = slotsDivision[posDiv].buscar(placa);
        Object resultadoMult = slotsMultiplicacion[posMult].buscar(placa);
        
        int slotEncontradoDiv = (resultadoDiv != null) ? posDiv : -1;
        int slotEncontradoMult = (resultadoMult != null) ? posMult : -1;

        if (slotEncontradoDiv != -1) {
            System.out.println("[Division] Placa " + placa + " encontrada en el slot " + posDiv);
        } else {
            System.out.println("[Division] Placa " + placa + " NO encontrada.");
        }

        if (slotEncontradoMult != -1) {
            System.out.println("[Multiplicacion] Placa " + placa + " encontrada en el slot " + posMult);
        } else {
            System.out.println("[Multiplicacion] Placa " + placa + " NO encontrada.");
        }

        return new int[]{slotEncontradoDiv, slotEncontradoMult};
    }

    // guardar en csv, metodo generico para exportar cualquiera
    public void exportarTablaCSV(Lista[] slots, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Encabezado de columnas
            writer.write("Slot;Cabeza (Placa/Llave);Colision_1;Colision_2;Colision_N...\n");

            for (int i = 0; i < m; i++) {
                // slots[i].aCSV() muestra la lista completa del slot i en formato CSV, es decir
                // muestra la cabeza y todas las colisiones en ese slot
                writer.write("Slot " + i + slots[i].aCSV());
                writer.newLine();
            }

            System.out.println("Archivo CSV generado exitosamente: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("Error al exportar la tabla a CSV: " + e.getMessage());
        }
    }

    // exportar tabla de división a CSV
    public void exportarTablaDivisionCSV(String nombreArchivo) {
        exportarTablaCSV(slotsDivision, nombreArchivo);
    }

    // exportar tabla de multiplicación a CSV
    public void exportarTablaMultiplicacionCSV(String nombreArchivo) {
        exportarTablaCSV(slotsMultiplicacion, nombreArchivo);
    }

    // mostrar el total de colisiones para cada función hash
    public int getContadorColisionesDivision() { 
        return contadorColisionesDivision;
    }

    public int getContadorColisionesMultiplicacion() {
        return contadorColisionesMultiplicacion;
    }
    // hasta aqui

    // reiniciar el contador de colisiones para cada función hash
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
