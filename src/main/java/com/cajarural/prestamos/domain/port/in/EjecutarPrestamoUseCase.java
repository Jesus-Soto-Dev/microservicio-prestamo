package com.cajarural.prestamos.domain.port.in;

import java.util.List;
import java.util.UUID;

import com.cajarural.prestamos.domain.model.CuotaAmortizacion;
import com.cajarural.prestamos.domain.model.Prestamo;

//Lo que se le puede pedir al dominio -> implementado por la capa de aplicación
//NOTA: Al añadir una operación a la API, se declara como interfaz primero, luego sei mplementa en el Service
public interface EjecutarPrestamoUseCase {

	Prestamo solicitarPrestamo(String niftitular, String importeStr, int plazoMeses, double tasaInteresAnual);
	
	/*
	 * TODO: implementar TICKET PREST-42
	 * */
	List<CuotaAmortizacion> obtenerAmortizacion(UUID prestamoId);
}
