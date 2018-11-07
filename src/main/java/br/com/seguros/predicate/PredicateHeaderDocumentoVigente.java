package br.com.seguros.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.commons.lang3.BooleanUtils;

import br.com.seguros.model.Campo;

public class PredicateHeaderDocumentoVigente implements Predicate {

	private static final String DOCUMENTO_VIGENTE = "documento_vigente";

	@Override
	public boolean matches(Exchange exchange) {

		List<Boolean> validaBoolean = Arrays.asList(Boolean.TRUE, Boolean.FALSE);
		
		Map<String, Object> request = exchange.getIn().getHeaders();
		Boolean documentoVigente = null;

		if (!request.isEmpty()) {
			if (request.containsKey("documento_vigente")) {
				documentoVigente = BooleanUtils.toBooleanObject((String) request.get("documento_vigente"));
				if (validaBoolean.contains(documentoVigente)) {
					exchange.getIn().setHeader("documentoVigenteRequest", (documentoVigente = true) ? 1 : 2);
					return true;
				} 
			} else {
				return true;
			}
		}

		List<Campo> campos = new ArrayList<Campo>();
		campos.add(new Campo(DOCUMENTO_VIGENTE, "Campo informado contém valor inválido"));
		exchange.getIn().setBody(campos, Map.class);
		exchange.getIn().setHeader("documentoVigenteRequest", documentoVigente);
		exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		return false;
	}
}