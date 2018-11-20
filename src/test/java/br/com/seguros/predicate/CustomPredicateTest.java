package br.com.seguros.predicate;

import static org.junit.Assert.assertTrue;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CustomPredicateTest {

	@Autowired
	CamelContext context;

	@Autowired
	CustomPredicate customPredicate;

	@Test
	public void noBusinessId() throws Exception {
		Exchange exchange = new DefaultExchange(context);
		exchange.getIn().setHeader("cpf_cnpj", "9999999999");
		assertTrue(customPredicate.getCompositePredicate().matches(exchange));

	}

	public static void main(String[] args) {
		LocalDate data = new LocalDate();
		System.out.println(data.toDate());

	}

}
