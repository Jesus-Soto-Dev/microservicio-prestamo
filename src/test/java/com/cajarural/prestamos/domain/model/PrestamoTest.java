package com.cajarural.prestamos.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrestamoTest {
	
	@Test
	void deberiaCrearSolicitado () {
		//GIVEN
		String nif = "12345678A";
		Importe importe = Importe.de("10000.00");
		
		//WHEN
		Prestamo prestamo = Prestamo.solicitar(nif, importe, 24, 5.5);
		
		//THEN
		assertEquals(EstadoPrestamo.SOLICITADO, prestamo.getEstado());
	}
	
	@Test
	void deberiaAprobarYGenerarCuotas() {
		//GIVEN
		String nif = "12345678A";
		Importe importe = Importe.de("10000.00");
		
		//WHEN
		Prestamo prestamo = Prestamo.solicitar(nif, importe, 24, 5.5);
		prestamo.aprobar();
		
		//THEN
		assertEquals(EstadoPrestamo.APROBADO, prestamo.getEstado());
		assertFalse(prestamo.getCuotas().isEmpty());
	}
	
	@Test
	void deberiaLanzarExceptionNoSolicitado() {
		//GIVEN
		String nif = "12345678A";
		Importe importe = Importe.de("10000.00");
		
		//WHEN
		Prestamo prestamo = Prestamo.solicitar(nif, importe, 24, 5.5);
		prestamo.rechazar();
		assertThrows(IllegalStateException.class, () -> {
			prestamo.aprobar(); //No está en estado SOLICITADO
		});
	}
	
	@Test
	void deberiaCambiarARechazado() {
		//GIVEN
		String nif = "12345678A";
		Importe importe = Importe.de("10000.00");
		
		//WHEN
		Prestamo prestamo = Prestamo.solicitar(nif, importe, 24, 5.5);
		prestamo.rechazar();
		
		//THEN
		assertEquals(EstadoPrestamo.RECHAZADO, prestamo.getEstado());
	}

}
