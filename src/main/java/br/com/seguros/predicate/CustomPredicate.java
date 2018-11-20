package br.com.seguros.predicate;

import org.apache.camel.Predicate;
import org.apache.camel.builder.PredicateBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomPredicate {

	public Predicate getCompositePredicate() {

		Predicate validaRequestPredicate = new ValidaRequestPredicate();
 		Predicate compositePredicate = PredicateBuilder.and(validaRequestPredicate);

		return compositePredicate;
	}

}
