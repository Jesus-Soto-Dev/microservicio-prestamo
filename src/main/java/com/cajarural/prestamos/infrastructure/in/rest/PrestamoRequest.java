package com.cajarural.prestamos.infrastructure.in.rest;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada -> representa el JSON en el body de POST /prestamos
 * */
public class PrestamoRequest { 
	
	@NotBlank(message = "El NIF del titular es obligatorio")
	private String nifTitular;
	
	@NotNull(message = "El importe es obligatorio")
	@DecimalMin(value = "500.00", message = "El importe mínimo es de 500 €")
	@DecimalMax(value = "50000.00", message = "El importe máximo es de 50000 €")
	private String importe;
	
	@Min(value = 6 , message = "El plazo mínimo es de 6 meses")
	@Max(value = 120, message = "El plazo máximo es de 120 meses")
	private int plazoMeses;
	
	@DecimalMin(value = "0.1", message = "La tasa mínima es de 0.1%")
	@DecimalMax(value = "30.0", message = "La tasa máxima es de 30%")
	private double tasaInteresAnual;

	public PrestamoRequest() {}

	public PrestamoRequest(String nifTitular, String importe, int plazoMeses, double tasaInteresAnual) {
		super();
		this.nifTitular = nifTitular;
		this.importe = importe;
		this.plazoMeses = plazoMeses;
		this.tasaInteresAnual = tasaInteresAnual;
	}

	public String getNifTitular() {
		return nifTitular;
	}

	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
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
}
