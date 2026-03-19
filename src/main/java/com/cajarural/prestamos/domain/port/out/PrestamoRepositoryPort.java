package com.cajarural.prestamos.domain.port.out;

import java.util.Optional;
import java.util.UUID;

import com.cajarural.prestamos.domain.model.Prestamo;

//Lo que el dominio necesita de la persistencia
//Lo implementa la capa de infraestructura(PrestamoPersistenceAdapter) y lo ejecuta la capa de aplicación(EjecutarPrestamoService)
public interface PrestamoRepositoryPort {

	Prestamo guardar(Prestamo prestamo);
	
	Optional<Prestamo> buscarPorId(UUID id);
}
