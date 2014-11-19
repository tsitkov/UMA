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
package org.mitre.uma.repository;

import org.mitre.uma.model.ResourceSetEntityUma;
import java.util.Set;

/**
 * Interface for saving UMA Resource Sets as ResourceSetEntityUma entities.
 * 
 * @author tsitkov 
 *
 */
public interface ResourceSetRepositoryUma {

	/**
	 * Save an ResourceSetEntityUma to the repository
	 * 
	 * @param rss	the ResourceSetEntityUma to save
	 * @return	the saved ResourceSetEntityUma
	 */
	public ResourceSetEntityUma save(ResourceSetEntityUma rss);

       /**
         * Returns the ResourceSetEntityUma for the given id
         * 
         * @param setId
         *            the id of the Resource Set that was created by AS
         * @return a valid ResourceSetEntityUma if it exists, null otherwise
         */
        public ResourceSetEntityUma  getBySetId(String setId);

       /**
         * Returns the ResourceSetEntityUma for the given id
         * 
         * @param rsId
         *            the id of the Resource Set passed by RS
         * @return a valid ResourceSetEntityUma if it exists, null otherwise
         */
        public ResourceSetEntityUma  getByRsId(String rsId);

       /**
         * Returns the ResourceSetEntityUma for the given id and scope
         * 
         * @param setId
         *            the id of the Resource Set
         * @param scope
         *            scope that is allowed (registered) for this Resource Set Id
         * @return a valid ResourceSetEntityUma if it exists, null otherwise
         */
        public ResourceSetEntityUma  getBySetIdAndScope(String setId, Set<String> scope);

       /**
         * Returns the ResourceSetEntityUma for the given id and revision
         * 
         * @param setId
         *            the id of the Resource Set
         * @param revision
         *            revision
         * @return a valid ResourceSetEntityUma if it exists, null otherwise
         */
        public ResourceSetEntityUma  getBySetIdAndRev(String setId, String rev);

}
