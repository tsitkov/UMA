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
package org.mitre.uma.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mitre.uma.model.PermissionEntityUma;
import org.mitre.uma.repository.PermissionRepositoryUma;
import org.mitre.util.jpa.JpaUtil;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA Permission Repository implementation.
 * 
 * @author  tsitkov
 *
 */
@Repository
@Transactional
public class JpaPermissionRepositoryUma implements PermissionRepositoryUma {

	@PersistenceContext
	EntityManager manager;

	/* (non-Javadoc)
	 * @see org.mitre.uma.repository.PermissionRepositoryUma#save(org.mitre.uma.model.PermissionEntityUma)
	 */
	@Override
//	@Transactional
	public PermissionEntityUma save(PermissionEntityUma perm) {

		return JpaUtil.saveOrUpdate(perm.getId() /* null */, manager, perm);

	}

	public PermissionEntityUma getByPermissionTicketValue(String ticket) {

		TypedQuery<PermissionEntityUma> query = manager.createNamedQuery("PermissionEntityUma.getByPermissionTicketValue", PermissionEntityUma.class);
                query.setParameter("perm_ticket", ticket);
                return JpaUtil.getSingleResult(query.getResultList());
	}
}
