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
package org.mitre.oauth2.repository;

import java.util.Collection;

import org.mitre.oauth2.model.ResourceServerDetailsEntityUma;

public interface OAuth2ResourceServerRepositoryUma{

	public ResourceServerDetailsEntityUma getById(Long id);

	public ResourceServerDetailsEntityUma getResourceServerByResourceServerId(String rsId);

	public ResourceServerDetailsEntityUma saveResourceServer(ResourceServerDetailsEntityUma rs);

	public void deleteResourceServer(ResourceServerDetailsEntityUma rs);

	public ResourceServerDetailsEntityUma updateResourceServer(Long id, ResourceServerDetailsEntityUma rs);

	public Collection<ResourceServerDetailsEntityUma> getAllResourceServers();

}
