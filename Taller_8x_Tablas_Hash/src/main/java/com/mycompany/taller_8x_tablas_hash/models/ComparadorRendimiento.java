package com.mycompany.taller_8x_tablas_hash.models;

import com.mycompany.taller_8x_tablas_hash.structures.BinaryTree;
import com.mycompany.taller_8x_tablas_hash.structures.Nodo;
import com.mycompany.taller_8x_tablas_hash.structures.TablaHash.OperacionesHash;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Compara tabla hash con LISTAS vs tabla hash con ARBOLES BINARIOS (m = 256),
 * midiendo insertar y borrar con las 10 primeras placas del archivo CSV.
 *
 * Se mide solo la estructura del slot (sin los println ni los contadores de
 * colisiones de OperacionesHash), porque imprimir cuesta mucho mas que insertar
 * y falsearia la comparacion.
 */
public class ComparadorRendimiento {

    private static final int M = 256;
    private static final int CUANTAS = 10;
    private static final int CALENTAMIENTO = 20000;
    private static final int REPETICIONES = 20000;

    public static void comparar(String rutaCSV) {
        String[] todas = leerPlacas(rutaCSV);
        if (todas == null || todas.length == 0) return;
        int n = Math.min(CUANTAS, todas.length);

        // Tabla con listas (se usa OperacionesHash por sus funciones hash y sus slots de Lista)
        OperacionesHash h = new OperacionesHash(M);
        // Tabla con arboles: un BinaryTree por slot, una por division y otra por multiplicacion
        BinaryTree[] arbDiv = new BinaryTree[M];
        BinaryTree[] arbMul = new BinaryTree[M];
        for (int i = 0; i < M; i++) {
            arbDiv[i] = new BinaryTree();
            arbMul[i] = new BinaryTree();
        }

        // Cargar TODOS los registros en ambas tablas (estado real de la tabla)
        for (String p : todas) {
            int k = leerCSVascii.calcularLlave(p);
            insertarLista(h, k, p);
            insertarArbol(h, arbDiv, arbMul, k);
        }

        String[] placas = new String[n];
        int[] llaves = new int[n];
        for (int i = 0; i < n; i++) {
            placas[i] = todas[i];
            llaves[i] = leerCSVascii.calcularLlave(placas[i]);
        }

        long overhead = calibrarNanoTime();

        // Calentamiento (no se registra)
        for (int r = 0; r < CALENTAMIENTO; r++) {
            for (int i = 0; i < n; i++) {
                borrarLista(h, llaves[i], placas[i]);
                insertarLista(h, llaves[i], placas[i]);
                borrarArbol(h, arbDiv, arbMul, llaves[i]);
                insertarArbol(h, arbDiv, arbMul, llaves[i]);
            }
        }

        // se guarda cada muestra para usar la MEDIANA
        long[][] borL = new long[n][REPETICIONES], insL = new long[n][REPETICIONES];
        long[][] borA = new long[n][REPETICIONES], insA = new long[n][REPETICIONES];
        for (int r = 0; r < REPETICIONES; r++) {
            for (int i = 0; i < n; i++) {
                long t0 = System.nanoTime();
                borrarLista(h, llaves[i], placas[i]);
                long t1 = System.nanoTime();
                insertarLista(h, llaves[i], placas[i]);
                long t2 = System.nanoTime();
                borrarArbol(h, arbDiv, arbMul, llaves[i]);
                long t3 = System.nanoTime();
                insertarArbol(h, arbDiv, arbMul, llaves[i]);
                long t4 = System.nanoTime();

                borL[i][r] = Math.max(0, t1 - t0 - overhead);
                insL[i][r] = Math.max(0, t2 - t1 - overhead);
                borA[i][r] = Math.max(0, t3 - t2 - overhead);
                insA[i][r] = Math.max(0, t4 - t3 - overhead);
            }
        }

  
        System.out.println();
        System.out.println("=================== COMPARACION: HASH CON LISTAS vs HASH CON ARBOLES ===================");
        System.out.println("m = " + M + " | registros cargados = " + todas.length
                + " | placas medidas = " + n + " | ciclos por placa = " + REPETICIONES);
        System.out.println("Tiempo = mediana en ns | Mejora (%) = (T_lista - T_arbol) / T_lista * 100 (positivo = el arbol es mas rapido)");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%-12s | %-26s | %-26s%n", "Placa", "INSERTAR (ns) lista/arbol/%", "BORRAR (ns) lista/arbol/%");
        System.out.println("---------------------------------------------------------------------------------------");

        double sIL = 0, sIA = 0, sBL = 0, sBA = 0;
        for (int i = 0; i < n; i++) {
            double il = mediana(insL[i]), ia = mediana(insA[i]);
            double bl = mediana(borL[i]), ba = mediana(borA[i]);
            System.out.printf("%-12s | %7.1f %7.1f %8.2f%% | %7.1f %7.1f %8.2f%%%n",
                    placas[i], il, ia, mejora(il, ia), bl, ba, mejora(bl, ba));
            sIL += il; sIA += ia; sBL += bl; sBA += ba;
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%-12s | %7.1f %7.1f %8.2f%% | %7.1f %7.1f %8.2f%%%n", "PROMEDIO",
                sIL / n, sIA / n, mejora(sIL / n, sIA / n),
                sBL / n, sBA / n, mejora(sBL / n, sBA / n));
        System.out.println("=======================================================================================");
    }

    // Lee la primera columna (placa) de cada linea del CSV, sin usar colecciones de Java
    private static String[] leerPlacas(String ruta) {
        try {
            int total = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (!linea.trim().isEmpty()) total++;
                }
            }
            String[] placas = new String[total];
            int i = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.trim().isEmpty()) continue;
                    placas[i++] = linea.split(";")[0].trim();
                }
            }
            return placas;
        } catch (Exception e) {
            System.out.println("Comparador: no se pudo leer el archivo: " + e.getMessage());
            return null;
        }
    }

    private static double mejora(double lista, double arbol) {
        return lista == 0 ? 0 : (lista - arbol) / lista * 100.0;
    }

    private static double mediana(long[] muestras) {
        long[] c = muestras.clone();
        java.util.Arrays.sort(c);
        int mid = c.length / 2;
        return c.length % 2 == 1 ? c[mid] : (c[mid - 1] + c[mid]) / 2.0;
    }

   
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void insertarLista(OperacionesHash h, int k, String placa) {
        h.getSlotsDivision()[h.hashDivision(k, M)].adicionarFinal(new Nodo<>(placa));
        h.getSlotsMultiplicacion()[h.hashMultiplicacion(k, M)].adicionarFinal(new Nodo<>(placa));
    }

    private static void borrarLista(OperacionesHash h, int k, String placa) {
        h.getSlotsDivision()[h.hashDivision(k, M)].eliminar(placa);
        h.getSlotsMultiplicacion()[h.hashMultiplicacion(k, M)].eliminar(placa);
    }

    private static void insertarArbol(OperacionesHash h, BinaryTree[] div, BinaryTree[] mul, int k) {
        BinaryTree d = div[h.hashDivision(k, M)];
        d.adicionar(k, d.getRaiz(), 1);
        BinaryTree u = mul[h.hashMultiplicacion(k, M)];
        u.adicionar(k, u.getRaiz(), 1);
    }

    private static void borrarArbol(OperacionesHash h, BinaryTree[] div, BinaryTree[] mul, int k) {
        BinaryTree d = div[h.hashDivision(k, M)];
        d.eliminar(k, d.getRaiz());
        BinaryTree u = mul[h.hashMultiplicacion(k, M)];
        u.eliminar(k, u.getRaiz());
    }

    // costo tipico (mediana) de una llamada a System.nanoTime(), para restarlo de cada medicion
    private static long calibrarNanoTime() {
        int veces = 100001;
        long[] d = new long[veces];
        for (int i = 0; i < veces; i++) {
            long a = System.nanoTime();
            d[i] = System.nanoTime() - a;
        }
        java.util.Arrays.sort(d);
        return d[veces / 2];
    }
}

