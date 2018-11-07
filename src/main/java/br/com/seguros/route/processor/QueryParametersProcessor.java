package br.com.seguros.route.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

@Component
public class QueryParametersProcessor implements Processor {

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {

		Map<String, Object> request = exchange.getIn().getHeaders();
		String cpfCnpj = null;
		Boolean documentoVigente = false;

		List<String> companhias = new ArrayList<String>();

		if (!request.isEmpty()) {
			if (request.containsKey("cpf_cnpj")) {
				cpfCnpj = (String) request.get("cpf_cnpj");
			}

			if (request.containsKey("documento_vigente")) {
				documentoVigente = BooleanUtils.toBooleanObject((String) request.get("documento_vigente"));
			}

			if (request.containsKey("companhia")) {
				if (request.get("companhia") instanceof String) {
					companhias.add((String) request.get("companhia"));
				} else {
					companhias.addAll((Collection<? extends String>) request.get("companhia"));
				}
			}

			System.out.println("===========================================================================================");
			System.out.println("Cpf => " + cpfCnpj + " Documento Vigente => " + documentoVigente + " Companhia" + companhias);
			System.out.println("===========================================================================================");

			// Set parametros para acesso a banco tratados.
			exchange.getIn().setHeader("cpfCnpjRequest", cpfCnpj);
			exchange.getIn().setHeader("documentoVigenteRequest", documentoVigente);
			exchange.getIn().setHeader("companhiaRequest", companhias);

		}
	}
}
