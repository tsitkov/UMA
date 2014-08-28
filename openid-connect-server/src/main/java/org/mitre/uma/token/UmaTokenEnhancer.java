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
package org.mitre.uma.token;

import java.util.Date;
import java.util.UUID;

import org.mitre.jwt.signer.service.JwtSigningAndValidationService;
import org.mitre.jwt.signer.service.impl.JWKSetCacheService;
import org.mitre.jwt.signer.service.impl.SymmetricCacheService;
import org.mitre.oauth2.model.ClientDetailsEntity;
import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.mitre.oauth2.service.ClientDetailsEntityService;
import org.mitre.openid.connect.config.ConfigurationPropertiesBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class UmaTokenEnhancer implements TokenEnhancer {

	Logger logger = LoggerFactory.getLogger(UmaTokenEnhancer.class);

	@Autowired
	private ConfigurationPropertiesBean configBean;

	@Autowired
	private JwtSigningAndValidationService jwtService;

	@Autowired
	private ClientDetailsEntityService clientService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,	OAuth2Authentication authentication) {

		OAuth2AccessTokenEntity token = (OAuth2AccessTokenEntity) accessToken;
		OAuth2Request originalAuthRequest = authentication.getOAuth2Request();

		String clientId = originalAuthRequest.getClientId();
		ClientDetailsEntity client = clientService.loadClientByClientId(clientId);

		JWTClaimsSet claims = new JWTClaimsSet();

		claims.setIssuer(configBean.getIssuer());

		claims.setIssueTime(new Date());

		claims.setExpirationTime(token.getExpiration());

		claims.setJWTID(UUID.randomUUID().toString()); // set a random NONCE in the middle of it

		JWSAlgorithm signingAlg = jwtService.getDefaultSigningAlgorithm();

		SignedJWT signed = new SignedJWT(new JWSHeader(signingAlg), claims);

		jwtService.signJwt(signed);

		token.setJwt(signed);

                if (!originalAuthRequest.getScope().contains("docs.kantarainitiative.org/uma/scopes/prot.json")) {
	            logger.warn("PAT issuance - bad scope.");
                    return null;
                }

		return token;
	}
	public ConfigurationPropertiesBean getConfigBean() {
		return configBean;
	}

	public void setConfigBean(ConfigurationPropertiesBean configBean) {
		this.configBean = configBean;
	}

	public JwtSigningAndValidationService getJwtService() {
		return jwtService;
	}

	public void setJwtService(JwtSigningAndValidationService jwtService) {
		this.jwtService = jwtService;
	}

	public ClientDetailsEntityService getClientService() {
		return clientService;
	}

	public void setClientService(ClientDetailsEntityService clientService) {
		this.clientService = clientService;
	}
}
