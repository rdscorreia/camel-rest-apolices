package br.com.seguros.route.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import br.com.seguros.model.Apolice;
import br.com.seguros.model.Apolices;

@Component
public class ResultSetProcessor implements Processor {

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {
		
		List<Apolice> apolices = new ArrayList<Apolice>();
		
		apolices = ((List<Apolice>) exchange.getIn().getBody());
	
		Apolices result = new Apolices();
		result.setApolices(apolices);
		
		if (result.getApolices().isEmpty()) {
			exchange.getOut().setBody("Deu erro na execução");
		}
		
		exchange.getOut().setBody(result, Apolices.class);
	}

}