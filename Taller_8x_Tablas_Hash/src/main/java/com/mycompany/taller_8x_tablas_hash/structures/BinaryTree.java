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
        
        public  boolean estaVacia(){
            if(nodos == 0)
                return true;
            else    
                return false;
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
	
        public Object buscar(long k, Node raiz){
            if( estaVacia() ) return null;
                
                
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
        
    private boolean eliminado;

    public boolean eliminar(long k, Node raiz){
        if( estaVacia() ) return false;
        eliminado = false;
        this.raiz = eliminarNodo(k, this.raiz);
        return eliminado;
    }

    private Node eliminarNodo(long k, Node r){
        if (r == null) return null;
        if (k < r.getDato()) {
            r.izquierdo = eliminarNodo(k, r.izquierdo);
        } else if (k > r.getDato()) {
            r.derecho = eliminarNodo(k, r.derecho);
        } else {
            eliminado = true;
            nodos--;
            if (r.izquierdo == null) return r.derecho;
            if (r.derecho == null)   return r.izquierdo;
            Node s = r.derecho;
            while (s.izquierdo != null) s = s.izquierdo;
            r.setDato(s.getDato());
            r.derecho = eliminarNodo(s.getDato(), r.derecho);
            nodos++;
        }
        return r;
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
        
            // Metodo para imprimir el árbol de forma en la interfaz gráfica
        public String aStringVisual(java.util.Map<Long, String> mapaPlacas) {
                   if (raiz == null) return "";
                      StringBuilder sb = new StringBuilder();
             sb.append("      (R) ── [ ").append(mapaPlacas.get(raiz.getDato())).append(" ]\n");
                    if (raiz.izquierdo != null || raiz.derecho != null) {
            String prefijoHijos = "             ";
                    if (raiz.izquierdo != null) {
                boolean esUltimo = (raiz.derecho == null);
                construirStringArbol(raiz.izquierdo, sb, prefijoHijos, esUltimo, "(I)", mapaPlacas);
            }
            if (raiz.derecho != null) {
                construirStringArbol(raiz.derecho, sb, prefijoHijos, true, "(D)", mapaPlacas);
            }
          }
        return sb.toString();
     }

       private void construirStringArbol(Node nodo, StringBuilder sb, String prefijo, boolean esUltimo, String tipoNodo, java.util.Map<Long, String> mapaPlacas) {
            if (nodo != null) {
            sb.append(prefijo);
            sb.append(esUltimo ? "└── " : "├── ");
            sb.append(tipoNodo).append(" ── [ ").append(mapaPlacas.get(nodo.getDato())).append(" ]\n");

            String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");

            if (nodo.izquierdo != null || nodo.derecho != null) {
                if (nodo.izquierdo != null) {
                    boolean ultimoIzq = (nodo.derecho == null);
                    construirStringArbol(nodo.izquierdo, sb, nuevoPrefijo, ultimoIzq, "(I)", mapaPlacas);
                }
                if (nodo.derecho != null) {
                    construirStringArbol(nodo.derecho, sb, nuevoPrefijo, true, "(D)", mapaPlacas);
                }
             }
         }
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