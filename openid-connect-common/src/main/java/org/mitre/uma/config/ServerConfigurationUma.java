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
package org.mitre.uma.config;

import java.util.List;

/**
 * 
 * Container class for a view of a server's configuration
 * 
 * @author tsitkov
 * 
 */
public class ServerConfigurationUma {


	private String issuer;
        private String version;
        private String pat_profiles_supported;
        private String aat_profiles_supported;
        private String rpt_profiles_supported;
        private String aat_grant_types_supported; // must be  List<String>
        private String pat_grant_types_supported;
        private String token_endpoint;
        private String user_endpoint;
        private String introspection_endpoint;
        private String resource_set_registration_endpoint;
        private String dynamic_client_endpoint;
        private String rpt_endpoint;
        private String authorization_request_endpoint;

	/**
	 * Issuer
	 */
        public String getIssuer() {
                return issuer;
        }
        public void setIssuer(String issuer) {
                this.issuer = issuer;
        }


	/**
	 * UMA version
	 */
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public String getPatProfilesSupported() {
		return pat_profiles_supported;
	}
	public void setPatProfilesSupported(String pat_profiles_supported) {
		this.pat_profiles_supported = pat_profiles_supported;
	}

	public String getAatProfilesSupported() {
		return aat_profiles_supported;
	}
	public void setAatProfilesSupported(String aat_profiles_supported) {
		this.aat_profiles_supported = aat_profiles_supported;
	}

	public String getRptProfilesSupported() {
		return rpt_profiles_supported;
	}
	public void setRptProfilesSupported(String rpt_profiles_supported) {
		this.rpt_profiles_supported = rpt_profiles_supported;
	}

	public String getPatGrantTypesSupported() {
		return pat_grant_types_supported;
	}
	public void setPatGrantTypesSupported(String pat_grant_types_supported) {
		this.pat_grant_types_supported = pat_grant_types_supported;
	}

	public String getAatGrantTypesSupported() {
		return aat_grant_types_supported;
	}
	public void settAatGrantTypesSupported(String aat_grant_types_supported) {
		this.aat_grant_types_supported = aat_grant_types_supported;
	}

	public String getTokenEndpoint() {
		return token_endpoint;
	}
	public void setTokenEndpoint(String token_endpoint) {
		this.token_endpoint = token_endpoint;
	}

	public String getUserEndpoint() {
		return user_endpoint;
	}
	public void setUserEndpoint(String user_endpoint) {
		this.user_endpoint = user_endpoint;
	}

	public String getIntrospectionEndpoint() {
		return introspection_endpoint;
	}
	public void setIntrospectionEndpoint(String introspection_endpoint) {
		this.introspection_endpoint = introspection_endpoint;
	}

	public String getResourceSetRegistrationEndpoint() {
		return resource_set_registration_endpoint;
	}
	public void setResourceSetRegistrationEndpoint(String resource_set_registration_endpoint) {
		this.resource_set_registration_endpoint = resource_set_registration_endpoint;
	}

	public String getDynamicElientEndpoint() {
		return dynamic_client_endpoint;
	}
	public void setDynamicClientEndpoint(String dynamic_client_endpoint) {
		this.dynamic_client_endpoint= dynamic_client_endpoint;
	}

	public String getRptEndpoint() {
		return rpt_endpoint;
	}
	public void setRptEndpoint(String rpt_endpoint) {
		this.rpt_endpoint = rpt_endpoint;
	}

	public String getAuthorizationRequestEndpoint() {
		return authorization_request_endpoint;
	}
	public void setAuthorizationRequestEndpoint(String version) {
		this.authorization_request_endpoint = authorization_request_endpoint;
	}


        // TODO: add all UMA config params
}
