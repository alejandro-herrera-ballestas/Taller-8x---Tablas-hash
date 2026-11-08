package com.mycompany.taller_8x_tablas_hash.structures;

public class BinaryTree {
	public Node raiz;
	public Node izquierdo;
	public Node derecho;
	public int altura=0;
	
	public long numeroMenor = 1001;
	public long numeroMayor = 0;
	
	public int nivelNumeroMenor=0;
	public int nivelNumeroMayor=0;
	
	
	public int nivel=0;
	public int nodos=0;
	
	public BinaryTree(){
            Limpiar();
	}
        
        public void Limpiar(){
            raiz = null;
            altura = 0;
            numeroMenor = 0;
            numeroMayor = 0;
            nivelNumeroMenor=0;
            nivelNumeroMayor=0;
            nivel=0;
            nodos=0;
        }
        
        
        	     
	public void adicionar (long valor, Node raiz, int nivel){
		if (raiz==null){
			this.raiz=new Node(valor);
			nodos = nodos + 1;
			updateAltura(this.altura,nivel);
			updateNivelNumeroMayor(valor,this.numeroMayor,nivel);
			updateNivelNumeroMenor(valor,this.numeroMenor,nivel);
		}
		else{
			if (valor<raiz.getDato()){
				if(raiz.izquierdo==null){
					raiz.izquierdo=new Node(valor);
					nodos = nodos + 1;
					updateAltura(this.altura,nivel+1);
					updateNivelNumeroMenor(valor,this.numeroMenor,nivel+1);
				}
				else{
					adicionar (valor,raiz.izquierdo,nivel+1);
				}	
			}
			if(valor>raiz.getDato()) {
				if(raiz.derecho==null){
					raiz.derecho=new Node(valor);
					nodos = nodos + 1;
					updateAltura(this.altura,nivel+1);
					updateNivelNumeroMayor(valor,this.numeroMayor,nivel+1);
					
				}
				else{
					adicionar (valor,raiz.derecho,nivel+1);
				}
			}
		} 
	}
	
        public static Object buscar(long k, Node raiz){
            if (raiz == null)
                return null;

            long dato = (long)(Long) raiz.getDato();

            if (k == dato) {
                return raiz.getDato();
            } else if (k < dato) {
                return buscar(k, raiz.izquierdo);
            } else if (k > dato) {
                return buscar(k, raiz.derecho);
            }

            return null;
        }
        
        public boolean eliminar(long k, Node raiz){
            if (raiz == null)
                return false;


            if (k == raiz.getDato()) {
                System.out.println("Encontrado");
                
                Node temporal = null;
                // Sin hijos o con un solo hijo
                if(raiz.izquierdo == null ){
                    
                    // Debugs
                    /*System.out.println("Izquierdo");
                    System.out.println("Raiz:" + raiz.getDato());
                    System.out.println("Raiz Derecho1: " + raiz.derecho.getDato());
                    System.out.println("Raiz Derecho2: " + raiz.derecho.izquierdo.getDato());
                    System.out.println("Raiz Izquierdo: " + raiz.izquierdo.getDato());
                    */
                    
                    temporal = raiz.derecho;
                    
                    System.out.println("Valor Temporal: " + temporal);
                    
                    raiz.setDato(temporal.getDato());
                    
                    raiz.derecho = null;
                 
                    raiz = temporal;
                    
                    
                    
                }else if(raiz.derecho == null){
                    // Debugs
                    /*
                    System.out.println("Derecho");
                    System.out.println("Raiz:" + raiz.getDato());
                    System.out.println("Raiz Izquierdo: " + raiz.izquierdo.getDato());
                    */
                    temporal = raiz.izquierdo;
                    
                    raiz.setDato(temporal.getDato());
                  
                    raiz.izquierdo = null;
                    raiz = temporal;
                    
                }
                // Con dos hijos
                else{
                    /*System.out.println("Hijos");
                    */
                    
                    temporal = inorderEliminar(raiz.derecho);
                    
                    raiz.setDato(temporal.getDato());

                    raiz = temporal;
                }
                
                return true;
            } else if (k < raiz.getDato()) {
                return (boolean) eliminar(k, raiz.izquierdo);
            } else if (k > raiz.getDato()) {
                return (boolean) eliminar(k, raiz.derecho);
            }

            return false;
        }
        
        public Node inorderEliminar(Node raiz){
                Node temp = null;
		if(raiz!=null){
                    if(raiz.izquierdo != null){
                        inorderEliminar(raiz.izquierdo);
                }else
                    temp = raiz;
                    raiz = null;
                    return temp;
		}
            return null;
	}
        
        
	public int getNumeroNodos(){
		return this.nodos;
	}
	
	public int getAltura(){
		return this.altura;
	}
	
	public int getNivelNumeroMayor(){
		return this.nivelNumeroMayor;
	}
	
	public int getNivelNumeroMenor(){
		return this.nivelNumeroMenor;
	}
	
	
	public void updateAltura(int mayor, int nivel){
		if(mayor<nivel){
			this.altura=nivel;
		}
	}
	
	public void updateNivelNumeroMayor(long valor, long numeroMayor, int nivel){
		if(numeroMayor<valor ){
			this.numeroMayor=valor;
			this.nivelNumeroMayor = nivel;
		}
		
			
	}
	
	public void updateNivelNumeroMenor(long valor, long numeroMenor, int nivel){
		if(numeroMenor>valor){
			this.numeroMenor=valor;
			this.nivelNumeroMenor = nivel;
		}
	}
	
	public void inorder(Node raiz){
		if(raiz!=null){
			inorder(raiz.izquierdo);
			System.out.print(raiz.getDato()+" - ");
			inorder(raiz.derecho);
		}
	}
	
	public void postorder(Node raiz){
		if(raiz!=null){
			postorder(raiz.izquierdo);
			postorder(raiz.derecho);
			System.out.print(raiz.getDato()+" - ");
			
		}
	}
	
	public void preorder(Node raiz){
		if(raiz!=null){
			System.out.print(raiz.getDato()+" - ");
			preorder(raiz.izquierdo);
			preorder(raiz.derecho);
		}
	}
	
	public Node getRaiz(){
		return this.raiz;
	}
	
}