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
package org.mitre.openid.connect.model;

import java.util.Set;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author tsitkov
 *
 */
@Entity
@Table(name="resource_set")
@NamedQueries({
	@NamedQuery(name = "ResourceSet.getAll", query = "select w from ResourceSet w"),
//	@NamedQuery(name = "ResourceSet.getByResourceSetId", query = "select w from ResourceSet w where w.rssId = :rssId"),

//	@NamedQuery(name = "ResourceSet.getByCreatoruserId", query = "select w from ResourceSet w where w.resourceOwnerId = :ownerId")
})
public class ResourceSet {

/*
        pat_value VARCHAR(4096),  
        rs_id BIGINT,
        owner_id BIGINT,
        rss_uri VARCHAR(2048),
        rss_id BIGINT,

        permission VARCHAR(256),
        scope VARCHAR(256),
        expiration TIMESTAMP,     
        policy_uri VARCHAR(2048)
*/
	// unique id
	private Long id;

	// resource owner who created this entry
	private Long ownerId;

	// Resource set URI
	private String rssUri;

	// Resource set ID
	private Long rssId;
/*
	// Permissions
	private String permission; 

	// Policy
	private String scope;

	// Expitation   ??? Is PAT expuration enough?
	private Date expiration;

	// Policies
	private String policyUri 
*/
	private Set<String> allowedPolicy;

	/**
	 * Empty constructor
	 */
	public ResourceSet() {

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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the rssId
	 */
	@Basic
	@Column(name="rssId")
	public Long  getResourceSetId() {
		return rssId;
	}

	/**
	 * @param rssId the rssId to set
	 */
	public void setResourceSetId(Long rssId) {
		this.rssId = rssId;
	}

	/**
	 * @return the allowedPolicy
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="resource_set_policy",
			joinColumns=@JoinColumn(name="rss_id")
			)
	@Column(name="policy")
	public Set<String> getAllowedPolicy() {
		return allowedPolicy;
	}

	/**
	 * @param allowedPolicy the allowedPolicy to set
	 */
	public void setAllowedPolicy(Set<String> allowedPolicy) {
		this.allowedPolicy = allowedPolicy;
	}

	@Basic
	@Column(name="owner_id")
	public Long getResourceOwnerId() {
		return ownerId;
	}

	public void setResourceOwnerId(Long resourceOwnerId) {
		this.ownerId = ownerId;
	}
}
