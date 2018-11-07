package br.com.seguros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="br.com.seguros")
public class ApolicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApolicesApplication.class, args);
	}
}
