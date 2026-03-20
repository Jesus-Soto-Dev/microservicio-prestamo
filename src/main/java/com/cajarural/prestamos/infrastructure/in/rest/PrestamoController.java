package com.cajarural.prestamos.infrastructure.in.rest;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cajarural.prestamos.domain.model.Prestamo;
import com.cajarural.prestamos.domain.port.in.EjecutarPrestamoUseCase;

import jakarta.validation.Valid;

/**
 * Recibe y valida el JSON de entrada -@Valid-
 * Llamar al puerto de entrada del domain
 * */
@RestController
@RequestMapping("/prestamos")
public class PrestamoController {
	private final EjecutarPrestamoUseCase useCase;

	public PrestamoController(EjecutarPrestamoUseCase useCase) {
		super();
		this.useCase = useCase;
	}
	
	/**
	 * @Valid activa las validaciones del DTO
	 * */
	@PostMapping
	public ResponseEntity<PrestamoResponse> solicitarPrestamo(@Valid @RequestBody PrestamoRequest request) {
		Prestamo prestamo = useCase.solicitarPrestamo(request.getNifTitular(), request.getImporte(), request.getPlazoMeses(), request.getTasaInteresAnual());
		return ResponseEntity.status(HttpStatus.CREATED).body(PrestamoResponse.from(prestamo));
	}

    /**
     * TODO: implementar — 
     * Debe devolver el cuadro de amortización del préstamo.
     */
    @GetMapping("/{id}/amortizacion")
    public ResponseEntity<?> obtenerAmortizacion(@PathVariable UUID id) {
        throw new UnsupportedOperationException("No implementado aún");
    }
}
