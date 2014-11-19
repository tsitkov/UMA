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
 * Entity class for RPT
 * 
 * @author tsitkov
 *
 */
@Entity
@Table(name = "rpt")
@NamedQueries({
	@NamedQuery(name = "RptEntityUma.getByRptValue", query = "select a from RptEntityUma a where a.rpt = :rpt"),
	@NamedQuery(name = "RptEntityUma.getByPermissionTicketValue", query = "select a from RptEntityUma a where a.permissionTicket = :ticket")
})
public class RptEntityUma {

	private Long id;
	private String ticket; /* permission ticket */
	private String aat;
	private String rpt;


	/**
	 * Default constructor.
	 */
	public RptEntityUma() {

	}

	/**
	 * Create a new RptEntityUma with the given aat and permission ticket
	 * 
	 * @param aat		AAT token
	 * @param ticket       Premission permTkt
	 */
	public RptEntityUma(String aat, String permissionTicket) { 
		this.aat = aat;
		this.ticket = permissionTicket;
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
	 * @return the UMA AAT
	 */
	@Basic
	@Column(name = "aat")
	public String getAat() {
		return aat;
	}

	/**
	 * @param aat  the AAT to set
	 */
	public void setAat(String aat) {
		this.aat = aat;
	}


	/**
	 * @return the Permission ticket
	 */
	@Basic
	@Column(name = "perm_ticket")
	public String getPermissionTicket() {
		return ticket;
	}

	/**
	 * @param permTkt  the permission ticket
	 */
	public void setPermissionTicket(String permTicket) {
		this.ticket = permTicket;
	}

	/**
	 * @return the RPT
	 */
	@Basic
	@Column(name = "rpt")
	public String getRpt() {
		return rpt;
	}

	/**
	 * @param rpt_token  the RPT
	 */
	public void setRpt(String rpt_token) {
		this.rpt = rpt_token;
	}

}
