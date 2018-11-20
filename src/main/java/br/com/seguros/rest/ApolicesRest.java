package br.com.seguros.rest;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ApolicesRest extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		rest("/").description("Apolice REST service")
			.consumes("application/json")
	        .produces("application/json")
	        .get()
	        	.description("The list of all the apolices")
	            .route().routeId("apolice-api")
	            .log("Chamando rota Rota_listar_Apolice_Route")
	            .to("direct:apolice-route")
	            .log("Terminando execução")	            
	    .endRest();
		
	}

}
