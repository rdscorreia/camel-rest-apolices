package br.com.seguros.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({ "id-apolice", "cpf-cnpj", "numero-documento", "descricao", "nome-segurado" })
public class Apolice {

	@JsonProperty("id-apolice")
	private Integer idApolice;

	@JsonProperty("cpf-cnpj")
	private String cpfCnpj;

	@JsonProperty("numero-documento")
	private String numeroDocumento;

	@JsonProperty("descricao")
	private String descricao;

	@JsonProperty("nome-segurado")
	private String nomeSegurado;

	public Integer getIdApolice() {
		return idApolice;
	}

	public void setIdApolice(Integer idApolice) {
		this.idApolice = idApolice;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNomeSegurado() {
		return nomeSegurado;
	}

	public void setNomeSegurado(String nomeSegurado) {
		this.nomeSegurado = nomeSegurado;
	}

}
