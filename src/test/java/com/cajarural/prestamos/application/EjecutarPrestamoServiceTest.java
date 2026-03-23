package com.cajarural.prestamos.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cajarural.prestamos.domain.model.EstadoPrestamo;
import com.cajarural.prestamos.domain.model.Importe;
import com.cajarural.prestamos.domain.model.NivelRiesgo;
import com.cajarural.prestamos.domain.model.Prestamo;
import com.cajarural.prestamos.domain.port.out.PrestamoRepositoryPort;
import com.cajarural.prestamos.domain.port.out.RiesgoCrediticioPort;

@ExtendWith(MockitoExtension.class)// Activa Mockito en Junit 5
public class EjecutarPrestamoServiceTest {

	@Mock
	PrestamoRepositoryPort repositoryPort;
	
	@Mock
	RiesgoCrediticioPort riesgoCrediticioPort;
	
	@InjectMocks
	EjecutarPrestamoService service;
	
	@Test
	void deberiaAprobarseSiElRiesgoEsBajo () {
		//GIVEN
		Prestamo prestamo = Prestamo.solicitar("12354678A",Importe.de("10000.00"),24,5.5);
		when(riesgoCrediticioPort.consultarRiesgo(anyString())).thenReturn(NivelRiesgo.BAJO);
		when(repositoryPort.guardar(any())).thenReturn(prestamo);
		
		//WHEN
		Prestamo resultado = service.solicitarPrestamo("12345678A", "10000.00", 24 , 5.5);
		
		//THEN
		assertEquals(EstadoPrestamo.APROBADO, resultado.getEstado());
	}
	
	@Test
	void deberiaRechazarRiesgoAlto() {
		//GIVEN
		Prestamo prestamo = Prestamo.solicitar("12345678A",Importe.de("10000.00"),24,5.5);
		when(riesgoCrediticioPort.consultarRiesgo(anyString())).thenReturn(NivelRiesgo.ALTO);
		when(repositoryPort.guardar(any())).thenReturn(prestamo);
		
		//WHEN
		Prestamo resultado = service.solicitarPrestamo("12345687A", "10000.00", 24, 5.5);
		
		//THEN
		assertEquals(EstadoPrestamo.RECHAZADO, resultado.getEstado());
	}
}
