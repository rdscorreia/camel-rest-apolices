package br.com.seguros.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.commons.lang3.BooleanUtils;

import br.com.seguros.model.Campo;

public class ValidaRequestPredicate implements Predicate {

	private static final String CAMPO_INFORMADO_CONTEM_VALOR_INVALIDO = "Campo informado contém valor inválido";
	private static final String CPF_CNPJ = "cpf_cnpj";
	private static final int CNPJ_LENGHT = 14;
	private static final int CPF_LENGHT = 11;

	private static final String DOCUMENTO_VIGENTE = "documento_vigente";
	private static final String COMPANHIA = "companhia";

	@Override
	public boolean matches(Exchange exchange) {
		Map<String, Object> request = exchange.getIn().getHeaders();
		List<Campo> campos = new ArrayList<Campo>();

		if (!request.isEmpty()) {
			campos.addAll(validaHeaderCpfCnpj(request, exchange));
			campos.addAll(validaHeaderDocumentoVigente(request, exchange));
			campos.addAll(validaHeaderCompanhia(request, exchange));
		}

		if (!campos.isEmpty()) {
			exchange.getIn().setBody(campos, Map.class);
			exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
			return false;
		}
		return true;
	}

	private List<Campo> validaHeaderCpfCnpj(Map<String, Object> request, Exchange exchange) {
		String cpfCnpj = null;
		List<Campo> campos = new ArrayList<Campo>();

		if (!request.containsKey("cpf_cnpj")) {
			campos.add(new Campo(CPF_CNPJ, CAMPO_INFORMADO_CONTEM_VALOR_INVALIDO));
			return campos;
		}

		cpfCnpj = (String) request.get("cpf_cnpj");
		if (!validaCpfCnpj(cpfCnpj)) {
			campos.add(new Campo(CPF_CNPJ, CAMPO_INFORMADO_CONTEM_VALOR_INVALIDO));
			return campos;
		}

		exchange.getIn().setHeader("cpfCnpjRequest", cpfCnpj);
		return campos;
	}

	private List<Campo> validaHeaderDocumentoVigente(Map<String, Object> request, Exchange exchange) {

		List<Boolean> validaBoolean = Arrays.asList(Boolean.TRUE, Boolean.FALSE);
		Boolean documentoVigente = null;
		List<Campo> campos = new ArrayList<Campo>();

		if (!request.containsKey("documento_vigente")) {
			return campos;
		}

		documentoVigente = BooleanUtils.toBooleanObject((String) request.get("documento_vigente"));
		if (!validaBoolean.contains(documentoVigente)) {
			campos.add(new Campo(DOCUMENTO_VIGENTE, CAMPO_INFORMADO_CONTEM_VALOR_INVALIDO));
			return campos;
		}

		exchange.getIn().setHeader("documentoVigenteRequest", (documentoVigente = true) ? 1 : 2);
		return campos;
	}

	@SuppressWarnings("unchecked")
	private List<Campo> validaHeaderCompanhia(Map<String, Object> request, Exchange exchange) {

		List<String> validaCompanhia = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
		List<String> companhias = new ArrayList<String>();
		List<Campo> campos = new ArrayList<Campo>();

		if (!request.containsKey("documento_vigente")) {
			return campos;
		}

		if (request.get("companhia") instanceof String) {
			companhias.add((String) request.get("companhia"));
			if (!validaCompanhia.containsAll(companhias)) {
				campos.add(new Campo(COMPANHIA, CAMPO_INFORMADO_CONTEM_VALOR_INVALIDO));
				return campos;
			}
		} else if (request.get("companhia") instanceof List) {
			companhias.addAll((Collection<? extends String>) request.get("companhia"));
			if (!validaCompanhia.containsAll(companhias)) {
				campos.add(new Campo(COMPANHIA, CAMPO_INFORMADO_CONTEM_VALOR_INVALIDO));
				return campos;
			}
		}

		exchange.getIn().setHeader("companhiaRequest", companhias);
		return campos;
	}

	// Mover para uma classe Utils
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
