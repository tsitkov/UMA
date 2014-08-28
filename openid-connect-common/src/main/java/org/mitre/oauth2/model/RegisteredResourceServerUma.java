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
package org.mitre.oauth2.model;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.mitre.jose.JWEAlgorithmEmbed;
import org.mitre.jose.JWEEncryptionMethodEmbed;
import org.mitre.jose.JWSAlgorithmEmbed;
import org.mitre.oauth2.model.ResourceServerDetailsEntityUma;
import org.mitre.oauth2.model.ResourceServerDetailsEntityUma.AppType;
import org.mitre.oauth2.model.ResourceServerDetailsEntityUma.AuthMethod;
import org.mitre.oauth2.model.ResourceServerDetailsEntityUma.SubjectType;
import org.springframework.security.core.GrantedAuthority;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;

public class RegisteredResourceServerUma {

	// these fields are needed in addition to the ones in ResourceServerDetailsEntityUma
	private String registrationAccessToken;
	private String registrationResourceServerUri;
	private Date rsSecretExpiresAt;
	private Date rsIdIssuedAt;
	private ResourceServerDetailsEntityUma rs;

	/**
	 * 
	 */
	public RegisteredResourceServerUma() {
		this.rs = new ResourceServerDetailsEntityUma();
	}

	/**
	 * @param rs
	 */
	public RegisteredResourceServerUma(ResourceServerDetailsEntityUma rs) {
		this.rs = rs;
	}

	/**
	 * @param rs
	 * @param registrationAccessToken
	 * @param registrationResourceServerUri
	 */
	public RegisteredResourceServerUma(ResourceServerDetailsEntityUma rs, String registrationAccessToken, String registrationResourceServerUri) {
		this.rs = rs;
		this.registrationAccessToken = registrationAccessToken;
		this.registrationResourceServerUri = registrationResourceServerUri;
	}

	/**
	 * @return the rs
	 */
	public ResourceServerDetailsEntityUma getResourceServer() {
		return rs;
	}
	/**
	 * @param rs the rs to set
	 */
	public void setResourceServer(ResourceServerDetailsEntityUma rs) {
		this.rs = rs;
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getResourceServerDescription()
	 */
	public String getResourceServerDescription() {
		return rs.getResourceServerDescription();
	}
	/**
	 * @param rsDescription
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setResourceServerDescription(java.lang.String)
	 */
	public void setResourceServerDescription(String rsDescription) {
		rs.setResourceServerDescription(rsDescription);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#isAllowRefresh()
	 */
	public boolean isAllowRefresh() {
		return rs.isAllowRefresh();
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#isReuseRefreshToken()
	 */
	public boolean isReuseRefreshToken() {
		return rs.isReuseRefreshToken();
	}
	/**
	 * @param reuseRefreshToken
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setReuseRefreshToken(boolean)
	 */
	public void setReuseRefreshToken(boolean reuseRefreshToken) {
		rs.setReuseRefreshToken(reuseRefreshToken);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#isDynamicallyRegistered()
	 */
	public boolean isDynamicallyRegistered() {
		return rs.isDynamicallyRegistered();
	}
	/**
	 * @param dynamicallyRegistered
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setDynamicallyRegistered(boolean)
	 */
	public void setDynamicallyRegistered(boolean dynamicallyRegistered) {
		rs.setDynamicallyRegistered(dynamicallyRegistered);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#isAllowIntrospection()
	 */
	public boolean isAllowIntrospection() {
		return rs.isAllowIntrospection();
	}
	/**
	 * @param allowIntrospection
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setAllowIntrospection(boolean)
	 */
	public void setAllowIntrospection(boolean allowIntrospection) {
		rs.setAllowIntrospection(allowIntrospection);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#isSecretRequired()
	 */
	public boolean isSecretRequired() {
		return rs.isSecretRequired();
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#isScoped()
	 */
	public boolean isScoped() {
		return rs.isScoped();
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getResourceServerId()
	 */
	public String getResourceServerId() {
		return rs.getResourceServerId();
	}
	/**
	 * @param rsId
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setResourceServerId(java.lang.String)
	 */
	public void setResourceServerId(String rsId) {
		rs.setResourceServerId(rsId);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getResourceServerSecret()
	 */
	public String getResourceServerSecret() {
		return rs.getResourceServerSecret();
	}
	/**
	 * @param rsSecret
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setResourceServerSecret(java.lang.String)
	 */
	public void setResourceServerSecret(String rsSecret) {
		rs.setResourceServerSecret(rsSecret);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getScope()
	 */
	public Set<String> getScope() {
		return rs.getScope();
	}
	/**
	 * @param scope
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setScope(java.util.Set)
	 */
	public void setScope(Set<String> scope) {
		rs.setScope(scope);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getGrantTypes()
	 */
	public Set<String> getGrantTypes() {
		return rs.getGrantTypes();
	}
	/**
	 * @param grantTypes
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setGrantTypes(java.util.Set)
	 */
	public void setGrantTypes(Set<String> grantTypes) {
		rs.setGrantTypes(grantTypes);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getAuthorizedGrantTypes()
	 */
	public Set<String> getAuthorizedGrantTypes() {
		return rs.getAuthorizedGrantTypes();
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getAuthorities()
	 */
	public Set<GrantedAuthority> getAuthorities() {
		return rs.getAuthorities();
	}
	/**
	 * @param authorities
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setAuthorities(java.util.Set)
	 */
	public void setAuthorities(Set<GrantedAuthority> authorities) {
		rs.setAuthorities(authorities);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getAccessTokenValiditySeconds()
	 */
	public Integer getAccessTokenValiditySeconds() {
		return rs.getAccessTokenValiditySeconds();
	}
	/**
	 * @param accessTokenValiditySeconds
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setAccessTokenValiditySeconds(java.lang.Integer)
	 */
	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		rs.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getRefreshTokenValiditySeconds()
	 */
	public Integer getRefreshTokenValiditySeconds() {
		return rs.getRefreshTokenValiditySeconds();
	}
	/**
	 * @param refreshTokenValiditySeconds
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setRefreshTokenValiditySeconds(java.lang.Integer)
	 */
	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		rs.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getRedirectUris()
	 */
	public Set<String> getRedirectUris() {
		return rs.getRedirectUris();
	}
	/**
	 * @param redirectUris
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setRedirectUris(java.util.Set)
	 */
	public void setRedirectUris(Set<String> redirectUris) {
		rs.setRedirectUris(redirectUris);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getRegisteredRedirectUri()
	 */
	public Set<String> getRegisteredRedirectUri() {
		return rs.getRegisteredRedirectUri();
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getResourceIds()
	 */
	public Set<String> getResourceIds() {
		return rs.getResourceIds();
	}
	/**
	 * @param resourceIds
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setResourceIds(java.util.Set)
	 */
	public void setResourceIds(Set<String> resourceIds) {
		rs.setResourceIds(resourceIds);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getAdditionalInformation()
	 */
	public Map<String, Object> getAdditionalInformation() {
		return rs.getAdditionalInformation();
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getApplicationType()
	 */
	public AppType getApplicationType() {
		return rs.getApplicationType();
	}
	/**
	 * @param applicationType
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setApplicationType(org.mitre.oauth2.model.ResourceServerDetailsEntityUma.AppType)
	 */
	public void setApplicationType(AppType applicationType) {
		rs.setApplicationType(applicationType);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getResourceServerName()
	 */
	public String getResourceServerName() {
		return rs.getResourceServerName();
	}
	/**
	 * @param rsName
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setResourceServerName(java.lang.String)
	 */
	public void setResourceServerName(String rsName) {
		rs.setResourceServerName(rsName);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getTokenEndpointAuthMethod()
	 */
	public AuthMethod getTokenEndpointAuthMethod() {
		return rs.getTokenEndpointAuthMethod();
	}
	/**
	 * @param tokenEndpointAuthMethod
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setTokenEndpointAuthMethod(org.mitre.oauth2.model.ResourceServerDetailsEntityUma.AuthMethod)
	 */
	public void setTokenEndpointAuthMethod(AuthMethod tokenEndpointAuthMethod) {
		rs.setTokenEndpointAuthMethod(tokenEndpointAuthMethod);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getSubjectType()
	 */
	public SubjectType getSubjectType() {
		return rs.getSubjectType();
	}
	/**
	 * @param subjectType
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setSubjectType(org.mitre.oauth2.model.ResourceServerDetailsEntityUma.SubjectType)
	 */
	public void setSubjectType(SubjectType subjectType) {
		rs.setSubjectType(subjectType);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getContacts()
	 */
	public Set<String> getContacts() {
		return rs.getContacts();
	}
	/**
	 * @param contacts
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setContacts(java.util.Set)
	 */
	public void setContacts(Set<String> contacts) {
		rs.setContacts(contacts);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getLogoUri()
	 */
	public String getLogoUri() {
		return rs.getLogoUri();
	}
	/**
	 * @param logoUri
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setLogoUri(java.lang.String)
	 */
	public void setLogoUri(String logoUri) {
		rs.setLogoUri(logoUri);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getPolicyUri()
	 */
	public String getPolicyUri() {
		return rs.getPolicyUri();
	}
	/**
	 * @param policyUri
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setPolicyUri(java.lang.String)
	 */
	public void setPolicyUri(String policyUri) {
		rs.setPolicyUri(policyUri);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getResourceServerUri()
	 */
	public String getResourceServerUri() {
		return rs.getResourceServerUri();
	}
	/**
	 * @param rsUri
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setResourceServerUri(java.lang.String)
	 */
	public void setResourceServerUri(String rsUri) {
		rs.setResourceServerUri(rsUri);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getTosUri()
	 */
	public String getTosUri() {
		return rs.getTosUri();
	}
	/**
	 * @param tosUri
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setTosUri(java.lang.String)
	 */
	public void setTosUri(String tosUri) {
		rs.setTosUri(tosUri);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getJwksUri()
	 */
	public String getJwksUri() {
		return rs.getJwksUri();
	}
	/**
	 * @param jwksUri
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setJwksUri(java.lang.String)
	 */
	public void setJwksUri(String jwksUri) {
		rs.setJwksUri(jwksUri);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getSectorIdentifierUri()
	 */
	public String getSectorIdentifierUri() {
		return rs.getSectorIdentifierUri();
	}
	/**
	 * @param sectorIdentifierUri
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setSectorIdentifierUri(java.lang.String)
	 */
	public void setSectorIdentifierUri(String sectorIdentifierUri) {
		rs.setSectorIdentifierUri(sectorIdentifierUri);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getDefaultMaxAge()
	 */
	public Integer getDefaultMaxAge() {
		return rs.getDefaultMaxAge();
	}
	/**
	 * @param defaultMaxAge
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setDefaultMaxAge(java.lang.Integer)
	 */
	public void setDefaultMaxAge(Integer defaultMaxAge) {
		rs.setDefaultMaxAge(defaultMaxAge);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getRequireAuthTime()
	 */
	public Boolean getRequireAuthTime() {
		return rs.getRequireAuthTime();
	}
	/**
	 * @param requireAuthTime
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setRequireAuthTime(java.lang.Boolean)
	 */
	public void setRequireAuthTime(Boolean requireAuthTime) {
		rs.setRequireAuthTime(requireAuthTime);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getResponseTypes()
	 */
	public Set<String> getResponseTypes() {
		return rs.getResponseTypes();
	}
	/**
	 * @param responseTypes
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setResponseTypes(java.util.Set)
	 */
	public void setResponseTypes(Set<String> responseTypes) {
		rs.setResponseTypes(responseTypes);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getDefaultACRvalues()
	 */
	public Set<String> getDefaultACRvalues() {
		return rs.getDefaultACRvalues();
	}
	/**
	 * @param defaultACRvalues
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setDefaultACRvalues(java.util.Set)
	 */
	public void setDefaultACRvalues(Set<String> defaultACRvalues) {
		rs.setDefaultACRvalues(defaultACRvalues);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getInitiateLoginUri()
	 */
	public String getInitiateLoginUri() {
		return rs.getInitiateLoginUri();
	}
	/**
	 * @param initiateLoginUri
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setInitiateLoginUri(java.lang.String)
	 */
	public void setInitiateLoginUri(String initiateLoginUri) {
		rs.setInitiateLoginUri(initiateLoginUri);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getPostLogoutRedirectUri()
	 */
	public String getPostLogoutRedirectUri() {
		return rs.getPostLogoutRedirectUri();
	}
	/**
	 * @param postLogoutRedirectUri
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setPostLogoutRedirectUri(java.lang.String)
	 */
	public void setPostLogoutRedirectUri(String postLogoutRedirectUri) {
		rs.setPostLogoutRedirectUri(postLogoutRedirectUri);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getRequestUris()
	 */
	public Set<String> getRequestUris() {
		return rs.getRequestUris();
	}
	/**
	 * @param requestUris
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setRequestUris(java.util.Set)
	 */
	public void setRequestUris(Set<String> requestUris) {
		rs.setRequestUris(requestUris);
	}
	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getRequestObjectSigningAlgEmbed()
	 */
	public JWSAlgorithmEmbed getRequestObjectSigningAlgEmbed() {
		return rs.getRequestObjectSigningAlgEmbed();
	}

	/**
	 * @param requestObjectSigningAlg
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setRequestObjectSigningAlgEmbed(org.mitre.jose.JWSAlgorithmEmbed)
	 */
	public void setRequestObjectSigningAlgEmbed(JWSAlgorithmEmbed requestObjectSigningAlg) {
		rs.setRequestObjectSigningAlgEmbed(requestObjectSigningAlg);
	}

	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getUserInfoSignedResponseAlgEmbed()
	 */
	public JWSAlgorithmEmbed getUserInfoSignedResponseAlgEmbed() {
		return rs.getUserInfoSignedResponseAlgEmbed();
	}

	/**
	 * @param userInfoSignedResponseAlg
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setUserInfoSignedResponseAlgEmbed(org.mitre.jose.JWSAlgorithmEmbed)
	 */
	public void setUserInfoSignedResponseAlgEmbed(JWSAlgorithmEmbed userInfoSignedResponseAlg) {
		rs.setUserInfoSignedResponseAlgEmbed(userInfoSignedResponseAlg);
	}

	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getUserInfoEncryptedResponseAlgEmbed()
	 */
	public JWEAlgorithmEmbed getUserInfoEncryptedResponseAlgEmbed() {
		return rs.getUserInfoEncryptedResponseAlgEmbed();
	}

	/**
	 * @param userInfoEncryptedResponseAlg
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setUserInfoEncryptedResponseAlgEmbed(org.mitre.jose.JWEAlgorithmEmbed)
	 */
	public void setUserInfoEncryptedResponseAlgEmbed(JWEAlgorithmEmbed userInfoEncryptedResponseAlg) {
		rs.setUserInfoEncryptedResponseAlgEmbed(userInfoEncryptedResponseAlg);
	}

	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getUserInfoEncryptedResponseEncEmbed()
	 */
	public JWEEncryptionMethodEmbed getUserInfoEncryptedResponseEncEmbed() {
		return rs.getUserInfoEncryptedResponseEncEmbed();
	}

	/**
	 * @param userInfoEncryptedResponseEnc
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setUserInfoEncryptedResponseEncEmbed(org.mitre.jose.JWEEncryptionMethodEmbed)
	 */
	public void setUserInfoEncryptedResponseEncEmbed(JWEEncryptionMethodEmbed userInfoEncryptedResponseEnc) {
		rs.setUserInfoEncryptedResponseEncEmbed(userInfoEncryptedResponseEnc);
	}

	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getRequestObjectSigningAlg()
	 */
	public JWSAlgorithm getRequestObjectSigningAlg() {
		return rs.getRequestObjectSigningAlg();
	}

	/**
	 * @param requestObjectSigningAlg
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setRequestObjectSigningAlg(com.nimbusds.jose.JWSAlgorithm)
	 */
	public void setRequestObjectSigningAlg(JWSAlgorithm requestObjectSigningAlg) {
		rs.setRequestObjectSigningAlg(requestObjectSigningAlg);
	}

	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getUserInfoSignedResponseAlg()
	 */
	public JWSAlgorithm getUserInfoSignedResponseAlg() {
		return rs.getUserInfoSignedResponseAlg();
	}

	/**
	 * @param userInfoSignedResponseAlg
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setUserInfoSignedResponseAlg(com.nimbusds.jose.JWSAlgorithm)
	 */
	public void setUserInfoSignedResponseAlg(JWSAlgorithm userInfoSignedResponseAlg) {
		rs.setUserInfoSignedResponseAlg(userInfoSignedResponseAlg);
	}

	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getUserInfoEncryptedResponseAlg()
	 */
	public JWEAlgorithm getUserInfoEncryptedResponseAlg() {
		return rs.getUserInfoEncryptedResponseAlg();
	}

	/**
	 * @param userInfoEncryptedResponseAlg
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setUserInfoEncryptedResponseAlg(com.nimbusds.jose.JWEAlgorithm)
	 */
	public void setUserInfoEncryptedResponseAlg(JWEAlgorithm userInfoEncryptedResponseAlg) {
		rs.setUserInfoEncryptedResponseAlg(userInfoEncryptedResponseAlg);
	}

	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getUserInfoEncryptedResponseEnc()
	 */
	public EncryptionMethod getUserInfoEncryptedResponseEnc() {
		return rs.getUserInfoEncryptedResponseEnc();
	}

	/**
	 * @param userInfoEncryptedResponseEnc
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setUserInfoEncryptedResponseEnc(com.nimbusds.jose.EncryptionMethod)
	 */
	public void setUserInfoEncryptedResponseEnc(EncryptionMethod userInfoEncryptedResponseEnc) {
		rs.setUserInfoEncryptedResponseEnc(userInfoEncryptedResponseEnc);
	}

	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getTokenEndpointAuthSigningAlgEmbed()
	 */
	public JWSAlgorithmEmbed getTokenEndpointAuthSigningAlgEmbed() {
		return rs.getTokenEndpointAuthSigningAlgEmbed();
	}

	/**
	 * @param tokenEndpointAuthSigningAlgEmbed
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setTokenEndpointAuthSigningAlgEmbed(org.mitre.jose.JWSAlgorithmEmbed)
	 */
	public void setTokenEndpointAuthSigningAlgEmbed(JWSAlgorithmEmbed tokenEndpointAuthSigningAlgEmbed) {
		rs.setTokenEndpointAuthSigningAlgEmbed(tokenEndpointAuthSigningAlgEmbed);
	}

	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getTokenEndpointAuthSigningAlg()
	 */
	public JWSAlgorithm getTokenEndpointAuthSigningAlg() {
		return rs.getTokenEndpointAuthSigningAlg();
	}

	/**
	 * @param tokenEndpointAuthSigningAlg
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setTokenEndpointAuthSigningAlg(com.nimbusds.jose.JWSAlgorithm)
	 */
	public void setTokenEndpointAuthSigningAlg(JWSAlgorithm tokenEndpointAuthSigningAlg) {
		rs.setTokenEndpointAuthSigningAlg(tokenEndpointAuthSigningAlg);
	}

	/**
	 * @return
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#getCreatedAt()
	 */
	public Date getCreatedAt() {
		return rs.getCreatedAt();
	}
	/**
	 * @param createdAt
	 * @see org.mitre.oauth2.model.ResourceServerDetailsEntityUma#setCreatedAt(java.util.Date)
	 */
	public void setCreatedAt(Date createdAt) {
		rs.setCreatedAt(createdAt);
	}
	/**
	 * @return the registrationAccessToken
	 */
	public String getRegistrationAccessToken() {
		return registrationAccessToken;
	}
	/**
	 * @param registrationAccessToken the registrationAccessToken to set
	 */
	public void setRegistrationAccessToken(String registrationAccessToken) {
		this.registrationAccessToken = registrationAccessToken;
	}
	/**
	 * @return the registrationResourceServerUri
	 */
	public String getRegistrationResourceServerUri() {
		return registrationResourceServerUri;
	}
	/**
	 * @param registrationResourceServerUri the registrationResourceServerUri to set
	 */
	public void setRegistrationResourceServerUri(String registrationResourceServerUri) {
		this.registrationResourceServerUri = registrationResourceServerUri;
	}
	/**
	 * @return the rsSecretExpiresAt
	 */
	public Date getResourceServerSecretExpiresAt() {
		return rsSecretExpiresAt;
	}
	/**
	 * @param rsSecretExpiresAt the rsSecretExpiresAt to set
	 */
	public void setResourceServerSecretExpiresAt(Date expiresAt) {
		this.rsSecretExpiresAt = expiresAt;
	}
	/**
	 * @return the rsIdIssuedAt
	 */
	public Date getResourceServerIdIssuedAt() {
		return rsIdIssuedAt;
	}
	/**
	 * @param rsIdIssuedAt the rsIdIssuedAt to set
	 */
	public void setResourceServerIdIssuedAt(Date issuedAt) {
		this.rsIdIssuedAt = issuedAt;
	}



}
