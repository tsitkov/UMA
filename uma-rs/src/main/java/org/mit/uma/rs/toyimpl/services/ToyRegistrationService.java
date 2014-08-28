package org.mit.uma.rs.toyimpl.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.mit.uma.rs.ASConfiguration;
import org.mit.uma.rs.RegistrationRequest;
import org.mit.uma.rs.RegistrationResponse;
import org.mit.uma.rs.services.DataService;
import org.mit.uma.rs.services.DiscoveryService;
import org.mit.uma.rs.services.RegistrationService;

public class ToyRegistrationService implements RegistrationService {
	
	@Autowired
	private DataService dataSrv;

	@Autowired
	private DiscoveryService discoverySrv;

	public ToyRegistrationService() {
		System.out.print("Instantiating Registration service\n");
	}
	
	public RegistrationResponse performClientRegistration(ASConfiguration config) {
		String rc_reg = config.getResourceSetRegistrationEndpoint();
				
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	    RestTemplate template = new RestTemplate();
	    	    
	    String url = "http://localhost:8080/uma-server/register";
	    String request = "{" +
	    					 "\"client_name\": \"c_5\"," +
	    					 "\"scope\":[\"docs.kantarainitiative.org/uma/scopes/prot.json\"]," +
	    					 "\"redirect_uris\": [\"https://localhost:8081/authz\"]," +
	    					 "\"client_uri\" : \"http://localhost:8081/\"" +
	    				 "}";
/*
	    System.out.print("url:  " + url + "\n");    
	    System.out.print("reqeust:  " + request + "\n");
*/	    
		HttpEntity<String> request_entity = new HttpEntity<String>(request,headers);
/*		ResponseEntity<String> entity = template.postForEntity(url, request_entity, String.class);
*/

		ResponseEntity<RegistrationResponse> entity = template.postForEntity(url, request_entity, RegistrationResponse.class);
		RegistrationResponse response = entity.getBody();
/*		
		System.out.print("response from src:  " + response.getClientId() + "\n");		
		System.out.print("response from src:  " + entity);
*/	
		return response;
	}

}
