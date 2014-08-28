package org.mit.uma.rs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequest {
	
	@JsonProperty("client_name")
	String clientName;

	@JsonProperty("scope")
	String scope;

	@JsonProperty("redirect_uris")
	String redirectUris;

	@JsonProperty("client_uri")
	String clientUri;
		
	public String getClientName() {
		return clientName;
	}
	
	public String getScope() {
		return scope;
	}
	
	public String getRedirectUris() {
		return redirectUris;
	}

	public String getClientUri() {
		return clientUri;
	}
}
