package br.com.seguros.route.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import br.com.seguros.model.ApoliceResponse;
import br.com.seguros.model.Apolices;

@Component
public class ResultSetProcessor implements Processor {

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {

		List<ApoliceResponse> apolices = new ArrayList<ApoliceResponse>();

		List<Map<String, String>> results = (List<Map<String, String>>) exchange.getIn().getBody();

		if (results.isEmpty()) {
			exchange.getOut().setHeader(Exchange.CONTENT_TYPE, MediaType.TEXT_PLAIN);
			exchange.getOut().setBody("Nenhum registro encontrado", String.class);
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 204);
			return;
		}

		for (Map<String, String> map : results) {

			ApoliceResponse apolice = new ApoliceResponse();
			apolice.setCpfCnpj(map.get("cpfCnpj"));
			apolice.setDescricao(map.get("descricao"));
			apolice.setIdApolice(String.valueOf(map.get("idapolice")));
			apolice.setNumeroDocumento(map.get("numeroDocumento"));
			apolice.setNomeSegurado(map.get("nomeSegurado"));
			apolices.add(apolice);

		}

		Apolices result = new Apolices();
		result.setApolices(apolices);

		exchange.getOut().setBody(result, Apolices.class);

	}

}
