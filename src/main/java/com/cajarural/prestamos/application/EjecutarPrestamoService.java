package com.cajarural.prestamos.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cajarural.prestamos.domain.model.CuotaAmortizacion;
import com.cajarural.prestamos.domain.model.EstadoPrestamo;
import com.cajarural.prestamos.domain.model.Importe;
import com.cajarural.prestamos.domain.model.NivelRiesgo;
import com.cajarural.prestamos.domain.model.Prestamo;
import com.cajarural.prestamos.domain.port.in.EjecutarPrestamoUseCase;
import com.cajarural.prestamos.domain.port.out.PrestamoRepositoryPort;
import com.cajarural.prestamos.domain.port.out.RiesgoCrediticioPort;

//Implementa el puerto de entrada, se comunica con las interfaces -puertos de salida-, por ello es posible testearla con Mock
@Service
public class EjecutarPrestamoService implements EjecutarPrestamoUseCase{
	
	//Implementa los puertos por constructor
	private final PrestamoRepositoryPort repositoryPort;
	private final RiesgoCrediticioPort riesgoCrediticioPort;
	
	//Campos inmutables, facilidad de testeo y facilidad de detección de errores 
	private EjecutarPrestamoService(PrestamoRepositoryPort repositoryPort, RiesgoCrediticioPort riesgoCrediticioPort) {
		super();
		this.repositoryPort = repositoryPort;
		this.riesgoCrediticioPort = riesgoCrediticioPort;
	}

    /**
     * Flujo completo de solicitud de préstamo:
     *   1. Crear el préstamo en dominio (estado SOLICITADO)
     *   2. Consultar riesgo crediticio en el sistema legado
     *   3. Aprobar o rechazar según el nivel de riesgo
     *   4. Persistir el resultado
     */
	@Override
	public Prestamo solicitarPrestamo(String niftitular, String importeStr, int plazoMeses, double tasaInteresAnual) {
		// 1. Crear con validaciones
		Prestamo prestamo = Prestamo.solicitar(niftitular, Importe.de(importeStr), plazoMeses, tasaInteresAnual);
		
		// 2. Consultar el riesgo al legacy por medio del puerto -> solo se llama al port, no se sabe sobre SOAP y Camel
		NivelRiesgo riesgo = riesgoCrediticioPort.consultarRiesgo(niftitular);
		
		// 3. Riesgo Alto -> rechazar
		if( riesgo == NivelRiesgo.ALTO) {
			prestamo.rechazar();
		} else {
			prestamo.aprobar();
		}
		
		// 4. Persistencia -> a través puerto de salida
		return repositoryPort.guardar(prestamo);
	}

	/* Pendiente de implementación*/
	@Override
	public List<CuotaAmortizacion> obtenerAmortizacion(UUID prestamoId) {
		Prestamo prestamo = repositoryPort.buscarPorId(prestamoId)
				.orElseThrow(() -> new IllegalArgumentException("No existe préstamo con id : " + prestamoId));
		
		if(prestamo.getEstado() != EstadoPrestamo.APROBADO) throw new IllegalStateException("El préstamo " + prestamoId + " no está APROBADO, estado actual: " + prestamo.getEstado());
		return prestamo.getCuotas();
	}

}
