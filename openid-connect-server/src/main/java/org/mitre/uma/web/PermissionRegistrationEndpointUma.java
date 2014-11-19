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
package org.mitre.uma.web;

import java.util.Set;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.ui.ModelMap;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import com.nimbusds.jose.util.Base64URL;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;

import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.mitre.oauth2.service.OAuth2TokenEntityService;
import org.mitre.oauth2.service.IntrospectionAuthorizer;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.service.UserInfoService;
import org.mitre.openid.connect.view.HttpCodeView;
import org.mitre.uma.model.ResourceSetEntityUma;
import org.mitre.uma.repository.ResourceSetRepositoryUma;
import org.mitre.uma.repository.PermissionRepositoryUma;
import org.mitre.uma.model.PermissionEntityUma;


/**
 * Resource Set Registration Endpoint
 * 
 * @author tsitkov
 *
 */


@Controller
public class PermissionRegistrationEndpointUma {

	@Autowired
        private OAuth2TokenEntityService tokenServices;
  
        @Autowired
        private UserInfoService userInfoService;

	@Autowired
        private PermissionRepositoryUma permTktRepository;

	@Autowired
        private ResourceSetRepositoryUma resourceSetRepository;

	public PermissionRegistrationEndpointUma() {
        }

	public PermissionRegistrationEndpointUma(OAuth2TokenEntityService tokenServices) {
                this.tokenServices = tokenServices;
        }

	private static Logger logger = LoggerFactory.getLogger(PermissionRegistrationEndpointUma.class);


	/**
	 * Register Permission Tickets as specified in this request
	 */
	@RequestMapping(value="/permission_registration", method= {RequestMethod.POST}, produces = {"application/json"})
	public ResponseEntity<HashMap<String, String>>  putInfo(
                        @RequestParam(value="resource_set_id", required=true) String rssId,
                        @RequestParam(value="scopes", required=true) Set<String> scope,
          		@RequestHeader(value="Authorization", required=true ) String authBearerToken, Model model) 
			throws IOException, AuthenticationException, InvalidTokenException {

                HashMap<String, String> m = new  HashMap<String, String>();
                HttpHeaders responseHeaders = new HttpHeaders();

                /* validate PAT */
                String pat = null;
                if (Strings.isNullOrEmpty(authBearerToken)) {
                        logger.error("Validation failed; token value is null");
                        Map<String,Boolean> entity = ImmutableMap.of("active", Boolean.FALSE);
                        return new ResponseEntity<HashMap<String, String>> (null, responseHeaders,
                                        HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                }
                String[] words = authBearerToken.split(" ");
                if (words.length == 1 ||  words.length == 2) {
                        if (words[0].toLowerCase().contains("bearer")) {
                                pat = words[1];
                        } else {
                                pat = words[0];
                        }
                }
                if (Strings.isNullOrEmpty(pat)) {
                        logger.error("Validation failed; token value is null");
                        Map<String,Boolean> entity = ImmutableMap.of("active", Boolean.FALSE);
                        return new ResponseEntity<HashMap<String, String>> (null, responseHeaders,
                                        HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                }

		OAuth2AccessTokenEntity patValue = tokenServices.readAccessToken(pat);
                if (patValue == null) {
                        logger.error("PAT validation failed;  PAT  not found: " + pat);
			return new ResponseEntity<HashMap<String, String>> (null, responseHeaders,
					HttpStatus.UNAUTHORIZED); 
                }

		/* validate resource id */
                ResourceSetEntityUma resourceSet = resourceSetRepository.getBySetId(rssId);
                if (resourceSet == null) {
                        logger.error("The provided resource set identifier was not found at the authorization server " + rssId);
			m.put("error", "invalid_request_id");
			return new ResponseEntity<HashMap<String, String>> (m, responseHeaders,
					HttpStatus.BAD_REQUEST); 
		}

		/* validate scope */
		Set<String> scope2check =  new  HashSet<String>();
		Iterator<String> iterator = scope.iterator();
		String sc_next ;
		while(iterator.hasNext()) {
			sc_next = iterator.next();

			scope2check.add(sc_next);
			ResourceSetEntityUma resourceSetByScope = resourceSetRepository.getBySetIdAndScope(rssId, scope2check);
			if (resourceSetByScope == null) {
				logger.error("At least one of the scopes included in the request <" + scope +
				"> was not registered previously by this resource server." + rssId);
				m.put("error", "invalid_scope");
				return new ResponseEntity<HashMap<String, String>> (m, responseHeaders,
									HttpStatus.BAD_REQUEST); 
			}
			scope2check.clear();
		} 
                
		/* Create a permission ticket */
		Base64URL ticket = null;
                long start6=System.currentTimeMillis();
                String rssId2hash = start6 + rssId;
		try{
                        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
                        hasher.reset();
                        hasher.update(rssId2hash.getBytes("UTF-8"));
                        byte[] hashBytes = hasher.digest();
                        byte[] hashBytesLeftHalf = Arrays.copyOf(hashBytes, hashBytes.length / 2);
                        ticket = Base64URL.encode(hashBytesLeftHalf);
                } catch (NoSuchAlgorithmException e) {
                        logger.error("No such algorithm error: ", e);
			return new ResponseEntity<HashMap<String, String>> (null, responseHeaders,
					HttpStatus.INTERNAL_SERVER_ERROR); 
                }

		/* Prep body for responce */
		m.put("ticket", ticket.toString());
		/* Save it: populate the resource_set table */ 
		PermissionEntityUma newTkt = new PermissionEntityUma();
        	newTkt.setPat(pat);
		newTkt.setSetId(rssId);
		newTkt.setScope(scope);
		newTkt.setTicket(ticket.toString());
                permTktRepository.save(newTkt);

		return new ResponseEntity<HashMap<String, String>> (m, responseHeaders, HttpStatus.CREATED);
	}
}
