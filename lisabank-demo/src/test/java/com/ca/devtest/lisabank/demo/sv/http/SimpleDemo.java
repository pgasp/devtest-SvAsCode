/**
 * 
 */
package com.ca.devtest.lisabank.demo.sv.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ca.devtest.lisabank.demo.LisaBankClientApplication;
import com.ca.devtest.lisabank.demo.business.BankService;
import com.ca.devtest.lisabank.wsdl.Account;
import com.ca.devtest.lisabank.wsdl.User;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.annotation.Protocol;
import com.ca.devtest.sv.devtools.annotation.ProtocolType;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

/**
 * @author pascal.gasp@ca.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LisaBankClientApplication.class)
@DevTestVirtualServer(registryHost = "localhost", deployServiceToVse = "VSE")
public class SimpleDemo {
	static final Log logger = LogFactory.getLog(SimpleDemo.class);
	@Autowired
	private BankService bankServices;
	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();

	@DevTestVirtualService(serviceName = "SimpleDemo.getListUser", port = 9080, workingFolder = "UserServiceTest/getListUser/EJB3UserControlBean", basePath = "/itkoExamples/EJB3UserControlBean", requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	@Test
	public void getListUser() {
		User[] users = bankServices.getListUser();
		assertNotNull(users);
		assertEquals(9, users.length);
	}

	@DevTestVirtualService(serviceName = "SimpleDemo.getListUser",
			port = 9080, 
			workingFolder = "UserServiceTest/getListUser/template",
			basePath = "/itkoExamples/EJB3UserControlBean",
			parameters={
					@Parameter(name = "email", value = "toto.gasp@gmail.com"), 
					@Parameter(name = "login", value = "toto"),
					@Parameter(name = "pwd", value = "toto"),
					@Parameter(name = "nom", value = "toto GASP")},
			requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	@Test
	public void getListUserTemplate() {
		User[] users = bankServices.getListUser();
		printUsers(users);
		assertNotNull(users);
		assertEquals(1, users.length);
	}

	@DevTestVirtualService(serviceName = "SimpleDemo.getListUser", port = 9080, workingFolder = "UserServiceTest/getListUser/getListUser", basePath = "/itkoExamples/EJB3UserControlBean", requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	@Test
	public void getListUserDemo1User() {
		User[] users = bankServices.getListUser();
		assertNotNull(users);
		assertEquals(1, users.length);
	}
	private void printUsers(User[] users) {
		for (User user : users) {
			logger.info(user.getFname() + " " + user.getLname() + " " + user.getLogin());
		}

	}
}
