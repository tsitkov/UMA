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
package org.mitre.uma.rs.service.impl;

import static org.mitre.discovery.util.JsonUtils.getAsBoolean;
import static org.mitre.discovery.util.JsonUtils.getAsEncryptionMethodList;
import static org.mitre.discovery.util.JsonUtils.getAsJweAlgorithmList;
import static org.mitre.discovery.util.JsonUtils.getAsJwsAlgorithmList;
import static org.mitre.discovery.util.JsonUtils.getAsString;
import static org.mitre.discovery.util.JsonUtils.getAsStringList;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.mitre.uma.client.service.ServerConfigurationServiceUma;
import org.mitre.uma.config.ServerConfigurationUma;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.client.RestTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DynamicServerConfigurationServiceUma implements ServerConfigurationServiceUma {

	private static Logger logger = LoggerFactory.getLogger(DynamicServerConfigurationServiceUma.class);

        private HttpClient httpClient = new SystemDefaultHttpClient();
        private HttpComponentsClientHttpRequestFactory httpFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        private JsonParser parser = new JsonParser();
        // map of issuer -> server configuration, loaded dynamically from service discovery
        private LoadingCache<String, ServerConfigurationUma> servers;

        public DynamicServerConfigurationServiceUma() {
                // initialize the cache
                servers = CacheBuilder.newBuilder().build(new ServiceConfigurationFetcherUma());
        }

        @Override
        public ServerConfigurationUma getServerConfiguration(String issuer) {
                try {
                        return servers.get(issuer);
                } catch (UncheckedExecutionException ue) {
                        logger.warn("Couldn't load UMA AS configuration for " + issuer, ue);
                        return null;
                } catch (ExecutionException e) {
                        logger.warn("Couldn't load UMA AS configuration for " + issuer, e);
                        return null;
                }
        }
       
        private class ServiceConfigurationFetcherUma extends CacheLoader<String, ServerConfigurationUma> {
                private HttpClient httpClient = new SystemDefaultHttpClient();
                private HttpComponentsClientHttpRequestFactory httpFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
                private JsonParser parser = new JsonParser();

                @Override
                public ServerConfigurationUma load(String issuer) throws Exception {
                        RestTemplate restTemplate = new RestTemplate(httpFactory);

                        // data holder
                        ServerConfigurationUma conf = new ServerConfigurationUma();
                        String url = issuer + "/.well-known/uma-configuration";
                        String jsonString = restTemplate.getForObject(url, String.class);
                        JsonElement parsed = parser.parse(jsonString);
                        if (parsed.isJsonObject()) {

                                JsonObject o = parsed.getAsJsonObject();
                                // sanity checks
                                if (!o.has("issuer")) {
                                        throw new IllegalStateException("Returned object did not have an 'issuer' field");
                                }

                                if (!issuer.equals(o.get("issuer").getAsString())) {
                                        throw new IllegalStateException("Discovered issuers didn't match, expected " + issuer + " got " + o.get("issuer").getAsString());
                                }

                                conf.setIssuer(o.get("issuer").getAsString());
                                conf.setVersion(o.get("version").getAsString());

                                return conf;
                        } else {
                                throw new IllegalStateException("Couldn't parse server discovery results for " + url);
                        }

                 }
         }
}
