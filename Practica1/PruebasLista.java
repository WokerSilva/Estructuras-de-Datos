public class PruebasLista{

    public static void main(String[] args) {

	String NoBalanceado = " (30)+(12)-14 (4)*9) ";
	String SiBalanceado = " ((5)-(5)) ";
	System.out.println(" <<(30)+(12)-14 (4)*9)>> La cadena uno esta balanceada?" + Parentesis(NoBalanceado));
	System.out.println(" <<((5)-(5))>>  La cadena dos esta balanceada?" + Parentesis(SiBalanceado));
    }

    public static boolean Parentesis(String cadena){
	
        Pila<String> pila = new Pila();
	int i = 0;

        while (i <cadena.length()){
        if(cadena.charAt(i)=='('){
	    pila.push("(");               // Apilamos parentesis de apertura 
	}else if(cadena.charAt(i)==')'){  // Revisamos los casos
        if (!pila.esVacia()){
	    pila.pop();                   // Pila diferente de vacia hacemos pop
	}else{
	    pila.push(")");               // La pila no puede empezar con un cierre, apilamos y salimos
	    }
	 }
            i++;
	}
        if(pila.esVacia()){
	    return true;
	} else {
	    return false;
	}

    }		
}
