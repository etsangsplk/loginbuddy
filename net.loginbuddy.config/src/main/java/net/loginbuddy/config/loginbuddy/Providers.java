/*
 * Copyright (c) 2018. . All rights reserved.
 *
 * This software may be modified and distributed under the terms of the Apache License 2.0 license.
 * See http://www.apache.org/licenses/LICENSE-2.0 for details.
 *
 */

package net.loginbuddy.config.loginbuddy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import net.loginbuddy.common.config.Constants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Objects;
import java.util.logging.Logger;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Providers {

  private static final Logger LOGGER = Logger.getLogger(String.valueOf(Providers.class));

  @JsonProperty("provider")
  @JsonIgnore(false)
  private String provider;

  @JsonProperty("issuer")
  @JsonIgnore(false)
  private String issuer;

  @JsonProperty("client_id")
  @JsonIgnore(false)
  private String clientId;

  @JsonProperty("redirect_uri")
  @JsonIgnore(false)
  private String redirectUri;

  @JsonProperty("client_secret")
  @JsonIgnore(false)
  private String clientSecret;

  @JsonProperty("response_type")
  private String responseType;

  @JsonProperty("scope")
  private String scope;

  @JsonProperty("authorization_endpoint")
  private String authorizationEndpoint;

  @JsonProperty("openid_configuration_uri")
  private String openidConfigurationUri;

  @JsonProperty("token_endpoint")
  private String tokenEndpoint;

  @JsonProperty("userinfo_endpoint")
  private String userinfoEndpoint;

  @JsonProperty("jwks_uri")
  private String jwksUri;

  @JsonProperty("response_mode")
  private String responseMode;

  @JsonProperty("pkce")
  private boolean pkce;

  @JsonProperty("mappings")
  private JsonNode mappings;

  public Providers() {
    this.pkce = true;
    this.scope = Constants.OPENID_SCOPE.getKey();
    this.responseType = Constants.CODE.getKey();
  }

  public Providers(String issuer, String clientId, String redirectUri) {
    this();
    this.issuer = issuer;
    this.clientId = clientId;
    this.redirectUri = redirectUri;
  }

  public Providers(String issuer, String clientId, String redirectUri, String clientSecret) {
    this(issuer, clientId, redirectUri);
    this.clientSecret = clientSecret;
  }

  /**
   * Updates the net.loginbuddy.service.config and removes the oidc net.loginbuddy.service.config endpoint. This way we do not do another API call to retrieve it
   * again. It does NOT update 'issuer' or 'provider'
   */
  public void enhanceToFull(Providers config) {
    this.clientId = config.getClientId();
    this.clientSecret = config.getClientSecret();
    this.redirectUri = config.getRedirectUri();
    this.scope = config.getScope();
    this.authorizationEndpoint = config.getAuthorizationEndpoint();
    this.tokenEndpoint = config.getTokenEndpoint();
    this.userinfoEndpoint = config.getUserinfoEndpoint();
    this.jwksUri = config.getJwksUri();
    this.responseType = config.getResponseType();
    this.openidConfigurationUri = null;
  }

  public String getProvider() {
    return provider;
  }

  public String getIssuer() {
    return issuer;
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public String getRedirectUri() {
    return redirectUri;
  }

  public String getAuthorizationEndpoint() {
    return authorizationEndpoint;
  }

  public String getOpenidConfigurationUri() {
    return openidConfigurationUri;
  }

  public String getTokenEndpoint() {
    return tokenEndpoint;
  }

  public String getUserinfoEndpoint() {
    return userinfoEndpoint;
  }

  public String getJwksUri() {
    return jwksUri;
  }

  public String getScope() {
    return scope;
  }

  public String getResponseType() {
    return responseType;
  }

 public boolean getPkce() {
    return pkce;
  }

  public String getResponseMode() {
    return responseMode;
  }

  public JSONObject getMappings() {
    try {
      if(mappings == null) {
        return new JSONObject();
      }
      return (JSONObject) new JSONParser().parse(mappings.toString());
    } catch (ParseException e) {
      LOGGER.warning(String.format("The mapping object is invalid: %s", mappings == null ? "" : mappings.toString()));
      return new JSONObject();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Providers providers = (Providers) o;
    return provider.equalsIgnoreCase(providers.provider);
  }

  @Override
  public int hashCode() {
    return Objects.hash(provider);
  }
}