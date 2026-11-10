package com.mycompany.taller_8x_tablas_hash.structures;
import com.mycompany.taller_8x_tablas_hash.structures.TablaHash.OperacionesHash;

public class OperacionesBinaryTree {
    private OperacionesHash tabla = new OperacionesHash();
    private BinaryTree tree = new BinaryTree();
    
    public OperacionesBinaryTree() {
    }
    
    public boolean insertarDivision(String placa){
        int k = calcularLlave(placa);
        if( buscar(k) ){
            System.out.println("Valor de llave ya existente en la tabla\nNota: No se agrego elemento al arbol");
            return false;
        }
        
        long slot = tabla.hashDivision(k, 256);
        
        tree.adicionar(slot, tree.getRaiz() , 1);
        tabla.insertarElemento(k, placa);
        
        return true;
    }
    
    public boolean insertarMultiplicacion(String placa){
        int k = calcularLlave(placa);
        if( buscar(k) ){
            System.out.println("Valor de llave ya existente en la tabla\nNota: No se agrego elemento al arbol");
            return false;
        }
        
        long slot = tabla.hashMultiplicacion(k, 256);
        
        tree.adicionar(slot, tree.getRaiz() , 1);
        tabla.insertarElemento(k, placa);
        
        return true;
    }
    
    //busqueda solo con el valor del slot ya previamente generado
    public boolean buscarSlot(long k){
        //long resultado = (long) (Long) tree.buscar( k, tree.getRaiz() );
        
        if( buscar(k) ){
            return true;
        }
            return false;
    }
    
    
    public boolean buscarDivision(String placa){
        int k = calcularLlave(placa);
        long slot = tabla.hashDivision(k, 256);
        
        if( buscar(k) ){
            System.out.println("Resgistro con placa [" + placa + "] encontrado en el slot: " + slot);
            return true;
        }
        
        return false;
    }
    
    public boolean buscarMultiplicacion(String placa){
        int k = calcularLlave(placa);
        long slot = tabla.hashMultiplicacion(k, 256);
        
        if( buscar(k) ){
            System.out.println("Resgistro con placa [" + placa + "] encontrado en el slot: " + slot);
            return true;
        }
        
        return false;
    }
    
    // eliminacion por ingreso de slot
    public boolean eliminar(long k){
        //se valida que exista primero
        if(!buscar(k)){
            System.out.println("Registro no existente");
            return false;
        }
        
        if( tree.eliminar(k, tree.getRaiz() ) ){
            System.out.println("Slot: " + k + " eliminado");
            return true;
        }       
        return false;
    }
    
    
    public boolean eliminarDivision(String placa){
        int k = calcularLlave(placa);
        long slot = tabla.hashDivision(k, 256);
        
        if( !buscar(slot) ){
            System.out.println("Registro no existente");
            return false;
        }
        
        if( tree.eliminar(slot, tree.getRaiz()) ){
            tabla.borrarElemento(k, placa);
            System.out.println("Registro con placa [" + placa + "] encontrado y eliminado");
            return true;
        } 
       
        return false;
    }
    
    public boolean eliminarMultiplicacion(String placa){
        int k = calcularLlave(placa);
        long slot = tabla.hashMultiplicacion(k, 256);
        
        if( !buscar(slot) ){
            System.out.println("Registro no existente");
            return false;
        }
        
        if( tree.eliminar(slot, tree.getRaiz()) ){
            tabla.borrarElemento(k, placa);
            System.out.println("Registro con placa [" + placa + "] encontrado y eliminado");
            return true;
        } 
       
        return false;
    }
    
    // Calcula la llave entera k a partir de un texto sumando los códigos ASCII de cada carácter
    public static int calcularLlave(String texto) {
        int k = 0;
        for (int i = 0; i < texto.length(); i++) {
            k =(k * 2) + (int) texto.charAt(i);
        }
        return k;
    }
    
    // convertir tree.buscar en boolean
    public boolean buscar(long k){
        long resultado = (long) (Long) tree.buscar(k, tree.getRaiz());
        
        if(resultado == k){
            return true;
        }    
        return false;
    }
    
}
