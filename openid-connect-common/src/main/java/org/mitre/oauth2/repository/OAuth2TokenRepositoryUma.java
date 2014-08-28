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

import java.util.List;
import java.util.Set;

import org.mitre.oauth2.model.ResourceServerDetailsEntityUma;
import org.mitre.oauth2.model.OAuth2AccessTokenEntityUma;
import org.mitre.oauth2.model.OAuth2RefreshTokenEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface OAuth2TokenRepositoryUma {

	public OAuth2AccessTokenEntityUma saveAccessToken(OAuth2AccessTokenEntityUma token);

	public OAuth2RefreshTokenEntity getRefreshTokenByValue(String refreshTokenValue);

	public OAuth2RefreshTokenEntity getRefreshTokenById(Long Id);

	public void clearAccessTokensForRefreshToken(OAuth2RefreshTokenEntity refreshToken);

	public void removeRefreshToken(OAuth2RefreshTokenEntity refreshToken);

	public OAuth2RefreshTokenEntity saveRefreshToken(OAuth2RefreshTokenEntity refreshToken);

	public OAuth2AccessTokenEntityUma getAccessTokenByValue(String accessTokenValue);

	public OAuth2AccessTokenEntityUma getAccessTokenById(Long id);

	public void removeAccessToken(OAuth2AccessTokenEntityUma accessToken);

	public void clearTokensForResourceServer(ResourceServerDetailsEntityUma rs);

	public List<OAuth2AccessTokenEntityUma> getAccessTokensForResourceServer(ResourceServerDetailsEntityUma rs);

	public List<OAuth2RefreshTokenEntity> getRefreshTokensForResourceServer(ResourceServerDetailsEntityUma rs);

	public OAuth2AccessTokenEntityUma getByAuthentication(OAuth2Authentication auth);

//	public OAuth2AccessTokenEntityUma getAccessTokenForIdToken(OAuth2AccessTokenEntityUma idToken);

	public Set<OAuth2AccessTokenEntityUma> getAllAccessTokens();

	public Set<OAuth2RefreshTokenEntity> getAllRefreshTokens();

	public Set<OAuth2AccessTokenEntityUma> getAllExpiredAccessTokens();

	public Set<OAuth2RefreshTokenEntity> getAllExpiredRefreshTokens();

}
