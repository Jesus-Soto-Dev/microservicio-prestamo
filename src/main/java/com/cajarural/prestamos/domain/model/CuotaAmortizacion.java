package com.cajarural.prestamos.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class CuotaAmortizacion { //Value Object

	private final int numeroCuota; //Empieza en 1
	
	private final LocalDate fechaVencimiento;
	
	private final BigDecimal cuotaMensual; // capital + intereses
	
	private final BigDecimal capitalAmortizado; //Parte de al cuota amortiza el capital
	
	private final BigDecimal intereses; 
	
	private final BigDecimal saldoPendiente; //Total-cuota

	public CuotaAmortizacion(int numeroCuota, LocalDate fechaVencimiento, BigDecimal cuotaMensual,
			BigDecimal capitalAmortizado, BigDecimal intereses, BigDecimal saldoPendiente) {
		super();
		this.numeroCuota = numeroCuota;
		this.fechaVencimiento = fechaVencimiento;
		this.cuotaMensual = cuotaMensual;
		this.capitalAmortizado = capitalAmortizado;
		this.intereses = intereses;
		this.saldoPendiente = saldoPendiente;
	}
	
    public int getNumeroCuota()              { return numeroCuota; }
    public LocalDate getFechaVencimiento()   { return fechaVencimiento; }
    public BigDecimal getCuotaMensual()      { return cuotaMensual; }
    public BigDecimal getCapitalAmortizado() { return capitalAmortizado; }
    public BigDecimal getIntereses()         { return intereses; }
    public BigDecimal getSaldoPendiente()    { return saldoPendiente; }
	
	@Override
	public String toString() {
		return String.format(
	            "Cuota %d | %s | Total: %s€ | Capital: %s€ | Intereses: %s€ | Pendiente: %s€",
	            numeroCuota,
	            fechaVencimiento,
	            cuotaMensual.toPlainString(),
	            capitalAmortizado.toPlainString(),
	            intereses.toPlainString(),
	            saldoPendiente.toPlainString()
	            );
	}
}
