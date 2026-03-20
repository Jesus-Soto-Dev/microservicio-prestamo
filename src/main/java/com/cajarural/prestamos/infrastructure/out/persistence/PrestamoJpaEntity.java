package com.cajarural.prestamos.infrastructure.out.persistence;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.cajarural.prestamos.domain.model.EstadoPrestamo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="PRESTAMOS")
public class PrestamoJpaEntity {

	@Id
	@Column(name="ID", length= 36, nullable = false)
	private String id;
	
	@Column(name="NIF_TITULAR", nullable = false, length = 20)
	private String nifTitular;
	
	@Column(name="IMPORTE", nullable = false)
	private BigDecimal importe;
	
	@Column(name="PLAZO_MESES", nullable = false)
	private int plazoMeses;
	
	@Column(name="TASA_INTERES_ANUAL", nullable = false)
	private double tasaInteresAnual;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ESTADO", nullable = false, length = 20)
	private EstadoPrestamo estado;
	
	@Column(name="FECHA_SOLICITUD", nullable = false)
	private LocalDate fechaSolicitud;

	
	public PrestamoJpaEntity() {}

	public PrestamoJpaEntity(String id, String nifTitular, BigDecimal importe, int plazoMeses, double tasaInteresAnual,
			EstadoPrestamo estado, LocalDate fechaSolicitud) {
		super();
		this.id = id;
		this.nifTitular = nifTitular;
		this.importe = importe;
		this.plazoMeses = plazoMeses;
		this.tasaInteresAnual = tasaInteresAnual;
		this.estado = estado;
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNifTitular() {
		return nifTitular;
	}

	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public int getPlazoMeses() {
		return plazoMeses;
	}

	public void setPlazoMeses(int plazoMeses) {
		this.plazoMeses = plazoMeses;
	}

	public double getTasaInteresAnual() {
		return tasaInteresAnual;
	}

	public void setTasaInteresAnual(double tasaInteresAnual) {
		this.tasaInteresAnual = tasaInteresAnual;
	}

	public EstadoPrestamo getEstado() {
		return estado;
	}

	public void setEstado(EstadoPrestamo estado) {
		this.estado = estado;
	}

	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	
}
