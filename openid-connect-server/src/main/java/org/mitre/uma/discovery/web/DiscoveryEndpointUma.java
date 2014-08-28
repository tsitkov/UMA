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
package org.mitre.uma.discovery.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mitre.openid.connect.config.ConfigurationPropertiesBean;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.common.base.Strings;
import org.springframework.ui.ExtendedModelMap;

/**
 * 
 * Handle UMA Discovery for proof-of-concept UMA code.
 * 
 * @author tsitkov
 *
 */

@Controller
public class DiscoveryEndpointUma {


        private static Logger logger = LoggerFactory.getLogger(DiscoveryEndpointUma.class);

        @Autowired
        private ConfigurationPropertiesBean config;

        @RequestMapping("/.well-known/uma-configuration")
        public String providerConfiguration(Model model) {

                String baseUrl = config.getIssuer();
                /* Per section 1.4 of UMA core required and some optional params */

                Map<String, Object> m = new HashMap<String, Object>();

                m.put("issuer",  "http://localhost:8080/uma-server/");
                m.put("version", "1.0"); 
	        m.put("pat_profiles_supported", "bearer");
	        m.put("aat_profiles_supported", "bearer");
	        m.put("rpt_profiles_supported", "bearer");
	        m.put("pat_grant_types_supported", "authorization_code");
	        m.put("aat_grant_types_supported", "authorization_code");
	        m.put("token_endpoint", "http://localhost:8080/uma-server/token/");
	        m.put("user_endpoint", "http://localhost:8080/uma-server/authorize");
	        m.put("introspection_endpoint", "http://localhost:8080/uma-server/authorize/");
	        m.put("resource_set_registration_endpoint", "http://localhost:8080/uma-server/resource");
	        m.put("dynamic_client_endpoint", "http://localhost:8080/uma-server/dyncl_uri");
	        m.put("rpt_endpoint", "http://localhost:8080/uma-server/rpt_uri");
	        m.put("authorization_request_endpoint", "http://localhost:8080/uma-server/authorize");
	        //m.put("authorization_request_endpoint", "http://localhost:8080/uma-server/perm_uri");

                model.addAttribute("entity", m);

                return "jsonEntityView";

        }
}
