package br.com.seguros.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

import br.com.seguros.model.Campo;

public class PredicateHeaderCompanhias implements Predicate {

	private static final String COMPANHIA = "companhia";

	@SuppressWarnings("unchecked")
	@Override
	public boolean matches(Exchange exchange) {

		List<String> validaCompanhia = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");

		Map<String, Object> request = exchange.getIn().getHeaders();
		List<String> companhias = new ArrayList<String>();

		if (!request.isEmpty()) {
			if (request.containsKey("companhia")) {
				if (request.get("companhia") instanceof String) {
					companhias.add((String) request.get("companhia"));
					if (validaCompanhia.containsAll(companhias)) {
						exchange.getIn().setHeader("companhiaRequest", companhias);
						return true;
					}
				} else {
					companhias.addAll((Collection<? extends String>) request.get("companhia"));
					if (validaCompanhia.containsAll(companhias)) {
						exchange.getIn().setHeader("companhiaRequest", companhias);
						return true;
					}
				}
			} else {
				return true;
			}
		}

		List<Campo> campos = new ArrayList<Campo>();
		campos.add(new Campo(COMPANHIA, "Campo informado contém valor inválido"));
		exchange.getIn().setBody(campos, Campo.class);
		exchange.getIn().setHeader("companhiaRequest", companhias);
		exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		return false;
	}

}