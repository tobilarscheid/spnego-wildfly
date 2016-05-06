package de.ctrlaltdel;

import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 * SpnegoAckLoginModule
 */
public class SpnegoAckLoginModule implements LoginModule {

	private Subject subject;
	private Principal principal;
	private Map sharedState;
	private CallbackHandler callbackHandler;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		this.subject = subject;
		this.sharedState = sharedState;
		this.callbackHandler = callbackHandler;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean login() throws LoginException {

		System.out.println("login called");
		return true;
	}

	@Override
	public boolean commit() throws LoginException {
		System.out.println("commit called");
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		return false;
	}

}
