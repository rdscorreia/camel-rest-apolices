package br.com.seguros.route;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.validation.PredicateValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.seguros.model.Apolices;
import br.com.seguros.predicate.CustomPredicate;
import br.com.seguros.route.processor.ResultSetProcessor;

@Component
public class ApolicesRoute extends RouteBuilder{
	
	@Qualifier("dataSource")
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ResultSetProcessor resultSetProcessor;
	
	@Autowired
	CustomPredicate customPredicate;

	@Override
	public void configure() throws Exception {
		from("direct:apolice-route")
		.routeId("Rota_listar_Apolice_Route")
		.doTry()
			.choice()
				.when(customPredicate.getCompositePredicate())
					.log("Passou pelos Predicados")
					.setBody().simple("CALL db_apolices.proc_getApolices(${in.header.cpfCnpjRequest});")
					.log("Setando e chamando a procedure => ${body}")
					.to("jdbc:dataSource")
					.process(resultSetProcessor)
					.marshal().json(JsonLibrary.Jackson, Apolices.class)
					.log("Logando o resultado da procedure => ${body}")
			 .otherwise()
		    	.log("Deu erro ${headers}")
		    	.marshal().json(JsonLibrary.Jackson, Map.class)
		    .end()
		 .endDoTry()
		 .doCatch(PredicateValidationException.class)
		 	.log("Erro do doTry doCatch")
		 .end();
			
	}
}

