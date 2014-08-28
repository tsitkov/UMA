package org.mit.uma.rs.toyimpl.services;

import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.mit.uma.rs.ASConfiguration;
import org.mit.uma.rs.services.DiscoveryService;
import org.mit.uma.rs.services.DataService;



public class ToyDiscoveryService implements DiscoveryService {
	
	@Autowired
	private DataService dataSrv;

	public ToyDiscoveryService() {
		System.out.print("I'm instantiated\n");
	}

	public ASConfiguration getServerAPIs(String host) {
		/* check if we need to get a configuration from AS server's */
		
		System.out.print("host: " + host);
		
		System.out.print("dataSrv: " + this.dataSrv);
		
		
		
		ASConfiguration cfg = this.dataSrv.getAzServerConfiguration(host);
		if (cfg == null) {
			/* pulling data from authorization server */
			String conf_url = host + "/uma-server/.well-known/uma-configuration";
			RestTemplate restTemplate = new RestTemplate();
	        cfg = restTemplate.getForObject(conf_url, ASConfiguration.class);
			if (cfg != null) {
				System.out.print("adding config data");
				this.dataSrv.addAzServerConfiguration(host, cfg);
			}
		}
		
		return cfg;
	}
	
}
