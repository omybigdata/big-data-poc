package Encryption.KeyManager

import groovyx.net.http.HttpResponseException
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.GrantedAuthorityImpl
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient

class PlatformAuthenticationProvider implements AuthenticationProvider
{
	public static final String API_ROOT = "http://localhost:8080/KeyManager/"

	Authentication authenticate(Authentication authentication) {
		if (authentication instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication

			String username = token.getPrincipal()
			String password = token.getCredentials()

			def roles = []
			RESTClient client = new RESTClient(API_ROOT, ContentType.JSON)
			try {
				HttpResponseDecorator r = (HttpResponseDecorator) client.get(
							path: "users/${username}/roles",
							query: ['app-id': 'user-management']	)

				JSONArray json = (JSONArray) r.data
				json.each {String roleName ->
					roles << new GrantedAuthorityImpl(roleName)
				}
			}
			catch (HttpResponseException e) {
				e.printStackTrace()
			}

			if (roles) {
				return new UsernamePasswordAuthenticationToken(username, password, roles)
			}
		}
		return null
	}

	boolean supports(Class<? extends Object> aClass) {
		if (aClass.isAssignableFrom(UsernamePasswordAuthenticationToken.class)) {
			return true
		}

		return false
	}
}
