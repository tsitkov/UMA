/*******************************************************************************
 * Copyright 2015 MIT Kerberos and Internet Trust Consortium
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

import org.mitre.uma.model.RptEntityUma;

/**
 * Interface for saving UMA RPT tokens  as RptEntityUma entities.
 * 
 * @author tsitkov 
 *
 */
public interface RptRepositoryUma {

	/**
	 * Save an RptEntityUma to the repository
	 * 
	 * @param rpt	the RptEntityUma to save
	 * @return	the saved RptEntityUma
	 */
	public RptEntityUma save(RptEntityUma rpt);

	public RptEntityUma getByRptValue(String  rpt);
	public RptEntityUma getByPermissionTicketValue(String  ticket);
}
