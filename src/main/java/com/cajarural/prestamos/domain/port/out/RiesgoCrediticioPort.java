package com.cajarural.prestamos.domain.port.out;
import com.cajarural.prestamos.domain.model.NivelRiesgo;
//Implementada por infraestructura/soap (RiesgoCrediticoAdapter) y ejecutada por aplicación (EjecutarPrestamoService)
//El servicio SOAP y el dominio comparten ese atributo, pero no es el mismo por la Anti-Corruption Layer
public interface RiesgoCrediticioPort {
    /**
     * Consulta el nivel de riesgo de un cliente en el sistema legado.
     *
     * @param nif identificador del cliente
     * @return BAJO, MEDIO o ALTO
     * @throws RiesgoCrediticioException si el sistema legado no responde
     */
	NivelRiesgo consultarRiesgo(String nif);
}
