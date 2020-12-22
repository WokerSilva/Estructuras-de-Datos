import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p> Clase concreta para modelar la estructura de datos Lista</p>
 * <p>Esta clase implementa una Lista genérica, es decir que es homogénea pero
 * puede tener elementos de cualquier tipo.
 * @author Marco Silva
 * @version 2.0
 * @param <T>
 */
public class Lista<T> implements Listable<T>{

        /* Clase interna para construir la estructura */
        protected class Nodo{
        /* Referencias a los nodos anterior y siguiente */
        public Nodo anterior, siguiente;
        /* El elemento que almacena un nodo */
        public T elemento;

        /* Unico constructor de la clase */
        public Nodo(T elemento){
	    this.elemento = elemento;
        }
        public boolean equals(Nodo n){
	    Nodo n1 = cabeza;
	    Nodo n2 = null;
	    while(n1 != null && n2 != null){
		if(!n1.elemento.equals(n2.elemento))
		    return false;
		n1 = n1.siguiente;
		n2 = n2.siguiente;
	    }
	    return true;
        }
	}

        private class IteradorLista implements Iterator<T>{
        /* La lista a recorrer*/
        /* Elementos del centinela que recorre la lista*/
        private Lista<T>.Nodo siguiente;
	private Lista<T>.Nodo anterior;

        public IteradorLista(){
            start();
        }

        @Override
        public boolean hasNext() {
            return siguiente != null;
        }

        @Override
        public T next() {
            if(siguiente == null)throw new NoSuchElementException();
	    anterior = siguiente;
	    siguiente = siguiente.siguiente;
	    return anterior.elemento;
        }
	
	 /* Mueve el iterador al inicio de la lista. */
        public void start() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        public void end() {
            anterior = cola;
            siguiente = null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        } 
    }

    
    /* Atributos de la lista */
    protected Nodo cabeza, cola;
    protected int longitud;

    /**
     *  Constructor por omisión de la clase, no recibe parámetros.
     *  Crea una nueva lista con longitud 0.
     **/
    public Lista(){
	cabeza = null;
        longitud = 0;
    }

    /**
     *  Constructor copia de la clase. Recibe una lista con elementos.
     *  Crea una nueva lista exactamente igual a la que recibimos como parámetro.
     * @param l
     **/
    public Lista(Lista<T> l){
        for(T elem : l){
	    agregarAlFinal(elem);
	}
    }

    /**
     *  Constructor de la clase que recibe parámetros.
     *  Crea una nueva lista con los elementos de la estructura iterable que recibe como parámetro.
     * @param iterable
     **/
    public Lista(Iterable<T> iterable){
        for(T elem: iterable)
	    agregar(elem);
    }
    
    /**
     * Método que nos dice si las lista está vacía.
     * @return <code>true</code> si el conjunto está vacío, <code>false</code>
     * en otro caso.
     */
    @Override
    public boolean esVacia(){
        return cabeza == null;
    }
    
    /**
     * Método para eliminar todos los elementos de una lista
     */
    @Override
    public void vaciar(){
        cabeza = cola = null;
	longitud = 0;
    }
    
    /**
     * Método para obtener el tamaño de la lista
     * @return tamanio Número de elementos de la lista.
     **/
    @Override
    public int getTamanio(){
        return longitud;
    }
    
    /**
     * Método para agregar un elemento a la lista.
     * @param elemento Objeto que se agregará a la lista.
     */
    @Override
    public void agregar(T elemento){
       if(elemento == null) throw new IllegalArgumentException();
        
        Nodo nodoAux = new Nodo(elemento);
        if(getTamanio() < 1){
            cabeza = cola = nodoAux;
        }else{
            cabeza.anterior = nodoAux;
            nodoAux.siguiente = cabeza;
            cabeza = nodoAux; 
        }
        longitud++;
    }
    
    
    /**
     * Método para agregar al final un elemento a la lista.
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlFinal(T elemento) throws IllegalArgumentException {
        if(elemento == null)throw new IllegalArgumentException();
	Nodo nodoAux = new Nodo(elemento);
	if(getTamanio() < 1){
	    cola = cabeza = nodoAux;
	}else{
	    cola.siguiente = nodoAux;
	    nodoAux.anterior = cola;
	    cola = nodoAux;
	}
	longitud++;
    }

    /**
     * Método para obtener el primer elemento.
     */
    @Override
    public T getPrimero() throws NoSuchElementException {
        if(getTamanio() < 1)throw new NoSuchElementException();
	return cabeza.elemento;
    }

    /**
     * Método para obtener el último elemento.
     */
    public T getUltimo() throws NoSuchElementException {
        if(getTamanio() < 1)throw new NoSuchElementException();
	return cola.elemento; 
    }
    
    /**
     * Método para verificar si un elemento pertenece a la lista.
     * @param elemento Objeto que se va a buscar en la lista.
     * @return <code>true</code> si el elemento esta en el lista y false en otro caso.
     */
    @Override
    public boolean contiene(T elemento) throws NoSuchElementException {
        return buscaNodo(cabeza, elemento) != null;
    }

    /**
     * Método para eliminar un elemento de la lista.
     * @param elemento Objeto que se eliminara de la lista.
     * todo
     */
    @Override
    public void eliminar(T elemento) throws NoSuchElementException {
        Nodo nodo = buscaNodo(cabeza, elemento);
	if(nodo == null){
	    return;
	}else if(cabeza == cola){
	    cabeza = cola = null;
	}else if(cabeza == nodo){
	    cabeza = cabeza.siguiente;
	    cabeza.anterior = null;
	}else if(cola == nodo){
	    cola = cola.anterior;
	    cola.siguiente = null;
	}else{
	    nodo.siguiente.anterior = nodo.anterior;
	    nodo.anterior.siguiente = nodo.siguiente;
	}
	longitud--;
    }

    /**
     * Método para eliminar el primer elemento de la lista.
     */
    @Override
    public void eliminarPrimero() throws NoSuchElementException {
        if(getTamanio() < 1)throw new NoSuchElementException();

	T eAux = cabeza.elemento;
	if(getTamanio() == 1){
	    cabeza = cola = null;
	}else{
	    cabeza = cabeza.siguiente;
	    cabeza.anterior = null;
	}
	longitud--;
    }

    /**
     * Método para eliminar el ultimo elemento de la lista.
     */
    public void eliminarUltimo() throws NoSuchElementException {
	if(cola == null) throw new NoSuchElementException();
	T eAux = cola.elemento;
	if(getTamanio() == 1){
	    cola = cabeza = null;
	}else{
	    cola = cola.anterior;
	    cola.siguiente = null;
	}
	longitud--;
    }

    /**
     * Método que devuelve la posición en la lista que tiene la primera
     * aparición del <code> elemento</code>.
     * @param elemento El elemnto del cuál queremos saber su posición.
     * @return i la posición del elemento en la lista, -1, si no se encuentra en ésta.
     */
    @Override
    public int indiceDe(T elemento) throws NoSuchElementException{
        return indiceDe(0, cabeza, elemento);
    }
    /*Método auxiliar*/
    private int indiceDe(int i, Nodo nodo, T elemento){
        if(nodo == null) return -1;
        if(nodo.elemento.equals(elemento)) return i;
        return indiceDe(i+1, nodo.siguiente, elemento);
    }


    /**
     * Método que nos devuelve el elemento que esta en la posición i
     * @param i La posición cuyo elemento deseamos conocer.
     * @return <code> elemento </code> El elemento que contiene la lista,
     * <code>null</code> si no se encuentra
     * @throws IndexOutOfBoundsException Si el índice es < 0 o >longitud()
     */
    @Override
    public T getElemento(int i)throws IndexOutOfBoundsException{
	if(i < 0 || i >=getTamanio())throw new IndexOutOfBoundsException();
	return getElemento(i, cabeza, 0);
    }
    /*Método auxiliar*/
    private T getElemento(int i, Nodo nodo, int j){
        if(i == j) return nodo.elemento;
        return getElemento(i, nodo.siguiente, j+1);
    }

    /**
     * Método que devuelve una copia de la lista, pero en orden inverso
     * @return Una copia con la lista l revés.
     */
    @Override
    public Lista<T> reversa(){
        Lista<T> listaAux = new Lista<>();
	return reversa(listaAux, cabeza);
    }
    /*Método auxiliar*/
    private Lista<T> reversa(Lista<T>list, Nodo nodo){
	if(nodo == null) return list;
	list.agregar(nodo.elemento);
	return reversa(list, nodo.siguiente);
    }

    /**
     * Método que devuelve una copia exacta de la lista
     * @return la copia de la lista.
     */
    @Override
    public Lista<T> copia(){
        Lista<T> listAux = new Lista<>();
	return copia(listAux, cabeza);
    }
    /*Método auxiliar*/
    private Lista<T> copia(Lista<T>list, Nodo nodo){
	if(nodo == null) return list;
	list.agregarAlFinal(nodo.elemento);
	return copia(list, nodo.siguiente);
    }

    /**
     * Método que nos dice si una lista es igual que otra.
     * @param o objeto a comparar con la lista.
     * @return <code>true</code> si son iguales, <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object o){
	if(!(o instanceof Lista))
	    return false;
    @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>) o;
    if(lista.getTamanio() != getTamanio()) return false;

    Nodo a1 = cabeza;
    Nodo a2 = lista.cabeza;
    while(a1 != null && a2 != null){
	if(!a1.elemento.equals(a2.elemento))
	    return false;
	a1 = a1.siguiente;
	a2 = a2.siguiente;
    }
    return true;
    }

    /**
     * Método que devuelve un iterador sobre la lista
     * @return java.util.Iterador -- iterador sobre la lista
     */
    @Override
    public java.util.Iterator<T> iterator(){
        return new IteradorLista();
    }

    @Override
    public String toString() {
        if (esVacia()) {
            return "[]";
        }
        Nodo nodo = cabeza;
        String cad = "[" + nodo.elemento;
        while (nodo.siguiente != null) {
            nodo = nodo.siguiente;
            cad += ", " + nodo.elemento;
        }
        return cad + "]";
    }
      
    
    /* Método auxiliar para obtener una referencia a un nodo con un elemento
    específico. Si no existe tal nodo, devuelve <code> null </code> */
    private Nodo getNodo(T elem) throws NoSuchElementException {
        return buscaNodo(cabeza, elem);
	}
    private Nodo buscaNodo(Nodo a, T elemento){
	if(a == null) return null;
	if(a.elemento.equals(elemento)) return a;
	return buscaNodo(a.siguiente, elemento);
    }

}
