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

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Date;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import com.nimbusds.jose.util.Base64URL;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.ModelMap;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.mitre.openid.connect.view.JsonEntityView;
import org.mitre.oauth2.service.IntrospectionAuthorizer;
import org.mitre.oauth2.service.OAuth2TokenEntityService;
import org.mitre.oauth2.model.AuthenticationHolderEntity;
import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.service.UserInfoService;
import org.mitre.openid.connect.view.HttpCodeView;
import org.mitre.uma.model.ResourceSetEntityUma;
import org.mitre.uma.repository.ResourceSetRepositoryUma;

/**
 * Resource Set Registration Endpoint
 * 
 * @author tsitkov
 *
 */


@Controller
@RequestMapping(value="/resource_set_registration/resource_set")
public class ResourceSetRegistrationEndpointUma {

	@Autowired
        private OAuth2TokenEntityService tokenServices;
  
        @Autowired
        private UserInfoService userInfoService;

	@Autowired
        private ResourceSetRepositoryUma rssRepository;

	public ResourceSetRegistrationEndpointUma() {
        }

	public ResourceSetRegistrationEndpointUma(OAuth2TokenEntityService tokenServices) {
                this.tokenServices = tokenServices;
        }

	private static Logger logger = LoggerFactory.getLogger(ResourceSetRegistrationEndpointUma.class);

	/**
	 * List Resource Set as specified in this request
	 */
	@RequestMapping(value="/{rsId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<HashMap<String, String>>  getInfo( @PathVariable("rsId") String rsId,
			Model model) throws IOException, AuthenticationException, InvalidTokenException {

		HashMap<String, String> m = new  HashMap<String, String>();
   		HttpHeaders responseHeaders = new HttpHeaders();

                ResourceSetEntityUma resourceSet = rssRepository.getByRsId(rsId);
                if (resourceSet ==  null) {
			m.put("status", "not found");
			logger.error("Resource set has not been registered.");
			return new ResponseEntity<HashMap<String, String>> (m, responseHeaders, HttpStatus.NOT_FOUND);
		}

		/* Prep body for responce */
		m.put("status", "created");
		m.put("_id", resourceSet.getSetId());
		m.put("_rev", resourceSet.getRevision());

		responseHeaders.set("ETag", resourceSet.getSetId());
		return new ResponseEntity<HashMap<String, String>> (m, responseHeaders, HttpStatus.OK);
	}

	/**
	 * Create a new/Register Resource Set as specified in this request
	 */
	@RequestMapping(value="/{rsId}", method={RequestMethod.PUT}, produces={"application/json"})
	public ResponseEntity<HashMap<String, String>>  putInfo(@PathVariable("rsId") String rsId,
			@RequestParam(value="scopes", required=true) Set<String> rssScope,
                        @RequestParam(value="name", required=true) String rssName,
			@RequestHeader(value="Authorization", required=true) String authBearerToken, Model model) throws IOException {

		HashMap<String, String> m = new HashMap<String, String>();
   		HttpHeaders responseHeaders = new HttpHeaders();

		/* validate PAT */
		String pat = null; 
                if (Strings.isNullOrEmpty(authBearerToken)) {
                        logger.error("Validation failed; token value is null");
                        Map<String,Boolean> entity = ImmutableMap.of("active", Boolean.FALSE);
			return new ResponseEntity<HashMap<String, String>> (null, responseHeaders,
					HttpStatus.NON_AUTHORITATIVE_INFORMATION /* UNAUTHORIZED,BAD_REQUEST */); 
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
					HttpStatus.NON_AUTHORITATIVE_INFORMATION /* UNAUTHORIZED,BAD_REQUEST */); 
                }

		OAuth2AccessTokenEntity patValue = tokenServices.readAccessToken(pat);
                if (patValue == null) {
                        logger.error("PAT validation failed;  PAT  not found: " + pat);
			return new ResponseEntity<HashMap<String, String>> (null, responseHeaders,
					HttpStatus.UNAUTHORIZED /* BAD_REQUEST */); 
                }

		/* Prep the resorce owner info */
		AuthenticationHolderEntity authnHolder = patValue.getAuthenticationHolder();
		OAuth2Authentication auth = authnHolder.getAuthentication();
                String username = auth.getName();
                UserInfo userInfo = userInfoService.getByUsernameAndClientId(username, auth.getOAuth2Request().getClientId());
                if (userInfo == null) {
                        logger.error("getInfo failed; user not found: " + username);
			return new ResponseEntity<HashMap<String, String>> (null, responseHeaders,
					HttpStatus.NOT_FOUND/* BAD_REQUEST */); 
                }

		/* Create resource set id and revision */
		Base64URL rssId = null, rssRev = null;
		try{
			MessageDigest hasher = MessageDigest.getInstance("SHA-256");
			Set<String> sortedScopes = new TreeSet(rssScope);
                	String rssId2hash = rssName + sortedScopes.toString();
			hasher.reset();
			hasher.update(rssId2hash.getBytes("UTF-8"));
			byte[] hashBytes = hasher.digest();
			byte[] hashBytesLeftHalf = Arrays.copyOf(hashBytes, hashBytes.length / 2);
			rssId = Base64URL.encode(hashBytesLeftHalf);

			hasher.reset();
			hasher.update(pat.getBytes("UTF-8"));
			hashBytes = hasher.digest();
			hashBytesLeftHalf = Arrays.copyOf(hashBytes, hashBytes.length / 2);
			rssRev = Base64URL.encode(hashBytesLeftHalf);
		} catch (NoSuchAlgorithmException e) {
			logger.error("No such algorithm error: ", e);
			return new ResponseEntity<HashMap<String, String>> (null, responseHeaders,
					HttpStatus.INTERNAL_SERVER_ERROR /* NOT_IMPLEMENTED, OTHER */); 
		}

		/* Save it: populate the resource_set table */ 
                /* First, make sure this is not a duplicate */
                /* validate resource id */
                ResourceSetEntityUma resourceSet = rssRepository.getBySetIdAndRev(rssId.toString(), rssRev.toString());
                if (resourceSet !=  null) {
			logger.error("Resource set has already been registered.");
			return new ResponseEntity<HashMap<String, String>> (m, responseHeaders, HttpStatus.PRECONDITION_FAILED);
		}

		/* Prep body for responce */
		m.put("status", "created");
		m.put("_id", rssId.toString());
		m.put("_rev", rssRev.toString());

                  
		ResourceSetEntityUma newRss = new ResourceSetEntityUma();
        	newRss.setRsId(rsId);
        	newRss.setPat(pat);
		newRss.setServerId(patValue.getClient().getClientName());
		newRss.setName(rssName);
		newRss.setScope(rssScope);
		newRss.setRevision(rssRev.toString());
		newRss.setSetId(rssId.toString());
		newRss.setOwnerId(userInfo.getSub());
		newRss.setOwnerName(userInfo.getName());
                rssRepository.save(newRss);

		responseHeaders.set("ETag", rssRev.toString());
		return new ResponseEntity<HashMap<String, String>> (m, responseHeaders, HttpStatus.CREATED);
	}
}
