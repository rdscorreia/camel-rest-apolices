package br.com.seguros.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class ApiConfig extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		restConfiguration()
	        .contextPath("/apolices")
	        .apiContextPath("/api-doc")
	            .apiProperty("api.title", "Camel REST API")
	            .apiProperty("api.version", "1.0")
	            .apiProperty("cors", "true")
	            .apiContextRouteId("doc-api")
	        .bindingMode(RestBindingMode.off);
	}

}
