package org.mit.uma.rs.toyimpl.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mit.uma.rs.ASConfiguration;
import org.mit.uma.rs.services.DataService;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ToyDataService implements DataService {
	
	private String path = "C:/tmp/rs/config.json";
	private Map<String,ASConfiguration> servers;
	
	public ToyDataService() {
		System.out.print("Instantiating ToyDataService\n");
	}

	public ArrayList<String> getUsers() {
		
		return null;
	}
	
	public ArrayList<String> getAzServers() {
		return null;
	}
	
	public ASConfiguration getAzServerConfiguration(String host) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				File confFile = new File(this.path);
				if (confFile.exists()) {
					this.servers = mapper.readValue(confFile, new TypeReference<Map<String, ASConfiguration>>() { });
				} else {
					this.servers = new HashMap<String,ASConfiguration>();
				}
			} catch (JsonGenerationException e) { 
				e.printStackTrace(); 
			} catch (JsonMappingException e) { 
				e.printStackTrace(); 
			} catch (IOException e) { 
				e.printStackTrace(); 
			}
		
		return null;
	}
	
	public Boolean addAzServerConfiguration(String host, ASConfiguration config) {
		if (this.servers.put(host, config) != null) {
			return false; 
		}
		
		/* write data to file */
		ObjectMapper mapper = new ObjectMapper();
		try {
			File confFile = new File(this.path);
			mapper.writeValue(confFile, this.servers);
		} catch (JsonGenerationException e) { 
			e.printStackTrace(); 
		} catch (JsonMappingException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		}		
		return true;
	}

}
