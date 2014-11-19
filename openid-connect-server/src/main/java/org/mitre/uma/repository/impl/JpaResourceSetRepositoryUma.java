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

import org.mitre.uma.model.ResourceSetEntityUma;
import org.mitre.uma.repository.ResourceSetRepositoryUma;
import org.mitre.util.jpa.JpaUtil;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

/**
 * JPA ResourceSet Repository implementation.
 * 
 * @author  tsitkov
 *
 */
@Repository
@Transactional
public class JpaResourceSetRepositoryUma implements ResourceSetRepositoryUma {

	@PersistenceContext
	EntityManager manager;

	/* (non-Javadoc)
	 * @see org.mitre.uma.repository.ResourceSetRepositoryUma#save(org.mitre.uma.model.ResourceSetEntityUma)
	 */
	@Override
//	@Transactional
	public ResourceSetEntityUma save(ResourceSetEntityUma rss) {

		return JpaUtil.saveOrUpdate(rss.getId() /* null */, manager, rss);

	}

	@Override
	public ResourceSetEntityUma getBySetId(String setId) {

                TypedQuery<ResourceSetEntityUma> query = manager.createNamedQuery("ResourceSetEntityUma.getBySetId", ResourceSetEntityUma.class);
                query.setParameter("set_id", setId);
                return JpaUtil.getSingleResult(query.getResultList());
	}

	@Override
	public ResourceSetEntityUma getByRsId(String rsId) {

                TypedQuery<ResourceSetEntityUma> query = manager.createNamedQuery("ResourceSetEntityUma.getByRsId", ResourceSetEntityUma.class);
                query.setParameter("rsid", rsId);
                return JpaUtil.getSingleResult(query.getResultList());
	}

	@Override
	public ResourceSetEntityUma getBySetIdAndScope(String setId, Set<String> scope) {

                TypedQuery<ResourceSetEntityUma> query = manager.createNamedQuery("ResourceSetEntityUma.getBySetIdAndScope", ResourceSetEntityUma.class);
                query.setParameter("set_id", setId);
                query.setParameter("scope", scope);
                return JpaUtil.getSingleResult(query.getResultList());
	}

	@Override
	public ResourceSetEntityUma getBySetIdAndRev(String setId, String rev) {

                TypedQuery<ResourceSetEntityUma> query = manager.createNamedQuery("ResourceSetEntityUma.getBySetIdAndRev", ResourceSetEntityUma.class);
                query.setParameter("set_id", setId);
                query.setParameter("revision", rev);
                return JpaUtil.getSingleResult(query.getResultList());
	}
}
