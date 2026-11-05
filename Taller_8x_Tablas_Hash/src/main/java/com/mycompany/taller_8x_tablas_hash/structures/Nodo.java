package com.mycompany.taller_8x_tablas_hash.structures;

public class Nodo<T> {
	T dato;
	Nodo<T> sig;
	public Nodo(T dato){
		this.dato=dato;
		this.sig=null;
	}
	public Nodo(T dato, Nodo<T> sig){
		this.dato=dato;
		this.sig=sig;
	}
	public T getDato(){
		return this.dato;
	}
}