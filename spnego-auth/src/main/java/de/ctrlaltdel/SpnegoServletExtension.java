package de.ctrlaltdel;

import de.ctrlaltdel.SpnegoAuthenticationMechanism.Factory;

import javax.servlet.ServletContext;

import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;

/**
 * SpnegoServletExtension
 */
public class SpnegoServletExtension implements ServletExtension {
    @Override
    public void handleDeployment(DeploymentInfo deploymentInfo, ServletContext servletContext) {

        deploymentInfo.addAuthenticationMechanism("spnego", new Factory());
    }
}