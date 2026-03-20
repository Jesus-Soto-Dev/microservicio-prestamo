package com.cajarural.prestamos.infrastructure.out.soap;
//Clases generadas por CXF
import com.cajarural.prestamos.infrastructure.out.soap.generated.ConsultarRiesgoRequest;
import com.cajarural.prestamos.infrastructure.out.soap.generated.ConsultarRiesgoResponse;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
/**
 * Flujo:
 * 	1. Recibe el NIF como body
 * 	2. SI falla el SOAP, reintenta 3 veces
 * 	3. Si sigue fallando, pasa la Dead Letter Channel
 * 	4. Se todo fluye correctamente, devuelve el nivel de riesgo como String
 * */
@Component
public class RiesgoCamelRoute extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
		errorHandler(
			deadLetterChannel("log:riesgo-crediticio-errores?level=ERROR")
				.maximumRedeliveries(3) // tres intentos
				.redeliveryDelay(2000) // delay
				.useExponentialBackOff() // por cada intento, aumenta el delay
				.logRetryAttempted(true)
		);
		
		from("direct:consultarRiesgo") //Llamada desde Java
			.routeId("ruta-consultar-riesgo") // para logs
			.log("Consultando riesgo crediticio para NIF: ${body}")
			.process(exchange -> {
				String nif = exchange.getIn().getBody(String.class);
				//Se construye la request SOAP con las clases generadas
				ConsultarRiesgoRequest request = new ConsultarRiesgoRequest();
				request.setNif(nif);
				exchange.getIn().setBody(request);
			})
			.to("cxf:{{legado.riesgo-crediticio-url}} +" //Componente Camel para SOAP
					+ "?serviceClass=com.cajarural.prestamos.infrastructure.out.soap.generated.RiesgoCrediticioPortType"
					+ "&operationName=consultarRiesgo" //Soap para invocar
					+ "&dataFormat=POJO") //Devuelve un LIST
			.process(exchange -> { //Transforma la respuesta al objeto JAVA
				List<?> bodies = exchange.getIn().getBody(List.class); // Adaptamos la lista recogida
				//bodies.get(0) es una clase ConsultarRiesgoResponse!
				ConsultarRiesgoResponse response = (ConsultarRiesgoResponse) bodies.get(0); //Lo guardamos en la clase JAVA generada para la respuesta
				exchange.getIn().setBody(response.getNivelRiesgo());
			}).log("Nivel de riesgo recibido del legado: ${body}");
			
	}

}
