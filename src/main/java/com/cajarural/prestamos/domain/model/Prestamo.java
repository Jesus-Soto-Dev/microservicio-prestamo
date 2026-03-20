package com.cajarural.prestamos.domain.model;
import java.time.LocalDate;
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
     * TODO: al aprobar, generar el cuadro de amortización. Ver ticket PREST-42.
     */
    public void aprobar() {
        if (this.estado != EstadoPrestamo.SOLICITADO) {
            throw new IllegalStateException(
                "Solo se pueden aprobar préstamos SOLICITADOS, estado actual: " + estado);
        }
        this.estado = EstadoPrestamo.APROBADO;
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
        throw new UnsupportedOperationException("No implementado aún — ver ticket PREST-42");
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
