package com.mpx.birjan.core;

import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.handler.RequestData;
import org.apache.ws.security.message.token.UsernameToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UsernameTokenValidator extends
		org.apache.ws.security.validate.UsernameTokenValidator {

	@Autowired(required=false)
	private AuthenticationManager authenticationManager;

	@Override
	protected void verifyPlaintextPassword(UsernameToken usernameToken,
			RequestData data) throws WSSecurityException {
		String user = usernameToken.getName();
		String password = usernameToken.getPassword();
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				user, password);
		authentication = authenticationManager.authenticate(authentication);
		if (!authentication.isAuthenticated()) {
			throw new WSSecurityException(
					WSSecurityException.FAILED_AUTHENTICATION);
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
