package br.com.seguros.predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

import br.com.seguros.model.Campo;

public class PredicateHeaderCfpCnpj implements Predicate {

	private static final String CPF_CNPJ = "cpf_cnpj";
	private static final int CNPJ_LENGHT = 14;
	private static final int CPF_LENGHT = 11;

	@Override
	public boolean matches(Exchange exchange) {

		Map<String, Object> request = exchange.getIn().getHeaders();
		String cpfCnpj = null;

		if (!request.isEmpty()) {
			if (request.containsKey("cpf_cnpj")) {
				cpfCnpj = (String) request.get("cpf_cnpj");
				if (validaCpfCnpj(cpfCnpj)) {
					exchange.getIn().setHeader("cpfCnpjRequest", cpfCnpj);
					return true;
				}
			}
		}
		List<Campo> campos = new ArrayList<Campo>();
		campos.add(new Campo(CPF_CNPJ, "Campo informado contém valor inválido"));
		exchange.getIn().setBody(campos, Map.class);
		exchange.getIn().setHeader("cpfCnpjRequest", cpfCnpj);
		exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
		return false;
	}

	private boolean validaCpfCnpj(String campo) {
		return (validaCampo(campo) && (isCnpjValido(campo) || isCpfValido(campo)));
	}

	private boolean validaCampo(String campo) {

		return !campo.isEmpty() || campo != null;
	}

	public boolean isCpfValido(final String cpf) {

		if (cpf == null) {
			return false;
		}

		if (!cpf.matches("[0-9]+") || cpf.length() != CPF_LENGHT) {
			return false;
		}

		return true;
	}

	private boolean isCnpjValido(final String cnpj) {

		if (cnpj == null) {
			return false;
		}

		if (!cnpj.matches("[0-9]+") || cnpj.length() != CNPJ_LENGHT) {
			return false;
		}

		return true;
	}

}
