package com.cajarural.prestamos.domain.model;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class Prestamo {
	private final UUID id;
	private final String nifTitular;
	private final Importe importe;
	private final int plazoMeses;
	private final double tasaInteresAnual;
	private EstadoPrestamo estado;
	private final LocalDate fechaSolicitud;
	private List cuotas;
	
	private Prestamo(UUID id, String nifTitular, Importe importe, int plazoMeses, double tasaInteresAnual,
			EstadoPrestamo estado, LocalDate fechaSolicitud) {
		super();
		this.id = id;
		this.nifTitular = nifTitular;
		this.importe = importe;
		this.plazoMeses = plazoMeses;
		this.tasaInteresAnual = tasaInteresAnual;
		this.estado = estado;
		this.fechaSolicitud = fechaSolicitud;
		this.cuotas = new ArrayList<>(); 
	}
	
	//Factory Method --> estado SOLICITADO
	public static Prestamo solicitar(String nifTitular, Importe importe, int plazoMeses, double tasaInteresAnual) {
		Objects.requireNonNull(nifTitular, "El NIF no puede ser nulo");
		if(plazoMeses < 6 || plazoMeses > 120) {
			throw new IllegalArgumentException("El plazo debe estar entre 6 y 120 meses, recibido: " + plazoMeses);
		}
		if(tasaInteresAnual <= 0 || tasaInteresAnual>30) {
			throw new IllegalArgumentException("La tasa de interés debe estar entre 0 y 30%, recibida: " + tasaInteresAnual);
		}
		return new Prestamo(
				UUID.randomUUID(), nifTitular, importe, plazoMeses, tasaInteresAnual, EstadoPrestamo.SOLICITADO, LocalDate.now()
				);
	}
	
	// Factory method para reconstruir desde persistencia.
	public static Prestamo reconstituir(UUID id, String nifTitular, Importe importe, int plazoMeses, double tasaInteresAnual,
			EstadoPrestamo estado, LocalDate fechaSolicitud) {
		return new Prestamo(id, nifTitular, importe, plazoMeses, tasaInteresAnual, estado, fechaSolicitud);
	}
	
	  /*
     * Aprueba el préstamo si está en estado SOLICITADO.
     */
    public void aprobar() {
        if (this.estado != EstadoPrestamo.SOLICITADO) {
            throw new IllegalStateException(
                "Solo se pueden aprobar préstamos SOLICITADOS, estado actual: " + estado);
        }
        this.estado = EstadoPrestamo.APROBADO;
        this.cuotas = calcularCuadroAmortizacion();
    }

    /* Rechaza el préstamo si está en estado SOLICITADO.
     */
    public void rechazar() {
        if (this.estado != EstadoPrestamo.SOLICITADO) {
            throw new IllegalStateException(
                "Solo se pueden rechazar préstamos SOLICITADOS");
        }
        this.estado = EstadoPrestamo.RECHAZADO;
    }

    /*
     * TODO: implementar cálculo de cuadro de amortización.  Ver ticket PREST-42.
     */
    public List<CuotaAmortizacion> getCuotas() {
        return Collections.unmodifiableList(cuotas);
    }
    
    private List<CuotaAmortizacion> calcularCuadroAmortizacion() {
    	List<CuotaAmortizacion> resultado = new ArrayList<>();
    	BigDecimal capital = importe.getValor();
    	BigDecimal tasaMensual = BigDecimal.valueOf(tasaInteresAnual).divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
    	BigDecimal numerador = capital.multiply(tasaMensual);
    	BigDecimal unoPlusTasa = BigDecimal.ONE.add(tasaMensual);
    	BigDecimal potencia = unoPlusTasa.pow(plazoMeses, MathContext.DECIMAL128);
    	BigDecimal potenciaNegativa = BigDecimal.ONE.divide(potencia,10,RoundingMode.HALF_UP);
    	BigDecimal denominador = BigDecimal.ONE.subtract(potenciaNegativa); 
    	BigDecimal cuotaMensual = numerador.divide(denominador, 2, RoundingMode.HALF_UP);
    	
    	BigDecimal saldoPendiente = capital;
    	LocalDate fechaCuota = fechaSolicitud.plusMonths(1);
    	for(int mes = 1; mes <= plazoMeses; mes++) {
    		BigDecimal interes = saldoPendiente.multiply(tasaMensual).setScale(2, RoundingMode.HALF_UP);
    		BigDecimal capitalAmortizado = cuotaMensual.subtract(interes);
    		saldoPendiente = saldoPendiente.subtract(capitalAmortizado);
    		if(mes == plazoMeses) {
    			capitalAmortizado = capitalAmortizado.add(saldoPendiente);
    			saldoPendiente = BigDecimal.ZERO;
    		}
    		resultado.add(new CuotaAmortizacion(
    			mes,fechaCuota,cuotaMensual,capitalAmortizado,interes,saldoPendiente.max(BigDecimal.ZERO)	
    		));
    		fechaCuota = fechaCuota.plusMonths(1);
    	}
    	return resultado;
    }

    // Getters
    public UUID getId()                  { return id; }
    public String getNifTitular()        { return nifTitular; }
    public Importe getImporte()          { return importe; }
    public int getPlazoMeses()           { return plazoMeses; }
    public double getTasaInteresAnual()  { return tasaInteresAnual; }
    public EstadoPrestamo getEstado()    { return estado; }
    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
}
