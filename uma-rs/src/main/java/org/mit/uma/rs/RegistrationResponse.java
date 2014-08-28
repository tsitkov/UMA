package org.mit.uma.rs;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class RegistrationResponse {

	@JsonProperty("client_id")
	String clientId;

	@JsonProperty("client_secret")
	String clientSecret;
	
	@JsonProperty("client_secret_expires_at")
	String clientSecretExpiresAt;

	@JsonProperty("client_id_issued_at")
	String clientIdIssuedAt;

	@JsonProperty("registration_access_token")
	String registrationAccessToken;

	@JsonProperty("registration_client_uri")
	String registrationClientUri;

	@JsonProperty("redirect_uris")
	ArrayList<String> redirectUris;

	@JsonProperty("client_name")
	String clientName;

	@JsonProperty("token_endpoint_auth_method")
	String tokenEndpointAuthMethod;

	@JsonProperty("scope")
	String scope;

	@JsonProperty("grant_types")
	ArrayList<String> grantTypes;

	@JsonProperty("response_types")
	ArrayList<String> responseTypes;

	@JsonProperty("default_acr_values")
	ArrayList<String> defaultAcrValues;
	
	public String getClientId() { 
		return clientId;
	}
	
	public String getClientSecret(){
		return clientSecret;
	}
		
	public String getClientSecretExpiresAt() {
		return clientSecretExpiresAt;
	}
	
	public String getClientIdIssuedAt() {
		return clientIdIssuedAt;
	}
	
	public String getRegistrationAccessToken() {
		return registrationAccessToken;
	}
	
	public String getRegistrationClientUri() {
		return registrationClientUri;
	}
	
	public ArrayList<String> getRedirectUris() {
		return redirectUris;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public String getTokenEndpointAuthMethod() {
		return tokenEndpointAuthMethod;
	}
	
	public String getScope() {
		return scope;
	}
	
	ArrayList<String> getGrantTypes() {
		return grantTypes;
	}
	
	ArrayList<String> getResponseTypes() {
		return responseTypes;
	}
	
	ArrayList<String> getDefaultAcrValues() {
		return defaultAcrValues;
	}

}
