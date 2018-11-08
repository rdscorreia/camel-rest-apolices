package br.com.seguros;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApolicesApplicationTests {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void verificaUrlComParametros() {
		ResponseEntity<String> response = restTemplate.getForEntity("/apolices/?cpf_cnpj=99999999999&documento_vigente=true&companhia=1&companhia=2", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		String s = response.getBody();
		assertThat(s.contains("data"));
	}

	@Test
	public void verificaCpfOK() {
		ResponseEntity<String> response = restTemplate.getForEntity("/apolices/?cpf_cnpj=99999999999", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		String s = response.getBody();
		assertThat(s.contains("data"));
	}
	
	@Test
	public void verificaCpfNOK() {		
		ResponseEntity<String> response = restTemplate.getForEntity("/apolices/?cpf_cnpj=9999999999", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		String s = response.getBody();
		assertThat(s.contains("cpf_cnpj"));
	}
	
	@Test
	public void verificaDocumentoVigenteNOK() {		
		ResponseEntity<String> response = restTemplate.getForEntity("/apolices/?cpf_cnpj=9999999999&documento_vigente=bla", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		String s = response.getBody();
		assertThat(s.contains("documento_vigente"));
	}

	@Test
	public void verificaCompanhiaNOK() {		
		ResponseEntity<String> response = restTemplate.getForEntity("/apolices/?cpf_cnpj=9999999999&documento_vigente=true,&companhia=R", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		String s = response.getBody();
		assertThat(s.contains("companhia"));
	}
	
	@Test
	public void verificaUrlSemParametro() {		
		ResponseEntity<String> response = restTemplate.getForEntity("/apolices/", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		String s = response.getBody();
		assertThat(s.contains(""));
	}

}
