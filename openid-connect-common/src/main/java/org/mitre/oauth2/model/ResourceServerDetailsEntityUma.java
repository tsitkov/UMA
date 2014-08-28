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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.mitre.jose.JWEAlgorithmEmbed;
import org.mitre.jose.JWEEncryptionMethodEmbed;
import org.mitre.jose.JWSAlgorithmEmbed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;

/**
 * 
 */
@Entity
@Table(name = "rs_details")
@NamedQueries({
	@NamedQuery(name = "ResourceServerDetailsEntityUma.findAll", query = "SELECT c FROM ResourceServerDetailsEntityUma c"),
//	@NamedQuery(name = "ResourceServerDetailsEntityUma.getByResourceServerId", query = "select c from ResourceServerDetailsEntityUma c where c.rsId = :rsId")
})
public class ResourceServerDetailsEntityUma implements ClientDetails {

	/**
	 * 
	 */
	private static final int DEFAULT_ID_TOKEN_VALIDITY_SECONDS = 600;

	private static final long serialVersionUID = -1617727085733786296L;  // ????????

	private Long id;

	/** Fields from the OAuth2 Dynamic Registration Specification */
	private String rsId = null; // rs_id
	private String resourceServerSecret = null; // rs_secret
	private Set<String> redirectUris = new HashSet<String>(); // redirect_uris
	private String resourceServerName; // rs_name
	private String resourceServerUri; // rs_uri
	private String logoUri; // logo_uri
	private Set<String> contacts; // contacts
	private String tosUri; // tos_uri
	private AuthMethod tokenEndpointAuthMethod = AuthMethod.SECRET_BASIC; // token_endpoint_auth_method
	private Set<String> scope = new HashSet<String>(); // scope
	private Set<String> grantTypes = new HashSet<String>(); // grant_types
	private Set<String> responseTypes = new HashSet<String>(); // response_types
	private String policyUri;
	private String jwksUri;

	/** Fields from OIDC ResorceServer Registration Specification **/
	private AppType applicationType; // application_type
	private String sectorIdentifierUri; // sector_identifier_uri
	private SubjectType subjectType; // subject_type

	private JWSAlgorithmEmbed requestObjectSigningAlg = null; // request_object_signing_alg

	private JWSAlgorithmEmbed userInfoSignedResponseAlg = null; // user_info_signed_response_alg
	private JWEAlgorithmEmbed userInfoEncryptedResponseAlg = null; // user_info_encrypted_response_alg
	private JWEEncryptionMethodEmbed userInfoEncryptedResponseEnc = null; // user_info_encrypted_response_enc
/*
	private JWSAlgorithmEmbed idTokenSignedResponseAlg = null; // id_token_signed_response_alg
	private JWEAlgorithmEmbed idTokenEncryptedResponseAlg = null; // id_token_encrypted_response_alg
	private JWEEncryptionMethodEmbed idTokenEncryptedResponseEnc = null; // id_token_encrypted_response_enc
*/
	private JWSAlgorithmEmbed tokenEndpointAuthSigningAlg = null; // token_endpoint_auth_signing_alg

	private Integer defaultMaxAge; // default_max_age
	private Boolean requireAuthTime; // require_auth_time
	private Set<String> defaultACRvalues; // default_acr_values

	private String initiateLoginUri; // initiate_login_uri
	private String postLogoutRedirectUri; // post_logout_redirect_uri

	private Set<String> requestUris; // request_uris

	/** Fields to support the ResorceServerDetails interface **/
	private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	private Integer accessTokenValiditySeconds = 0; // in seconds
	private Integer refreshTokenValiditySeconds = 0; // in seconds
	private Set<String> resourceIds = new HashSet<String>();
	private Map<String, Object> additionalInformation = new HashMap<String, Object>();

	/** Our own fields **/
	private String rsDescription = ""; // human-readable description
	private boolean reuseRefreshToken = true; // do we let someone reuse a refresh token?
	private boolean dynamicallyRegistered = false; // was this resourceServer dynamically registered?
	private boolean allowIntrospection = false; // do we let this resourceServer call the introspection endpoint?
//	private Integer idTokenValiditySeconds; //timeout for id tokens
	private Date createdAt; // time the resourceServer was created

	public enum AuthMethod {
		SECRET_POST("rs_secret_post"),
		SECRET_BASIC("rs_secret_basic"),
		SECRET_JWT("rs_secret_jwt"),
		PRIVATE_KEY("private_key_jwt"),
		NONE("none");

		private final String value;

		// map to aid reverse lookup
		private static final Map<String, AuthMethod> lookup = new HashMap<String, AuthMethod>();
		static {
			for (AuthMethod a : AuthMethod.values()) {
				lookup.put(a.getValue(), a);
			}
		}

		AuthMethod(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static AuthMethod getByValue(String value) {
			return lookup.get(value);
		}
	}

	public enum AppType {
		WEB("web"), NATIVE("native");

		private final String value;

		// map to aid reverse lookup
		private static final Map<String, AppType> lookup = new HashMap<String, AppType>();
		static {
			for (AppType a : AppType.values()) {
				lookup.put(a.getValue(), a);
			}
		}

		AppType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static AppType getByValue(String value) {
			return lookup.get(value);
		}
	}

	public enum SubjectType {
		PAIRWISE("pairwise"), PUBLIC("public");

		private final String value;

		// map to aid reverse lookup
		private static final Map<String, SubjectType> lookup = new HashMap<String, SubjectType>();
		static {
			for (SubjectType u : SubjectType.values()) {
				lookup.put(u.getValue(), u);
			}
		}

		SubjectType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static SubjectType getByValue(String value) {
			return lookup.get(value);
		}
	}

	/**
	 * Create a blank ResourceServerDetailsEntityUma
	 */
	public ResourceServerDetailsEntityUma() {

	}


	@PrePersist
	@PreUpdate
	private void prePersist() {
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the rsDescription
	 */
	@Basic
	@Column(name="rs_description")
	public String getResourceServerDescription() {
		return getClientDescription();
	}
	public String getClientDescription() {
		return rsDescription;
	}

	/**
	 * @param rsDescription Human-readable long description of the resourceServer (optional)
	 */
	public void setClientDescription(String rsDescription) {
		this.rsDescription = rsDescription;
	}
	public void setResourceServerDescription(String rsDescription) {
		setClientDescription(rsDescription);
	}

	/**
	 * @return the allowRefresh
	 */
	@Transient
	public boolean isAllowRefresh() {
		if (grantTypes != null) {
			return getAuthorizedGrantTypes().contains("refresh_token");
		} else {
			return false; // if there are no grants, we can't be refreshing them, can we?
		}
	}

	@Basic
	@Column(name="reuse_refresh_tokens")
	public boolean isReuseRefreshToken() {
		return reuseRefreshToken;
	}

	public void setReuseRefreshToken(boolean reuseRefreshToken) {
		this.reuseRefreshToken = reuseRefreshToken;
	}

	/**
	 * Number of seconds ID token is valid for. MUST be a positive integer, can not be null.
	 * 
	 * @return the idTokenValiditySeconds
	 */

	@Basic
	@Column(name="id_token_validity_seconds")
	public Integer getIdTokenValiditySeconds() {
		return 0;
	}

	/**
	 * @param idTokenValiditySeconds the idTokenValiditySeconds to set
	 */

	public void setIdTokenValiditySeconds(Integer idTokenValiditySeconds) {
	}

	/**
	 * @return the dynamicallyRegistered
	 */
	@Basic
	@Column(name="dynamically_registered")
	public boolean isDynamicallyRegistered() {
		return dynamicallyRegistered;
	}

	/**
	 * @param dynamicallyRegistered the dynamicallyRegistered to set
	 */
	public void setDynamicallyRegistered(boolean dynamicallyRegistered) {
		this.dynamicallyRegistered = dynamicallyRegistered;
	}





	/**
	 * @return the allowIntrospection
	 */
	@Basic
	@Column(name="allow_introspection")
	public boolean isAllowIntrospection() {
		return allowIntrospection;
	}

	/**
	 * @param allowIntrospection the allowIntrospection to set
	 */
	public void setAllowIntrospection(boolean allowIntrospection) {
		this.allowIntrospection = allowIntrospection;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isSecretRequired() {
		if (getTokenEndpointAuthMethod() != null &&
				(getTokenEndpointAuthMethod().equals(AuthMethod.SECRET_BASIC) ||
				 getTokenEndpointAuthMethod().equals(AuthMethod.SECRET_POST) ||
				 getTokenEndpointAuthMethod().equals(AuthMethod.SECRET_JWT))) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * If the scope list is not null or empty, then this resourceServer has been scoped.
	 */
	@Override
	@Transient
	public boolean isScoped() {
		return getScope() != null && !getScope().isEmpty();
	}

	/**
	 * @return the rsId
	 */
	@Basic
	@Override
	@Column(name="rs_id")
	public String getClientId() {
		return rsId;
	}

	public String getResourceServerId() {
		return getClientId();
	}

	/**
	 * @param rsId The OAuth2 rs_id, must be unique to this resourceServer
	 */
	public void setClientId(String rsId) {
		this.rsId = rsId;
	}
	public void setResourceServerId(String rsId) {
                setClientId(rsId);
	}

	/**
	 * @return the resourceServerSecret
	 */
	@Basic
	@Override
	@Column(name="rs_secret")
	public String getClientSecret() {
		return resourceServerSecret;
	}
	public String getResourceServerSecret() {
		return getClientSecret();
	}

	/**
	 * @param resourceServerSecret the OAuth2 rs_secret (optional)
	 */
	public void setClientSecret(String resourceServerSecret) {
		this.resourceServerSecret = resourceServerSecret;
	}
	public void setResourceServerSecret(String resourceServerSecret) {
		setClientSecret(resourceServerSecret);	
	}

	/**
	 * @return the scope
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="rs_scope",
			joinColumns=@JoinColumn(name="owner_id")
			)
	@Override
	@Column(name="scope")
	public Set<String> getScope() {
		return scope;
	}

	/**
	 * @param scope the set of scopes allowed to be issued to this resourceServer
	 */
	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	/**
	 * @return the authorizedGrantTypes
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="rs_grant_type",
			joinColumns=@JoinColumn(name="owner_id")
			)
	@Column(name="grant_type")
	public Set<String> getGrantTypes() {
		return grantTypes;
	}

	/**
	 * @param authorizedGrantTypes the OAuth2 grant types that this resourceServer is allowed to use
	 */
	public void setGrantTypes(Set<String> grantTypes) {
		this.grantTypes = grantTypes;
	}

	/**
	 * passthrough for SECOAUTH api
	 */
	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return getGrantTypes();
	}

	/**
	 * @return the authorities
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="rs_authority",
			joinColumns=@JoinColumn(name="owner_id")
			)
	@Override
	@Column(name="authority")
	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the Spring Security authorities this resourceServer is given
	 */
	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	@Basic
	@Column(name="access_token_validity_seconds")
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	/**
	 * @param accessTokenTimeout the accessTokenTimeout to set
	 */
	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	@Override
	@Basic
	@Column(name="refresh_token_validity_seconds")
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	/**
	 * @param refreshTokenTimeout Lifetime of refresh tokens, in seconds (optional - leave null for no timeout)
	 */
	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	/**
	 * @return the registeredRedirectUri
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="rs_redirect_uri",
			joinColumns=@JoinColumn(name="owner_id")
			)
	@Column(name="redirect_uri")
	public Set<String> getRedirectUris() {
		return redirectUris;
	}

	/**
	 * @param registeredRedirectUri the registeredRedirectUri to set
	 */
	public void setRedirectUris(Set<String> redirectUris) {
		this.redirectUris = redirectUris;
	}

	/**
	 * Pass-through method to fulfill the ResorceServerDetails interface with a bad name
	 */
	@Override
	@Transient
	public Set<String> getRegisteredRedirectUri() {
		return getRedirectUris();
	}

	/**
	 * @return the resourceIds
	 */
	@Override
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="rs_resource",
			joinColumns=@JoinColumn(name="owner_id")
			)
	@Column(name="resource_id")
	public Set<String> getResourceIds() {
		return resourceIds;
	}

	/**
	 * @param resourceIds the resourceIds to set
	 */
	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = resourceIds;
	}


	/**
	 * This library does not make use of this field, so it is not
	 * stored using our persistence layer.
	 * 
	 * However, it's somehow required by SECOUATH.
	 * 
	 * @return an empty map
	 */
	@Override
	@Transient
	public Map<String, Object> getAdditionalInformation() {
		return this.additionalInformation;
	}




	@Enumerated(EnumType.STRING)
	@Column(name="application_type")
	public AppType getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(AppType applicationType) {
		this.applicationType = applicationType;
	}

	@Basic
	@Column(name="rs_name")
	public String getClientName() {
		return resourceServerName;
	}
	public String getResourceServerName() {
		return getClientName();
	}

	public void setClientName(String resourceServerName) {
		this.resourceServerName = resourceServerName;
	}
	public void setResourceServerName(String resourceServerName) {
	        setResourceServerName(resourceServerName);	
	}

	@Enumerated(EnumType.STRING)
	@Column(name="token_endpoint_auth_method")
	public AuthMethod getTokenEndpointAuthMethod() {
		return tokenEndpointAuthMethod;
	}

	public void setTokenEndpointAuthMethod(AuthMethod tokenEndpointAuthMethod) {
		this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="subject_type")
	public SubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(SubjectType subjectType) {
		this.subjectType = subjectType;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="rs_contact",
			joinColumns=@JoinColumn(name="owner_id")
			)
	@Column(name="contact")
	public Set<String> getContacts() {
		return contacts;
	}

	public void setContacts(Set<String> contacts) {
		this.contacts = contacts;
	}

	@Basic
	@Column(name="logo_uri")
	public String getLogoUri() {
		return logoUri;
	}

	public void setLogoUri(String logoUri) {
		this.logoUri = logoUri;
	}

	@Basic
	@Column(name="policy_uri")
	public String getPolicyUri() {
		return policyUri;
	}

	public void setPolicyUri(String policyUri) {
		this.policyUri = policyUri;
	}

	/**
	 * @return the resourceServerUrl
	 */
	@Basic
	@Column(name="rs_uri")
	public String getClientUri() {
		return resourceServerUri;
	}
	public String getResourceServerUri() {
		return getClientUri();
	}

	/**
	 * @param resourceServerUrl the resourceServerUrl to set
	 */
	public void setClientUri(String resourceServerUri) {
		this.resourceServerUri = resourceServerUri;
	}
	public void setResourceServerUri(String resourceServerUri) {
	        setClientUri(resourceServerUri);	
	}

	/**
	 * @return the tosUrl
	 */
	@Basic
	@Column(name="tos_uri")
	public String getTosUri() {
		return tosUri;
	}

	/**
	 * @param tosUrl the tosUrl to set
	 */
	public void setTosUri(String tosUri) {
		this.tosUri = tosUri;
	}

	@Basic
	@Column(name="jwks_uri")
	public String getJwksUri() {
		return jwksUri;
	}

	public void setJwksUri(String jwksUri) {
		this.jwksUri = jwksUri;
	}

	@Basic
	@Column(name="sector_identifier_uri")
	public String getSectorIdentifierUri() {
		return sectorIdentifierUri;
	}

	public void setSectorIdentifierUri(String sectorIdentifierUri) {
		this.sectorIdentifierUri = sectorIdentifierUri;
	}

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "algorithmName", column=@Column(name="request_object_signing_alg"))
	})
	public JWSAlgorithmEmbed getRequestObjectSigningAlgEmbed() {
		return requestObjectSigningAlg;
	}

	public void setRequestObjectSigningAlgEmbed(JWSAlgorithmEmbed requestObjectSigningAlg) {
		this.requestObjectSigningAlg = requestObjectSigningAlg;
	}

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "algorithmName", column=@Column(name="user_info_signed_response_alg"))
	})
	public JWSAlgorithmEmbed getUserInfoSignedResponseAlgEmbed() {
		return userInfoSignedResponseAlg;
	}

	public void setUserInfoSignedResponseAlgEmbed(JWSAlgorithmEmbed userInfoSignedResponseAlg) {
		this.userInfoSignedResponseAlg = userInfoSignedResponseAlg;
	}

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "algorithmName", column=@Column(name="user_info_encrypted_response_alg"))
	})
	public JWEAlgorithmEmbed getUserInfoEncryptedResponseAlgEmbed() {
		return userInfoEncryptedResponseAlg;
	}

	public void setUserInfoEncryptedResponseAlgEmbed(JWEAlgorithmEmbed userInfoEncryptedResponseAlg) {
		this.userInfoEncryptedResponseAlg = userInfoEncryptedResponseAlg;
	}

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "algorithmName", column=@Column(name="user_info_encrypted_response_enc"))
	})
	public JWEEncryptionMethodEmbed getUserInfoEncryptedResponseEncEmbed() {
		return userInfoEncryptedResponseEnc;
	}

	public void setUserInfoEncryptedResponseEncEmbed(JWEEncryptionMethodEmbed userInfoEncryptedResponseEnc) {
		this.userInfoEncryptedResponseEnc = userInfoEncryptedResponseEnc;
	}

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "algorithmName", column=@Column(name="id_token_signed_response_alg"))
	})
	public JWSAlgorithmEmbed getIdTokenSignedResponseAlgEmbed() {
		return null;
	}

	public void setIdTokenSignedResponseAlgEmbed(JWSAlgorithmEmbed idTokenSignedResponseAlg) {
	}

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "algorithmName", column=@Column(name="id_token_encrypted_response_alg"))
	})
	public JWEAlgorithmEmbed getIdTokenEncryptedResponseAlgEmbed() {
		return null;
	}

	public void setIdTokenEncryptedResponseAlgEmbed(JWEAlgorithmEmbed idTokenEncryptedResponseAlg) {
	}
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "algorithmName", column=@Column(name="id_token_encrypted_response_enc"))
	})
	public JWEEncryptionMethodEmbed getIdTokenEncryptedResponseEncEmbed() {
		return null;
	}

	public void setIdTokenEncryptedResponseEncEmbed(JWEEncryptionMethodEmbed idTokenEncryptedResponseEnc) {
	}

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "algorithmName", column=@Column(name="token_endpoint_auth_signing_alg"))
	})
	public JWSAlgorithmEmbed getTokenEndpointAuthSigningAlgEmbed() {
		return tokenEndpointAuthSigningAlg;
	}

	public void setTokenEndpointAuthSigningAlgEmbed(JWSAlgorithmEmbed tokenEndpointAuthSigningAlgEmbed) {
		this.tokenEndpointAuthSigningAlg = tokenEndpointAuthSigningAlgEmbed;
	}

	//
	// Transient passthrough methods for JOSE elements
	//

	@Transient
	public JWSAlgorithm getRequestObjectSigningAlg() {
		if (requestObjectSigningAlg != null) {
			return requestObjectSigningAlg.getAlgorithm();
		} else {
			return null;
		}
	}

	public void setRequestObjectSigningAlg(JWSAlgorithm requestObjectSigningAlg) {
		this.requestObjectSigningAlg = new JWSAlgorithmEmbed(requestObjectSigningAlg);
	}

	@Transient
	public JWSAlgorithm getUserInfoSignedResponseAlg() {
		if (userInfoSignedResponseAlg != null) {
			return userInfoSignedResponseAlg.getAlgorithm();
		} else {
			return null;
		}
	}

	public void setUserInfoSignedResponseAlg(JWSAlgorithm userInfoSignedResponseAlg) {
		this.userInfoSignedResponseAlg = new JWSAlgorithmEmbed(userInfoSignedResponseAlg);
	}

	@Transient
	public JWEAlgorithm getUserInfoEncryptedResponseAlg() {
		if (userInfoEncryptedResponseAlg != null) {
			return userInfoEncryptedResponseAlg.getAlgorithm();
		} else {
			return null;
		}
	}

	public void setUserInfoEncryptedResponseAlg(JWEAlgorithm userInfoEncryptedResponseAlg) {
		this.userInfoEncryptedResponseAlg = new JWEAlgorithmEmbed(userInfoEncryptedResponseAlg);
	}

	@Transient
	public EncryptionMethod getUserInfoEncryptedResponseEnc() {
		if (userInfoEncryptedResponseEnc != null) {
			return userInfoEncryptedResponseEnc.getAlgorithm();
		} else {
			return null;
		}
	}

	public void setUserInfoEncryptedResponseEnc(EncryptionMethod userInfoEncryptedResponseEnc) {
		this.userInfoEncryptedResponseEnc = new JWEEncryptionMethodEmbed(userInfoEncryptedResponseEnc);
	}

	@Transient
	public JWSAlgorithm getIdTokenSignedResponseAlg() {
		return null;
	}

	public void setIdTokenSignedResponseAlg(JWSAlgorithm idTokenSignedResponseAlg) {
		//this.idTokenSignedResponseAlg = new JWSAlgorithmEmbed(idTokenSignedResponseAlg);
	}
	@Transient
	public JWEAlgorithm getIdTokenEncryptedResponseAlg() {
		return null;
	}

	public void setIdTokenEncryptedResponseAlg(JWEAlgorithm idTokenEncryptedResponseAlg) {
	}

	@Transient
	public EncryptionMethod getIdTokenEncryptedResponseEnc() {
		return null;
	}

	public void setIdTokenEncryptedResponseEnc(EncryptionMethod idTokenEncryptedResponseEnc) {
		return;
	}

	@Transient
	public JWSAlgorithm getTokenEndpointAuthSigningAlg() {

		if (tokenEndpointAuthSigningAlg != null) {
			return tokenEndpointAuthSigningAlg.getAlgorithm();
		} else {
			return null;
		}

	}

	public void setTokenEndpointAuthSigningAlg(JWSAlgorithm tokenEndpointAuthSigningAlg) {
		this.tokenEndpointAuthSigningAlg = new JWSAlgorithmEmbed(tokenEndpointAuthSigningAlg);
	}

	// END Transient JOSE methods

	@Basic
	@Column(name="default_max_age")
	public Integer getDefaultMaxAge() {
		return defaultMaxAge;
	}

	public void setDefaultMaxAge(Integer defaultMaxAge) {
		this.defaultMaxAge = defaultMaxAge;
	}

	@Basic
	@Column(name="require_auth_time")
	public Boolean getRequireAuthTime() {
		return requireAuthTime;
	}

	public void setRequireAuthTime(Boolean requireAuthTime) {
		this.requireAuthTime = requireAuthTime;
	}

	/**
	 * @return the responseTypes
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="rs_response_type",
			joinColumns=@JoinColumn(name="owner_id")
			)
	@Column(name="response_type")
	public Set<String> getResponseTypes() {
		return responseTypes;
	}

	/**
	 * @param responseTypes the responseTypes to set
	 */
	public void setResponseTypes(Set<String> responseTypes) {
		this.responseTypes = responseTypes;
	}

	/**
	 * @return the defaultACRvalues
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="rs_default_acr_value",
			joinColumns=@JoinColumn(name="owner_id")
			)
	@Column(name="default_acr_value")
	public Set<String> getDefaultACRvalues() {
		return defaultACRvalues;
	}

	/**
	 * @param defaultACRvalues the defaultACRvalues to set
	 */
	public void setDefaultACRvalues(Set<String> defaultACRvalues) {
		this.defaultACRvalues = defaultACRvalues;
	}

	/**
	 * @return the initiateLoginUri
	 */
	@Basic
	@Column(name="initiate_login_uri")
	public String getInitiateLoginUri() {
		return initiateLoginUri;
	}

	/**
	 * @param initiateLoginUri the initiateLoginUri to set
	 */
	public void setInitiateLoginUri(String initiateLoginUri) {
		this.initiateLoginUri = initiateLoginUri;
	}

	/**
	 * @return the postLogoutRedirectUri
	 */
	@Basic
	@Column(name="post_logout_redirect_uri")
	public String getPostLogoutRedirectUri() {
		return postLogoutRedirectUri;
	}

	/**
	 * @param postLogoutRedirectUri the postLogoutRedirectUri to set
	 */
	public void setPostLogoutRedirectUri(String postLogoutRedirectUri) {
		this.postLogoutRedirectUri = postLogoutRedirectUri;
	}

	/**
	 * @return the requestUris
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="rs_request_uri",
			joinColumns=@JoinColumn(name="owner_id")
			)
	@Column(name="request_uri")
	public Set<String> getRequestUris() {
		return requestUris;
	}

	/**
	 * @param requestUris the requestUris to set
	 */
	public void setRequestUris(Set<String> requestUris) {
		this.requestUris = requestUris;
	}

	/**
	 * @return the createdAt
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Our framework doesn't use this construct, we use WhitelistedSites and ApprovedSites instead.
	 */
	@Override
	public boolean isAutoApprove(String scope) {
		return false;
	}

}
