package org.mit.uma.rs;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ASConfiguration {
	
	@JsonProperty("resource_set_registration_endpoint")
	private String resourceSetRegistrationEndpoint;
    
	@JsonProperty("version")
	private String version;
    
	@JsonProperty("user_endpoint")
    private String userEndpoint;

    public String getResourceSetRegistrationEndpoint() {
        return resourceSetRegistrationEndpoint;
    }
    
    public String getVersion() {
    	return version;
    }
    
    public String getUserEndpoint() {
    	return userEndpoint;
    }
}
