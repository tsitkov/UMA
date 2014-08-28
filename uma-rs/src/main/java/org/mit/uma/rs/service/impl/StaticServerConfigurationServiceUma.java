/*******************************************************************************
 * Copyright 2014 The MITRE Corporation
 *   and the MIT Kerberos and Internet Trust Consortium
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/**
 * 
 */
package org.mitre.uma.client.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.mitre.uma.client.service.ServerConfigurationServiceUma;
import org.mitre.uma.config.ServerConfigurationUma;

import static org.junit.Assert.assertEquals;

/**
 * Statically configured server configuration service that maps issuer URLs to server configurations to use at that issuer.
 * 
 * @author tsitkov
 *
 */
public class StaticServerConfigurationServiceUma implements ServerConfigurationServiceUma {

	// map of issuer url -> server configuration information
	//private Map<String, ServerConfiguration> servers;
	private Map<String, ServerConfigurationUma> servers;

	/**
	 * @return the servers
	 */
	public Map<String, ServerConfigurationUma> getServers() {
//assertEquals("Z4", "A");
		return servers;
	}

	/**
	 * @param servers the servers to set
	 */
	public void setServers(Map<String, ServerConfigurationUma> servers) {
//assertEquals(servers, "A");
		this.servers = servers;
	}

	/* (non-Javadoc)
	 * @see org.mitre.openid.connect.client.service.ServerConfigurationService#getServerConfiguration(java.lang.String)
	 */
	@Override
	public ServerConfigurationUma getServerConfiguration(String issuer) {
//assertEquals(issuer, "A");
		return servers.get(issuer);
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
//assertEquals("Z1", "A");
		if (servers == null || servers.isEmpty()) {
			throw new IllegalArgumentException("Servers map cannot be null or empty.");
		}

	}

}
