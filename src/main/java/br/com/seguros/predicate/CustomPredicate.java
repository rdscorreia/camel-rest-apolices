package br.com.seguros.predicate;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.Predicate;
import org.apache.camel.builder.PredicateBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomPredicate {

	public Predicate getCompositePredicate() {

		/*
		Predicate predicateHeaderCfpCnpj = new PredicatedicadiHeaderCfpCnpj();
		Predicate predicateHeaderDocumentoVigente = new PredicateHeaderDocumentoVigente();
		Predicate predicateHeaderCompanhias = new PredicateHeaderCompanhias();

		List<Predicate> predicates = Arrays.asList(predicateHeaderCfpCnpj, predicateHeaderDocumentoVigente,	predicateHeaderCompanhias);
		
		Predicate compositePredicate = PredicateBuilder.and(predicates);
		*/
		
		Predicate validaRequestPredicate = new ValidaRequestPredicate();
 		Predicate compositePredicate = PredicateBuilder.and(validaRequestPredicate);

		return compositePredicate;
	}

}
