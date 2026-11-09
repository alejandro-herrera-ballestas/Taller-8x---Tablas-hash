package com.mycompany.taller_8x_tablas_hash.structures;

public class Lista <T>{
	Nodo<T> primerNodo;
	int tamaño = 0;
	public Lista(){
		limpiar();
	}

	private void limpiar(){
		primerNodo = null;
		tamaño = 0;
	}

	public boolean estaVacia(){
		if (tamaño == 0)
			return true;
		else
			return false;
	}
	
	public boolean adicionar(Nodo<T> Nodo){
		//ADICIONAR AL COMIENZO DE LA LISTA
		Nodo<T> nuevoNodo = Nodo;
		if(estaVacia())
			primerNodo = nuevoNodo;
		else{
			nuevoNodo.sig=primerNodo;
			primerNodo = nuevoNodo;
		}
		tamaño=tamaño+1;
		return true;
	}
	
	/*
	 * adicionarFinal
	 * 
	 * adiciona un nodo al final de la lista
	 * 
	 */
	
	public boolean adicionarFinal(Nodo<T> Nodo){
		
		Nodo<T> nuevoNodo = Nodo;
		Nodo<T> temp = null;
		if(estaVacia())
			primerNodo = nuevoNodo;
		else{
			temp = primerNodo;
			while(temp.sig!=null)
				temp = temp.sig;
			temp.sig = nuevoNodo;
		}
		tamaño=tamaño+1;
		return true;
	}
	
	/*
	 * adicionarEntreNodos
	 * 
	 * adiciona un nodo entre nodos en order no decreciente
	 * 
	 */
	//Modificamos el metodo eliminar de la lista, creamos uno que cumpla las condiciones para el hash
        public boolean eliminar(String placa) {
        if (estaVacia()) {
            return false; 
        }

        // Convertimos el dato a String para asegurarnos de que la comparación sea correcta
        if (primerNodo.getDato().toString().equals(placa)) {
            primerNodo = primerNodo.sig; // Desconecta la cabeza
            tamaño--; // Actualizamos el tamaño de la lista
            return true;
        }

        //El elemento está en medio o al final de la lista (colisión)
        Nodo<T> temp = primerNodo;
        while (temp.sig != null) {
            if (temp.sig.getDato().toString().equals(placa)) {
                temp.sig = temp.sig.sig; 
                tamaño--; 
                return true;
            }
            temp = temp.sig;
        }

        return false; 
    }
        
        
	public boolean adicionarEntreNodos(Nodo<T> Nodo){
		Nodo<T> nuevoNodo = Nodo;
		Nodo<T> temp = null;
		Nodo<T> anterior = null;
		if(estaVacia())
			primerNodo = nuevoNodo;
		else{
			temp = primerNodo;
			anterior = temp;
			boolean band=false;
			//CASO 1 : Lista tiene un elemento
			if(tamaño==1){
				if(Integer.parseInt(temp.getDato().toString()) < Integer.parseInt(nuevoNodo.getDato().toString())){
				//if(temp.getDato() < Nodo.getDato()){
                                primerNodo.sig=nuevoNodo;
				}
				else{
					nuevoNodo.sig = primerNodo;
					primerNodo=nuevoNodo;
				}
			}
			else{//LISTA TIENE MAS DE UN ELEMENTO
				
				while(temp!=null && 
						  Integer.parseInt(temp.getDato().toString()) < 
						  Integer.parseInt(nuevoNodo.getDato().toString())){
						  anterior=temp;
						  temp = temp.sig;
						  band=true;
						}
				    if(band){//
				    	nuevoNodo.sig =anterior.sig;
				    	anterior.sig = nuevoNodo;
				    }
				    else{//El valor del nuevo nodo es menor
				    	nuevoNodo.sig=temp;
				    	primerNodo=nuevoNodo;
				    	
				    }
			}
		}
		tamaño=tamaño+1;
		return true;
	}
	
	public void imprimir(){
		if(!estaVacia()){
			Nodo<T> temp = primerNodo;
			while(temp!=null){
				System.out.println(temp.getDato());
				temp=temp.sig;
			}
		}
		else
			System.out.println("La Lista esta vacia");
	}

public Object buscar(int k) {
        if (estaVacia()) {
        return null;
    }
    
    Nodo actual = primerNodo; 
    while (actual != null) {
        // si el dato del nodo actual es igual a k, retornamos el dato
        if (actual.getDato() != null && actual.getDato().equals(k)) {
            return actual.getDato(); 
        }
        actual = actual.sig;	// avanzamos al siguiente nodo
    }
    return null; // No se encontró en la lista
	}

	// convertir a csv
	public String aCSV() {
		if (estaVacia()) {	
			return "";
		}
		
		// temp es un nodo temporal para recorrer la lista
		// se empieza con el primer nodo
		StringBuilder sb = new StringBuilder();	
		Nodo<T> temp = primerNodo;	
		
		// mientras temp no sea nulo, agregamos el dato del nodo a la cadena CSV
    	while (temp != null) {	
        	sb.append(";").append(temp.getDato()); 
        	temp = temp.sig; 
    	}	

    	return sb.toString();
	}
}