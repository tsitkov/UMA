/*******************************************************************************
 * Copyright 2015 The MIT Kerberos and Internet Trust Consortium
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

import org.mitre.uma.model.RptEntityUma;
import org.mitre.uma.repository.RptRepositoryUma;
import org.mitre.util.jpa.JpaUtil;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.mitre.uma.model.PermissionEntityUma;

/**
 * JPA Rpt Repository implementation.
 * 
 * @author  tsitkov
 *
 */
@Repository
@Transactional
public class JpaRptRepositoryUma implements RptRepositoryUma {

	@PersistenceContext
	EntityManager manager;

	/* (non-Javadoc)
	 * @see org.mitre.uma.repository.RptRepositoryUma#save(org.mitre.uma.model.RptEntityUma)
	 */
	@Override
	public RptEntityUma save(RptEntityUma perm) {

		return JpaUtil.saveOrUpdate(perm.getId() /* null */, manager, perm);

	}

	@Override
	public RptEntityUma getByRptValue(String rpt) {

                TypedQuery<RptEntityUma> query = manager.createNamedQuery("RptEntityUma.getByRptValue", RptEntityUma.class);
                query.setParameter("rpt", rpt);
                return JpaUtil.getSingleResult(query.getResultList());

        }

	@Override
	public RptEntityUma getByPermissionTicketValue(String ticket) {

                TypedQuery<RptEntityUma> query = manager.createNamedQuery("RptEntityUma.getByPermissionTicketValue", RptEntityUma.class);
                query.setParameter("perm_ticket", ticket);
                return JpaUtil.getSingleResult(query.getResultList());

        }
}
