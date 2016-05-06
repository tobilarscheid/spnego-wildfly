package de.ctrlaltdel;

import javax.servlet.ServletContext;

import de.ctrlaltdel.SpnegoAuthenticationMechanism.Factory;
import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;

/**
 * SpnegoServletExtension
 */
public class SpnegoServletExtension implements ServletExtension {
	@Override
	public void handleDeployment(DeploymentInfo deploymentInfo, ServletContext servletContext) {
		System.out.println("deployment handler called");
		deploymentInfo.addAuthenticationMechanism("spnego", new Factory());
	}
}