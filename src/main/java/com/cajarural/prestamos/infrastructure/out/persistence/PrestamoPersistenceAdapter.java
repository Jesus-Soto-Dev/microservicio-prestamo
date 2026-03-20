package com.cajarural.prestamos.infrastructure.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.cajarural.prestamos.domain.model.Importe;
import com.cajarural.prestamos.domain.model.Prestamo;
import com.cajarural.prestamos.domain.port.out.PrestamoRepositoryPort;

/**
 * Traduce entre Prestamo (PrestamoRepositoryPort) y PrestamoJpaEntity(PrestamoJpaRepository)
 * */
@Component
public class PrestamoPersistenceAdapter implements PrestamoRepositoryPort{
	
	private final PrestamoJpaRepository jpaRepository;

	public PrestamoPersistenceAdapter(PrestamoJpaRepository jpaRepository) {
		super();
		this.jpaRepository = jpaRepository;
	}

	@Override
	public Prestamo guardar(Prestamo prestamo) {
		PrestamoJpaEntity entity = toEntity(prestamo);
		PrestamoJpaEntity saved = jpaRepository.save(entity);
		return toDomain(saved);
	}
	
	private PrestamoJpaEntity toEntity(Prestamo prestamo) {
		return new PrestamoJpaEntity(
				prestamo.getId().toString(),
				prestamo.getNifTitular(),
				prestamo.getImporte().getValor(),
				prestamo.getPlazoMeses(),
				prestamo.getTasaInteresAnual(),
				prestamo.getEstado(),
				prestamo.getFechaSolicitud()
				);
	}
	
	/**
	 * JPA -> Domain
	 * */
	private Prestamo toDomain(PrestamoJpaEntity entity) {
		return Prestamo.reconstituir(
				UUID.fromString(entity.getId()),
				entity.getNifTitular(),
				Importe.de(entity.getImporte()),
				entity.getPlazoMeses(),
				entity.getTasaInteresAnual(),
				entity.getEstado(),
				entity.getFechaSolicitud()
				);
	}

	@Override
	public Optional<Prestamo> buscarPorId(UUID id) {
		return jpaRepository.findById(id.toString()).map(this::toDomain);
	}

}
