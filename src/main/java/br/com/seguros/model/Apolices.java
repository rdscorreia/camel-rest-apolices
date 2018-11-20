package br.com.seguros.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class Apolices {

	@JsonProperty("data")
	private List<ApoliceResponse> apolices;

	public List<ApoliceResponse> getApolices() {
		return apolices;
	}

	public void setApolices(List<ApoliceResponse> apolices) {
		this.apolices = apolices;
	}

}
