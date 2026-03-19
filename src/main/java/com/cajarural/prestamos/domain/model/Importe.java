package com.cajarural.prestamos.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/*
 *   Reglas de un Value Object en DDD:
 *   1. Inmutable: todos los campos son final, sin setters.
 *   2. Se compara por valor: equals() compara el importe, no la referencia.
 *   3. Self-validating: lanza excepción si el valor es inválido.
 *   4. Sin identidad propia: dos Importe("100.00") son "el mismo".
 */
public final class Importe { //Value Object

	//Limite bancario
	private static final BigDecimal MINIMO = new BigDecimal("500.00");
	private static final BigDecimal MAXIMO = new BigDecimal("50000.00");
	
	private final BigDecimal valor;
	
	private Importe(BigDecimal valor) {
		//Redondeo a 2 decimales
		this.valor = valor.setScale(2, RoundingMode.HALF_UP);
	}
	
	//Factory method --> solo se puede crear de esta forma el importe, incluye la validación
	public static Importe de(BigDecimal valor) {
		Objects.requireNonNull(valor, "El importe no puede ser nulo");
		if(valor.compareTo(MINIMO) < 0) {
			throw new IllegalArgumentException("Importe mínimo: " + MINIMO + "€, recibido: " + valor);
		}
		if(valor.compareTo(MAXIMO) > 0) {
			throw new IllegalArgumentException("Importe máximo: " + MAXIMO + "€, recibido: " + valor);
		}
		return new Importe(valor);
	}
	
    /* Sobrecarga para comodidad al escribir tests */
    public static Importe de(String valor) {
        return de(new BigDecimal(valor));
    }
    
    public BigDecimal getValor() {
    	return valor;
    }
    
    @Override
    public boolean equals(Object o) {
    	if (this == o) return true;
    	if(!(o instanceof Importe)) return false;
    	Importe other = (Importe) o;
    	return this.valor.compareTo(other.valor) == 0; //CompareTu ignora la escala, equals sí la tiene en cuenta
    }
    
    @Override
    public String toString() { // .toPlainString se usa para no representar números grandes en notación científica
        return valor.toPlainString() + " €";
    }
    
}
