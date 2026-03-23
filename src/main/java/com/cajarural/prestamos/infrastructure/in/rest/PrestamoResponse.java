package com.cajarural.prestamos.infrastructure.in.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.cajarural.prestamos.domain.model.CuotaAmortizacion;
import com.cajarural.prestamos.domain.model.EstadoPrestamo;
import com.cajarural.prestamos.domain.model.Prestamo;

/**
 * DTO de salida --> el JSON que devuelve la API
 * Se traduce del dominio a este DTO
 * */
public class PrestamoResponse {
    private UUID id;
    private String nifTitular;
    private String importe;
    private int plazoMeses;
    private double tasaInteresAnual;
    private String estado;
    private LocalDate fechaSolicitud;
    private List<CuotaAmortizacion> cuotas;

    public PrestamoResponse() {}
    
    //Factory method: traduce Dominio a DTO
    public static PrestamoResponse from(Prestamo prestamo) {
    	PrestamoResponse response = new PrestamoResponse();
    	response.id = prestamo.getId();
    	response.nifTitular = prestamo.getNifTitular();
    	response.importe = prestamo.getImporte().toString();
    	response.plazoMeses = prestamo.getPlazoMeses();
    	response.tasaInteresAnual = prestamo.getTasaInteresAnual();
    	response.estado = prestamo.getEstado().name();
    	response.fechaSolicitud = prestamo.getFechaSolicitud();
    	if(prestamo.getEstado() == EstadoPrestamo.APROBADO) {
    		response.cuotas = prestamo.getCuotas();
    	}
    	return response;
    }

	public List<CuotaAmortizacion> getCuotas() {
		return cuotas;
	}

	public UUID getId() {
		return id;
	}

	public String getNifTitular() {
		return nifTitular;
	}

	public String getImporte() {
		return importe;
	}

	public int getPlazoMeses() {
		return plazoMeses;
	}

	public double getTasaInteresAnual() {
		return tasaInteresAnual;
	}

	public String getEstado() {
		return estado;
	}

	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}

}
