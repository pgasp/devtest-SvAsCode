/**
 * 
 */
package com.ca.devtest.sv.devtools.junit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.annotation.Protocol;
import com.ca.devtest.sv.devtools.annotation.processor.DevTestAnnotationProcessor;
import com.ca.devtest.sv.devtools.protocol.builder.DataProtocolBuilder;
import com.ca.devtest.sv.devtools.protocol.builder.ParamatrizedBuilder;
import com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolBuilderImpl;
import com.ca.devtest.sv.devtools.services.VirtualService;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceBuilder;

/**
 * @author gaspa03
 *
 */
public class VirtualServicesRule implements TestRule {

	private static final Log LOGGER = LogFactory.getLog(VirtualServicesRule.class);

	
	public  VirtualServicesRule() {
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.junit.rules.TestRule#apply(org.junit.runners.model.Statement,
	 * org.junit.runner.Description)
	 */
	public Statement apply(final Statement base, final Description description) {

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				if (!clazzNeedVirtualServices(description.getTestClass())) {
					LOGGER.info(description.getTestClass() + "is not annoted by DevTestVirtualServer");
					base.evaluate();
				} else {

					List<VirtualService> virtualServices = null;
					try {
						virtualServices = processMethodAnnotations(description);
						deployVirtualServices(virtualServices);
						base.evaluate();
					} finally {
						Thread.sleep(1000);
						unDeployVirtualServices(virtualServices);
					}

				}
			}

		};

	}

	/**
	 * @param virtualServices
	 */
	private void deployVirtualServices(List<VirtualService> virtualServices) {

		if (null != virtualServices) {
			for (VirtualService virtualService : virtualServices) {
				try {
					LOGGER.debug("Deploy virtual service " + virtualService.getName() + ".....");
					virtualService.deploy();
					LOGGER.debug("Virtual service " + virtualService.getName() + " deployed!");
				} catch (IOException e) {
					throw new RuntimeException("Error when try to deploy Virtual Service  " + virtualService.getName());
				}
			}
		}

	}

	/**
	 * @param virtualServices
	 */
	private void unDeployVirtualServices(List<VirtualService> virtualServices) {

		if (null != virtualServices) {
			for (VirtualService virtualService : virtualServices) {
				try {
					LOGGER.debug("unDeploy virtual service " + virtualService.getName() + ".....");
					virtualService.unDeploy();
					LOGGER.debug("Virtual service " + virtualService.getName() + " unDeployed!");

				} catch (Exception error) {

					throw new RuntimeException(
							"Error when try to unDeploy Virtual Service  " + virtualService.getName());

				}
			}
		}

	}

	private DevTestClient buildDevtestClient(Class<?> clazz) {
		DevTestVirtualServer virtualServer = clazz.getAnnotation(DevTestVirtualServer.class);
		return new DevTestClient(virtualServer.registryHost(), virtualServer.deployServiceToVse(),
				virtualServer.login(), virtualServer.password(), virtualServer.groupName());

	}

	/**
	 * @param testClass
	 * @return
	 */
	private boolean clazzNeedVirtualServices(Class<?> clazz) {

		return null != clazz.getAnnotation(DevTestVirtualServer.class);

	}

	/**
	 * @param testClass
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	private List<VirtualService> processMethodAnnotations(Description description) {
		List<VirtualService> virtualServices = new ArrayList<VirtualService>();
		try {
			 LOGGER.debug("Process annotation for method "+description.getMethodName());
			Class<?> testClazz = description.getTestClass();
			DevTestAnnotationProcessor devtestProcessor=new DevTestAnnotationProcessor(testClazz);

			Method method = testClazz.getMethod(description.getMethodName(), new Class[] {});

			
			virtualServices.addAll(devtestProcessor.process(method));
			
			
			
		} catch (Exception error) {

			throw new RuntimeException("Error when try to build Virtual Service over " + description.getDisplayName(),
					error);
		}

		return virtualServices;

	}

	
}
