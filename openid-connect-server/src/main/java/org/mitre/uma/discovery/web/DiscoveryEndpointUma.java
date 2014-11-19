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
import com.google.common.collect.Lists;
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

                if (!baseUrl.endsWith("/")) {
                        logger.warn("Configured issuer doesn't end in /, adding for discovery: " + baseUrl);
                        baseUrl = baseUrl.concat("/");
                }

                /* Per section 1.4 of UMA core required and some optional params */

                Map<String, Object> m = new HashMap<String, Object>();

                m.put("issuer",  baseUrl + "");
                m.put("version", "1.0"); 
	        m.put("pat_profiles_supported", "bearer");
	        m.put("aat_profiles_supported", "bearer");
	        m.put("rpt_profiles_supported", "bearer");
	        m.put("registration_endpoint", baseUrl + "register");
	        m.put("pat_grant_types_supported", "authorization_code");
	        m.put("aat_grant_types_supported", Lists.newArrayList("implicit","authorization_code"));
	        // m.put("aat_grant_types_supported", "authorization_code");
	        m.put("token_endpoint", baseUrl + "token");
	        m.put("user_endpoint", baseUrl + "authorize");
	        m.put("introspection_endpoint", baseUrl + "introspection");
	        m.put("resource_set_registration_endpoint", baseUrl + "resource_set_registration");
	        m.put("permission_registration_endpoint", baseUrl + "permission_registration");
	        //m.put("resource_set_registration_endpoint", baseUrl + "resource");
	        m.put("dynamic_client_endpoint", baseUrl + "dyncl_uri");
	        m.put("rpt_endpoint", baseUrl + "rpt_uri");
	        m.put("authorization_request_endpoint", baseUrl + "authorize");
	        //m.put("authorization_request_endpoint", baseUrl + "perm_uri");

                model.addAttribute("entity", m);

                return "jsonEntityView";

        }
}
