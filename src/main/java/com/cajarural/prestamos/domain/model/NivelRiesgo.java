package com.cajarural.prestamos.domain.model;

//El wsld lo devuelve en un String, pero se convierte a ENUM en el adaptador CXF, para no depender de nadie más
//Anti-Corruption Layer
public enum NivelRiesgo {
	BAJO,
	MEDIO,
	ALTO;
	
	public static NivelRiesgo fromLegado(String valor) {
		if (valor == null) return ALTO; //Si no se conoce, ALTO por defecto
		return switch (valor.toUpperCase().trim()) {
		case "BAJO" -> BAJO;
		case "MEDIO" -> MEDIO;
		default-> ALTO;
		};
	}
}
