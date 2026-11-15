package com.mycompany.taller_8x_tablas_hash.models;

import com.mycompany.taller_8x_tablas_hash.structures.BinaryTree;
import com.mycompany.taller_8x_tablas_hash.structures.Lista;
import com.mycompany.taller_8x_tablas_hash.structures.Nodo;
import com.mycompany.taller_8x_tablas_hash.structures.TablaHash.OperacionesHash;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;


public class ComparadorRendimiento {

    private static final int M = 256;           // tamano de la tabla (lo pide el taller)
    private static final int CALENTAMIENTO = 500;
    private static final int RONDAS = 1500;
    private static final int PERMUTACIONES = 16; // ordenes aleatorios precalculados

    /** Ejecuta la comparacion, la imprime por consola y devuelve el mismo reporte como texto. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String comparar(String rutaCSV) {
        String[] placas = leerPlacas(rutaCSV);
        if (placas == null || placas.length == 0) return "";
        int n = placas.length;

        OperacionesHash h = new OperacionesHash(M); // solo para sus funciones hash
        int[] llave = new int[n];
        int[] slotDiv = new int[n];
        int[] slotMul = new int[n];
        for (int i = 0; i < n; i++) {
            llave[i] = leerCSVascii.calcularLlave(placas[i]);
            slotDiv[i] = h.hashDivision(llave[i], M);
            slotMul[i] = h.hashMultiplicacion(llave[i], M);
        }

        // Estructuras (listas propias, independientes de la tabla de la interfaz)
        Lista[] listaDiv = new Lista[M], listaMul = new Lista[M];
        BinaryTree[] arbDiv = new BinaryTree[M], arbMul = new BinaryTree[M];
        for (int s = 0; s < M; s++) {
            listaDiv[s] = new Lista();
            listaMul[s] = new Lista();
            arbDiv[s] = new BinaryTree();
            arbMul[s] = new BinaryTree();
        }
        BinaryTree arbolUnico = new BinaryTree();

        // Estado inicial: TODOS los registros cargados en todas las estructuras
        for (int i = 0; i < n; i++) {
            listaDiv[slotDiv[i]].adicionarFinal(new Nodo<>(placas[i]));
            listaMul[slotMul[i]].adicionarFinal(new Nodo<>(placas[i]));
            arbDiv[slotDiv[i]].adicionar(llave[i], arbDiv[slotDiv[i]].getRaiz(), 1);
            arbMul[slotMul[i]].adicionar(llave[i], arbMul[slotMul[i]].getRaiz(), 1);
            arbolUnico.adicionar(llave[i], arbolUnico.getRaiz(), 1);
        }

        // Ordenes aleatorios precalculados (fuera del cronometro)
        int[][] orden = new int[PERMUTACIONES][n];
        Random rnd = new Random(12345);
        for (int p = 0; p < PERMUTACIONES; p++) {
            for (int i = 0; i < n; i++) orden[p][i] = i;
            for (int i = n - 1; i > 0; i--) {
                int j = rnd.nextInt(i + 1);
                int t = orden[p][i]; orden[p][i] = orden[p][j]; orden[p][j] = t;
            }
        }

        final int E = 5; // estructuras: 0 listaDiv, 1 listaMul, 2 arbDiv, 3 arbMul, 4 arbolUnico
        long[][] tBorrar = new long[E][RONDAS];
        long[][] tInsertar = new long[E][RONDAS];

        for (int r = -CALENTAMIENTO; r < RONDAS; r++) {
            int[] o = orden[Math.floorMod(r, PERMUTACIONES)];
            long t0, t1;

            // ---- 0: lista + division
            t0 = System.nanoTime();
            for (int j = 0; j < n; j++) { int i = o[j]; listaDiv[slotDiv[i]].eliminar(placas[i]); }
            t1 = System.nanoTime();
            long bl0 = t1 - t0;
            t0 = System.nanoTime();
            for (int j = 0; j < n; j++) { int i = o[j]; listaDiv[slotDiv[i]].adicionarFinal(new Nodo<>(placas[i])); }
            t1 = System.nanoTime();
            long il0 = t1 - t0;

            // ---- 1: lista + multiplicacion
            t0 = System.nanoTime();
            for (int j = 0; j < n; j++) { int i = o[j]; listaMul[slotMul[i]].eliminar(placas[i]); }
            t1 = System.nanoTime();
            long bl1 = t1 - t0;
            t0 = System.nanoTime();
            for (int j = 0; j < n; j++) { int i = o[j]; listaMul[slotMul[i]].adicionarFinal(new Nodo<>(placas[i])); }
            t1 = System.nanoTime();
            long il1 = t1 - t0;

            // ---- 2: arbol por slot + division
            t0 = System.nanoTime();
            for (int j = 0; j < n; j++) { int i = o[j]; BinaryTree a = arbDiv[slotDiv[i]]; a.eliminar(llave[i], a.getRaiz()); }
            t1 = System.nanoTime();
            long ba2 = t1 - t0;
            t0 = System.nanoTime();
            for (int j = 0; j < n; j++) { int i = o[j]; BinaryTree a = arbDiv[slotDiv[i]]; a.adicionar(llave[i], a.getRaiz(), 1); }
            t1 = System.nanoTime();
            long ia2 = t1 - t0;

            // ---- 3: arbol por slot + multiplicacion
            t0 = System.nanoTime();
            for (int j = 0; j < n; j++) { int i = o[j]; BinaryTree a = arbMul[slotMul[i]]; a.eliminar(llave[i], a.getRaiz()); }
            t1 = System.nanoTime();
            long ba3 = t1 - t0;
            t0 = System.nanoTime();
            for (int j = 0; j < n; j++) { int i = o[j]; BinaryTree a = arbMul[slotMul[i]]; a.adicionar(llave[i], a.getRaiz(), 1); }
            t1 = System.nanoTime();
            long ia3 = t1 - t0;

            // ---- 4: arbol binario unico (sin hash)
            t0 = System.nanoTime();
            for (int j = 0; j < n; j++) { int i = o[j]; arbolUnico.eliminar(llave[i], arbolUnico.getRaiz()); }
            t1 = System.nanoTime();
            long ba4 = t1 - t0;
            t0 = System.nanoTime();
            for (int j = 0; j < n; j++) { int i = o[j]; arbolUnico.adicionar(llave[i], arbolUnico.getRaiz(), 1); }
            t1 = System.nanoTime();
            long ia4 = t1 - t0;

            if (r >= 0) {
                tBorrar[0][r] = bl0; tInsertar[0][r] = il0;
                tBorrar[1][r] = bl1; tInsertar[1][r] = il1;
                tBorrar[2][r] = ba2; tInsertar[2][r] = ia2;
                tBorrar[3][r] = ba3; tInsertar[3][r] = ia3;
                tBorrar[4][r] = ba4; tInsertar[4][r] = ia4;
            }
        }

        // ns por operacion = mediana del lote / n
        double[] ins = new double[E], bor = new double[E];
        for (int e = 0; e < E; e++) {
            ins[e] = mediana(tInsertar[e]) / n;
            bor[e] = mediana(tBorrar[e]) / n;
        }

        String[] nombres = {
            "Hash + Listas      (division)",
            "Hash + Listas      (multiplicacion)",
            "Hash + Arboles     (division)",
            "Hash + Arboles     (multiplicacion)",
            "Arbol binario unico (sin hash)"
        };

        StringBuilder sb = new StringBuilder();
        sb.append("\n==================== COMPARACION DE RENDIMIENTO (insertar / borrar) ====================\n");
        sb.append(String.format("m = %d | registros = %d | factor de carga = %.2f | rondas = %d (mediana, orden aleatorio)%n",
                M, n, (double) n / M, RONDAS));
        sb.append("-----------------------------------------------------------------------------------------\n");
        sb.append(String.format("%-38s | %14s | %14s%n", "Estructura", "INSERTAR (ns)", "BORRAR (ns)"));
        sb.append("-----------------------------------------------------------------------------------------\n");
        for (int e = 0; e < E; e++) {
            sb.append(String.format("%-38s | %14.1f | %14.1f%n", nombres[e], ins[e], bor[e]));
        }
        sb.append("-----------------------------------------------------------------------------------------\n");
        sb.append("Mejora (%) = (T_referencia - T_nueva) / T_referencia * 100   (positivo = la nueva es mas rapida)\n\n");
        sb.append(String.format("%-44s | %10s | %10s%n", "Comparacion", "INSERTAR", "BORRAR"));
        sb.append(String.format("%-44s | %9.1f%% | %9.1f%%%n", "Hash+Listas(div) vs Arbol unico",
                mejora(ins[4], ins[0]), mejora(bor[4], bor[0])));
        sb.append(String.format("%-44s | %9.1f%% | %9.1f%%%n", "Hash+Listas(mult) vs Arbol unico",
                mejora(ins[4], ins[1]), mejora(bor[4], bor[1])));
        sb.append(String.format("%-44s | %9.1f%% | %9.1f%%%n", "Hash+Arboles(div) vs Hash+Listas(div)",
                mejora(ins[0], ins[2]), mejora(bor[0], bor[2])));
        sb.append(String.format("%-44s | %9.1f%% | %9.1f%%%n", "Hash+Arboles(mult) vs Hash+Listas(mult)",
                mejora(ins[1], ins[3]), mejora(bor[1], bor[3])));
        sb.append("=========================================================================================\n");

        String reporte = sb.toString();
        System.out.print(reporte);
        return reporte;
    }

    // Lee la primera columna (placa) de cada linea del CSV
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

    private static double mejora(double referencia, double nueva) {
        return referencia == 0 ? 0 : (referencia - nueva) / referencia * 100.0;
    }

    private static double mediana(long[] muestras) {
        long[] c = muestras.clone();
        Arrays.sort(c);
        int mid = c.length / 2;
        return c.length % 2 == 1 ? c[mid] : (c[mid - 1] + c[mid]) / 2.0;
    }
}