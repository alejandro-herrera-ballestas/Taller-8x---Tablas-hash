package com.mycompany.taller_8x_tablas_hash.structures.TablaHash;

public class Node{
	public long datos;
	public Node izquierdo;
	public Node derecho;
	public Node(long dato, Node izq, Node der){
		this.datos=dato;
		this.izquierdo=izq;
		this.derecho=der;
	}
	public Node(long dato){
		this.datos=dato;
		this.izquierdo=null;
		this.derecho=null;
	}
	public long getDato(){
		return this.datos;
	}
}
