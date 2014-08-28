package org.mit.uma.rs.services;

import java.util.ArrayList;

import org.mit.uma.rs.ASConfiguration;

public interface DataService {
	
	public ArrayList<String> getUsers();
	
	public ArrayList<String> getAzServers();
	
	public ASConfiguration getAzServerConfiguration(String host);
	
	public Boolean addAzServerConfiguration(String host, ASConfiguration config);
	
}
