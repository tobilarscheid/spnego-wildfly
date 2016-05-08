package de.ctrlaltdel;

import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import de.ctrlaltdel.SpnegoAuthenticationMechanism.SimplePrincipal;

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

	@SuppressWarnings("unchecked")
	@Override
	public boolean login() throws LoginException {
		System.out.println("login called1");
		NameCallback nc = new NameCallback("name");
		try {
			callbackHandler.handle(new Callback[] { nc });
		} catch (Exception x) {
			x.printStackTrace();
			throw new LoginException(x.getMessage());
		}

		String name = nc.getName();
		String credential = null;

		SimplePrincipal simplePrincipal = new SimplePrincipal(name, credential);

		sharedState.put("javax.security.auth.login.name", simplePrincipal.getName());
		sharedState.put("javax.security.auth.login.password", simplePrincipal.getCredential());

		this.principal = simplePrincipal;
		System.out.println("login called2");
		return true;
	}

	@Override
	public boolean commit() throws LoginException {
		subject.getPrincipals().add(principal);
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
