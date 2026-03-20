package com.cajarural.prestamos.infrastructure.out.soap;

import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import com.cajarural.prestamos.domain.model.NivelRiesgo;
import com.cajarural.prestamos.domain.port.out.RiesgoCrediticioPort;
/**
 * Se encarga de:
 * 	1. Enviar el NIF a la ruta Camel
 * 	2. Traducir el String a un ENUM (por el Anti-Corruption Layer)
 * */
@Component
public class RiesgoCrediticioAdapter implements RiesgoCrediticioPort{

	private final ProducerTemplate producerTemplate; //Objeto de Camel para enviar mensajes a una ruta desde Java
	
	public RiesgoCrediticioAdapter(ProducerTemplate producerTemplate) {
		super();
		this.producerTemplate = producerTemplate;
	}

	@Override
	public NivelRiesgo consultarRiesgo(String nif) {
		String nivelRiesgoStr = producerTemplate.requestBody("direct:consultarRiesgo", nif,String.class);
		return NivelRiesgo.fromLegado(nivelRiesgoStr); //Transforma String a ENUM
	}

}
