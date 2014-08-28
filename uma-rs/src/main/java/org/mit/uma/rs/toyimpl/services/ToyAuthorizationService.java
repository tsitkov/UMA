package org.mit.uma.rs.toyimpl.services;

import java.util.Arrays;
import org.apache.commons.codec.binary.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.mit.uma.rs.ASConfiguration;
import org.mit.uma.rs.services.AuthorizationService;

public class ToyAuthorizationService implements AuthorizationService {

	public void getPatToken() {
		/*
		  HttpHeaders headers = new HttpHeaders();
		 
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	    RestTemplate template = new RestTemplate();

		
		RestTemplate restTemplate = new RestTemplate();
		
		String auth = "myc3" + ":" + "password";
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
		
		System.out.print("auth: " + encodedAuth);
		
		headers.add("Authorization", "Basic " + encodedAuth);
		String authHeader = "Basic " + new String( encodedAuth );
		
	    String url = "http://192.168.1.9:8080/uma-server/token"; 
		String request = "{" +
        				 "\"grant_type\":\"authorization_code\"," +
        				 "\"code\":\"U50yRs\"," +
        				 "\"redirect_uri\":\"http://192.168.1.9:8081/authz\"," +
        				 "\"scope\":\"http://docs.kantarainitiative.org/uma/scopes/prot.json\"" +
                         "}";
        System.out.print(request);

		HttpEntity<String> request_entity = new HttpEntity<String>(request,headers);
		ResponseEntity<String> entity = template.postForEntity(url, request_entity, String.class);
	*/
		/*
		 * ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET, request, Account.class);
			ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET, request, Account.class);

		 */
		
		//System.out.print("response:  " + entity);
	
	}

}
