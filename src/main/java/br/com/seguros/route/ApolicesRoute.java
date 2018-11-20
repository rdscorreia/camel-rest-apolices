package br.com.seguros.route;

import javax.sql.DataSource;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.validation.PredicateValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.seguros.predicate.ValidaRequestPredicate;
import br.com.seguros.route.processor.ResultSetProcessor;

@Component
public class ApolicesRoute extends RouteBuilder{
	
	@Qualifier("dataSource")
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ResultSetProcessor resultSetProcessor;
	
	@Autowired
	ValidaRequestPredicate validaRequestPredicate;

	@SuppressWarnings("unchecked")
	@Override
	public void configure() throws Exception {
		from("direct:apolice-route")
		.routeId("Rota_listar_Apolice_Route")
		.doTry()
			.validate(validaRequestPredicate)	
			.setBody().simple("CALL db_apolices.proc_getApolices(${in.header.cpfCnpjRequest});")
			.to("jdbc:dataSource")
			.process(resultSetProcessor)
			.marshal().json(JsonLibrary.Jackson)
			.log("Logando o resultado final => ${body}")
			.to("mock:result")
		 .endDoTry()
		 .doCatch(PredicateValidationException.class, Exception.class)
		 	.marshal().json(JsonLibrary.Jackson)
		 	.log("Logando erros => ${body}")
		 	.to("mock:error")
		 .end();
			
	}
}

