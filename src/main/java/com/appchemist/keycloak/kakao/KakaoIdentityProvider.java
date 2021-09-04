package com.appchemist.keycloak.kakao;

import com.fasterxml.jackson.databind.JsonNode;
import org.keycloak.broker.oidc.AbstractOAuth2IdentityProvider;
import org.keycloak.broker.oidc.OAuth2IdentityProviderConfig;
import org.keycloak.broker.oidc.mappers.AbstractJsonUserAttributeMapper;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.broker.provider.IdentityBrokerException;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.broker.social.SocialIdentityProvider;
import org.keycloak.events.EventBuilder;
import org.keycloak.models.KeycloakSession;

import java.io.IOException;

public class KakaoIdentityProvider extends AbstractOAuth2IdentityProvider<OAuth2IdentityProviderConfig> implements SocialIdentityProvider<OAuth2IdentityProviderConfig> {
    public static final String AUTH_URL = "https://kauth.kakao.com/oauth/authorize";
    public static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    public static final String PROFILE_URL = "https://kapi.kakao.com/v2/user/me";

    public KakaoIdentityProvider(KeycloakSession session, OAuth2IdentityProviderConfig config) {
        super(session, config);

        config.setAuthorizationUrl(AUTH_URL);
        config.setTokenUrl(TOKEN_URL);
        config.setUserInfoUrl(PROFILE_URL);
    }

    @Override
    protected boolean supportsExternalExchange() {
        return true;
    }

    @Override
    protected String getProfileEndpointForValidation(EventBuilder event) {
        return PROFILE_URL;
    }


    @Override
    protected BrokeredIdentityContext extractIdentityFromProfile(EventBuilder event, JsonNode profile) {
        BrokeredIdentityContext user = new BrokeredIdentityContext(getJsonProperty(profile, "id"));

        JsonNode kakaoProperties = profile.get("properties");
        user.setUsername(kakaoProperties.get("nickname").asText());

        JsonNode kakaoAccount = profile.get("kakao_account");
        user.setEmail(kakaoAccount.get("email").asText());

        user.setIdpConfig(getConfig());
        user.setIdp(this);

        AbstractJsonUserAttributeMapper.storeUserProfileForMapper(user, profile, getConfig().getAlias());

        return user;
    }

    @Override
    protected BrokeredIdentityContext doGetFederatedIdentity(String accessToken) {
        logger.debug("doGetFederatedIdentity()");
        try {
            BrokeredIdentityContext identity = extractIdentityFromProfile(null, doHttpGet(PROFILE_URL, accessToken));

            identity.setEmail(identity.getEmail());

            if (identity.getUsername() == null || identity.getUsername().length() == 0) {
                identity.setUsername(identity.getEmail());
            }

            return identity;
        } catch (Exception e) {
            throw new IdentityBrokerException("Could not obtain user profile from linkedIn.", e);
        }
    }

    @Override
    protected String getDefaultScopes() {
        return "";
    }

    private JsonNode doHttpGet(String url, String bearerToken) throws IOException {
        JsonNode response = SimpleHttp.doGet(url, session).header("Authorization", "Bearer " + bearerToken).asJson();

        if (response.hasNonNull("serviceErrorCode")) {
            throw new IdentityBrokerException("Could not obtain response from [" + url + "]. Response from server: " + response);
        }

        return response;
    }
}