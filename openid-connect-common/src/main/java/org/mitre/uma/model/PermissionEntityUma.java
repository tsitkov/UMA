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
package org.mitre.uma.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.CollectionTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity class for permission tickets
 * 
 * @author tsitkov
 *
 */
@Entity
@Table(name = "permissions")
@NamedQueries({
	@NamedQuery(name = "PermissionEntityUma.getByPermissionTicketValue", query = "select a from PermissionEntityUma a where a.ticket = :perm_ticket")
})
public class PermissionEntityUma {

	private Long id;
	private String setId;
	private String pat;
	private Set<String> scope;
//	private Date expire;
	private String perm_ticket;

	//private OAuth2Authentication authentication;

	/**
	 * Default constructor.
	 */
	public PermissionEntityUma() {

	}

	/**
	 * Create a new PermissionEntityUma with the given pat, setId, etc.
	 * 
	 * @param pat		PAT token
	 * @param scope 	Resource set scope
	 * @param setId		Resource set ID
	 * @param perm_ticket	Premission perm_ticket
	 */
	public PermissionEntityUma( String pat, String setId, Set<String> scope, String perm_ticket) { 

		this.pat = pat;
		this.setId = setId;
		this.scope = scope;
		this.perm_ticket = perm_ticket;
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
	 * @return the UMA PAT
	 */
	@Basic
	@Column(name = "pat")
	public String getPat() {
		return pat;
	}

	/**
	 * @param pat  the PAT to set
	 */
	public void setPat(String pat) {
		this.pat = pat;
	}

	/**
	 * @return the setId
	 */
	@Basic
	@Column(name = "set_id")
	public String getSetId() {
		return setId;
	}

	/**
	 * @param setId  the ID of the resource set
	 */
	public void setSetId(String setId) {
		this.setId = setId;
	}

	/**
	 * @return the Resource Set scope
	 */
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(
                        name="permission_scope",
                        joinColumns=@JoinColumn(name="set_id")
                        )
	@Basic
	@Column(name = "scope")
	public Set<String> getScope() {
		return scope;
	}

	/**
	 * @param scope  the Resource Set scope to set
	 */
	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	/**
	 * @return the getExpiration
	 */
	/*@Basic
	@Column(name = "expire")
	public String getExpiration() {
		return expire;
	}
	*/
	/**
	 * @param setExpiration 
	 */
	/*
	public void setExpiration(Date time) {
		this.expire = expire;
	}
	*/

	/**
	 * @return the Permission perm_ticket
	 */
	@Basic
	@Column(name = "perm_ticket")
	public String getTicket() {
		return perm_ticket;
	}

	/**
	 * @param perm_ticket  the Permission perm_ticket
	 */
	public void setTicket(String perm_ticket) {
		this.perm_ticket = perm_ticket;
	}

	public boolean isExpired() {
		return false;
	}
}
