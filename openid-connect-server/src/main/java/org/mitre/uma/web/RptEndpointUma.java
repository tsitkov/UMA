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
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;

import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.mitre.oauth2.service.OAuth2TokenEntityService;
import org.mitre.oauth2.service.IntrospectionAuthorizer;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.service.UserInfoService;
import org.mitre.openid.connect.view.HttpCodeView;
import org.mitre.uma.model.ResourceSetEntityUma;
import org.mitre.uma.model.RptEntityUma;
import org.mitre.uma.repository.ResourceSetRepositoryUma;
import org.mitre.uma.repository.PermissionRepositoryUma;
import org.mitre.uma.repository.RptRepositoryUma;
import org.mitre.uma.model.PermissionEntityUma;


/**
 * RPT Endpoint
 * 
 * @author tsitkov
 *
 */


@Controller
public class RptEndpointUma {

	@Autowired
        private OAuth2TokenEntityService tokenServices;
  
        @Autowired
        private UserInfoService userInfoService;

	@Autowired
        private PermissionRepositoryUma permTktRepository;

	@Autowired
        private ResourceSetRepositoryUma resourceSetRepository;

	@Autowired
        private RptRepositoryUma rptRepository;

	public RptEndpointUma() {
        }

	public RptEndpointUma(OAuth2TokenEntityService tokenServices) {
                this.tokenServices = tokenServices;
        }

	private static Logger logger = LoggerFactory.getLogger(RptEndpointUma.class);


	/**
	* Register Permission Tickets as specified in this request
	*
	* Per UMA Core spec:
	* "The authorization server uses the ticket 
	* to look up the details of the previously registered requested permission, 
	* maps the requested permission to operative resource owner policies based on
	* the resource set identifier and scopes associated with it, 
	* potentially requests additional information, and ultimately responds
	* positively or negatively to the request for authorization data.
	*
	* The authorization server bases the issuing of authorization data on resource owner policies."
	*/
	@RequestMapping(value="/rpt_uri", method= {RequestMethod.POST}, produces = {"application/json"})
	public ResponseEntity<HashMap<String, String>>  putInfo(
                        @RequestParam(value="rpt", required=false) String rpt,
                        @RequestParam(value="ticket", required=true) String ticket,
          		@RequestHeader(value="Authorization", required=true ) String aatBearerToken, Model model)
			throws IOException, AuthenticationException, InvalidTokenException {

                HashMap<String, String> m = new  HashMap<String, String>();
                HttpHeaders responseHeaders = new HttpHeaders();

                /* validate AAT */
                String aat = null;
                if (Strings.isNullOrEmpty(aatBearerToken)) {
                        logger.error("Validation failed; token value is null");
                        Map<String,Boolean> entity = ImmutableMap.of("active", Boolean.FALSE);
			m.put("error", "not_authorized");
                        return new ResponseEntity<HashMap<String, String>> (m, responseHeaders,
                                        HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                }
                String[] words = aatBearerToken.split(" ");
                if (words.length == 1 ||  words.length == 2) {
                        if (words[0].toLowerCase().contains("bearer")) {
                                aat = words[1];
                        } else {
                                aat = words[0];
                        }
                }
                if (Strings.isNullOrEmpty(aat)) {
                        logger.error("Validation failed; token value is null");
                        Map<String,Boolean> entity = ImmutableMap.of("active", Boolean.FALSE);
			m.put("error", "not_authorized");
                        return new ResponseEntity<HashMap<String, String>> (m, responseHeaders,
                                        HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                }

		OAuth2AccessTokenEntity aatValue = tokenServices.readAccessToken(aat);
                if (aatValue == null) {
                        logger.error("AAT validation failed;  AAT  not found: " + aat);
			m.put("error", "not_authorized");
			return new ResponseEntity<HashMap<String, String>> (m, responseHeaders,
					HttpStatus.FORBIDDEN); 
                }

		/* validate permission ticket */
		PermissionEntityUma perm = permTktRepository.getByPermissionTicketValue(ticket); 
		if (perm.isExpired() == true) {
                        logger.error("Permission ticket expired.");
			m.put("error", "expired_ticket");
			return new ResponseEntity<HashMap<String, String>> (m, responseHeaders,
					HttpStatus.BAD_REQUEST); 
                }

		/* First,  query scopes registered for that permission ticket*/
		String setId = perm.getSetId();
		Set<String> scope_tkt = perm.getScope();
	
		/* Next,  query scopes registered for that setId */
                ResourceSetEntityUma resourceSet = resourceSetRepository.getBySetId(setId);
                if (resourceSet == null) {
                        logger.error("The provided resource set identifier was not found at the authorization server " + setId);
			m.put("error", "invalid_request_id");  // ???
			return new ResponseEntity<HashMap<String, String>> (m, responseHeaders,
					HttpStatus.BAD_REQUEST); 
		}
		/* Finally, map the requested permission to operative resource owner policies based on
		 * the resource set identifier and scopes associated with it
		 */
                Set<String> scope_rss = resourceSet.getScope();
		if (scope_rss.containsAll(scope_tkt) == false) {
			/* This is a very unlikely event.  Perhaps, should not do this. */
		}

		/* Create an RPT */
		Base64URL rpt_new = null;
                long start6=System.currentTimeMillis();
                String rpt2hash = start6  + ticket;
		try{
                        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
                        hasher.reset();
                        hasher.update(rpt2hash.getBytes("UTF-8"));
                        byte[] hashBytes = hasher.digest();
                        byte[] hashBytesLeftHalf = Arrays.copyOf(hashBytes, hashBytes.length / 2);
                        rpt_new = Base64URL.encode(hashBytesLeftHalf);
                } catch (NoSuchAlgorithmException e) {
                        logger.error("No such algorithm error: ", e);
			return new ResponseEntity<HashMap<String, String>> (null, responseHeaders,
					HttpStatus.INTERNAL_SERVER_ERROR); 
                }

		/* Prep body for responce */
		m.put("rpt", rpt_new.toString());

		/* Save it: populate the rpt table */ 
		RptEntityUma newRpt = new RptEntityUma();
        	newRpt.setAat(aat);
		newRpt.setPermissionTicket(ticket.toString());
		rptRepository.save(newRpt);

		return new ResponseEntity<HashMap<String, String>> (m, responseHeaders, HttpStatus.CREATED);
	}
}
