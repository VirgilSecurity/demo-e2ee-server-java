package com.virgilsecurity.e2ee.demo.server.utils;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.virgilsecurity.e2ee.demo.server.domain.UserInfo;

public class UserUtils {

	@SuppressWarnings("unchecked")
	public static UserInfo extractUserInfo(Principal principal) {
		if (principal != null) {
			OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
			Authentication authentication = oAuth2Authentication.getUserAuthentication();
			Map<String, String> details = (Map<String, String>) authentication.getDetails();

			return new UserInfo(details.get("name"), details.get("email"));
		}
		return null;
	}

}
