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
package org.mitre.uma.client.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mitre.uma.config.ServerConfigurationUma;

import org.mitre.uma.rs.service.impl.DynamicServerConfigurationServiceUma;
//import org.mitre.openid.connect.web.ProtectedResourceRegistrationEndpoint;
import org.springframework.ui.Model;

/**
 * @author tsitkov
 */

/* Server configuration test for the UMA proof-of-concept code */

public class TestStaticServerConfigurationServiceUma {

	private DynamicServerConfigurationServiceUma service;
	private String issuer = "http://localhost:8080/uma-server/";

	/**
	 * All valid parameters
	 */
	@Test
	public void getConfiguration() throws Exception {

            service = new DynamicServerConfigurationServiceUma();
            ServerConfigurationUma conf = service.getServerConfiguration(issuer);

//            assertEquals(issuer, conf.getIssuer());
  //          assertEquals("1.0", conf.getVersion());
            /* TODO: add the rest of params */

        //    String jstr = "{'client_name':'XXX'}";
          //  Model m;
            // ProtectedResourceRegistrationEndpoint client = new ProtectedResourceRegistrationEndpoint();
             //client.registerNewProtectedResource(jstr, m);

        }


	/**
	 * Invalid issuer.
	 */
	@Test
	public void getConfiguration_badIssuer() {

             service = new DynamicServerConfigurationServiceUma();
             ServerConfigurationUma result =
                 service.getServerConfiguration("http://localhost:8080/uma-badserver/");
    
    //         assertThat(result, is(nullValue()));
	}

}
