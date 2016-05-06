package de.ctrlaltdel;

import de.ctrlaltdel.SpnegoAuthenticationMechanism.SimplePrincipal;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import java.security.Principal;
import java.util.Map;

/**
 * SpnegoAckLoginModule
 */
public class SpnegoAckLoginModule implements LoginModule {

    private Subject subject;
    private Principal principal;
    private Map sharedState;
    private CallbackHandler callbackHandler;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.sharedState = sharedState;
        this.callbackHandler = callbackHandler;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean login() throws LoginException {

        NameCallback nc = new NameCallback("name");
        PasswordCallback pc = new PasswordCallback("password", false);
        try {
            callbackHandler.handle(new Callback[]{nc, pc});
        } catch (Exception x) {
            throw new LoginException(x.getMessage());
        }

        String name = nc.getName();
        char[] passwordChar = pc.getPassword();
        String credential = passwordChar != null ? new String(passwordChar) : null;

        long loginTime = Long.valueOf(credential);
        if (loginTime < SpnegoAuthenticationMechanism.UPTIME) {
            return false;
        }

        SimplePrincipal simplePrincipal = new SimplePrincipal(name, credential);

        sharedState.put("javax.security.auth.login.name", simplePrincipal.getName());
        sharedState.put("javax.security.auth.login.password", simplePrincipal.getCredential());

        this.principal = simplePrincipal;
        return true;
    }

    @Override
    public boolean commit() throws LoginException {
        if (principal == null) {
            return false;
        }
        subject.getPrincipals().add(principal);
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
