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

import org.mitre.uma.model.PermissionEntityUma;

/**
 * Interface for saving UMA permissions  as PermissionEntityUma entities.
 * 
 * @author tsitkov 
 *
 */
public interface PermissionRepositoryUma {

	/**
	 * Save a PermissionEntityUma to the repository
	 * 
	 * @param ticket	the PermissionEntityUma to save
	 * @return		the saved PermissionEntityUma
	 */
	public PermissionEntityUma save(PermissionEntityUma ticket);

	/**
	 * Get a permission ticket entity for this ticket
	 * 
	 * @param ticket	the PermissionEntityUma to query
	 * @return		the saved PermissionEntityUma
	 */
	public PermissionEntityUma getByPermissionTicketValue(String ticket);
}
