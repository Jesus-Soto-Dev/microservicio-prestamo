package com.cajarural.prestamos.infrastructure.out.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CamelSpringBootTest
@SpringBootTest
public class RiesgoCamelRouteTest {
	
	@Autowired
	CamelContext camelContext;
	
	@Autowired
	ProducerTemplate producerTemplate;
	
	@EndpointInject("mock:legado") //Inyecta un endpoint mock
	MockEndpoint mockLegado;
	
	@Test
	void deberiaConsultarRiesgoCorrectamente() throws Exception {
		//GIVEN -> modificar ruta
		AdviceWith.adviceWith(camelContext, "ruta-consultar-riesgo",
				route -> route
					.weaveByToUri("cxf:*") //busca endpoints cxf:
					.replace()
					.to("mock:legado")); //Se reemplaza por este mock
		mockLegado.whenAnyExchangeReceived(exchange -> exchange.getIn().setBody("BAJO"));
		
		//WHEN
		String resultado = producerTemplate.requestBody(
				"direct:consultarRiesgo", //from() de la ruta
				"12345678A",
				String.class
				);
		
		//THEN
		assertEquals("BAJO", resultado);
		mockLegado.expectedMessageCount(1);
		mockLegado.assertIsSatisfied();
	}

}
