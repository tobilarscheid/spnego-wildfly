package de.ctrlaltdel;

import java.security.Principal;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.extension.undertow.security.AccountImpl;

import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.api.AuthenticationMechanismFactory;
import io.undertow.security.api.SecurityContext;
import io.undertow.security.idm.Account;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormParserFactory;

/**
 * SpnegoHandler
 */
public class SpnegoAuthenticationMechanism implements AuthenticationMechanism {

	private static final Logger LOG = LoggerFactory.getLogger(SpnegoAuthenticationMechanism.class);

	private final String mechanismName;

	static final long UPTIME = System.currentTimeMillis();

	private SpnegoAuthenticationMechanism(String mechanismName) {
		this.mechanismName = mechanismName;
		System.out.println("authentication mechanism constructor called");

	}

	@Override
	public AuthenticationMechanismOutcome authenticate(HttpServerExchange exchange, SecurityContext securityContext) {

		System.out.println("authenticate called");
		HashSet<String> roles = new HashSet<>();
		roles.add("loginUser");
		Account account = new AccountImpl(new SimplePrincipal("me", "me"), roles, "me");
		securityContext.authenticationComplete(account, mechanismName, true);
		return AuthenticationMechanismOutcome.AUTHENTICATED;

	}

	@Override
	public ChallengeResult sendChallenge(HttpServerExchange exchange, SecurityContext securityContext) {
		System.out.println("send challenge called");
		return new ChallengeResult(true, HttpServletResponse.SC_UNAUTHORIZED);
	}

	public static final class Factory implements AuthenticationMechanismFactory {
		@Override
		public AuthenticationMechanism create(String mechanismName, FormParserFactory formParserFactory,
				Map<String, String> properties) {
			System.out.println("factpry called");
			return new SpnegoAuthenticationMechanism(mechanismName);
		}
	}

	public static class SimplePrincipal implements Principal {
		private final String name;
		private final String credential;

		public SimplePrincipal(String name, String credential) {
			this.credential = credential;
			int idx = name.indexOf('@');
			this.name = 0 < idx ? name.substring(0, idx) : name;
		}

		@Override
		public String getName() {
			return name;
		}

		public String getCredential() {
			return credential;
		}

		@Override
		public String toString() {
			return name;
		}
	}

}
