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

	@DevTestVirtualService(serviceName = "SimpleDemo.getListUser", port = 9080, workingFolder = "UserServiceTest/getListUser/getListUser", basePath = "/itkoExamples/EJB3UserControlBean", requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	@Test
	public void getListUserDemo1User() {
		User[] users = bankServices.getListUser();
		assertNotNull(users);
		assertEquals(1, users.length);
	}

	@DevTestVirtualService(serviceName = "EJB3AccountControlBean", port = 9080, basePath = "/itkoExamples/EJB3AccountControlBean", workingFolder = "AccountServiceTest/createUserWithCheckingAccount/EJB3AccountControlBean", requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	@DevTestVirtualService(serviceName = "EJB3UserControlBean", port = 9080, basePath = "/itkoExamples/EJB3UserControlBean", workingFolder = "AccountServiceTest/createUserWithCheckingAccount/EJB3UserControlBean", requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	@DevTestVirtualService(serviceName = "TokenBean", port = 9080, basePath = "/itkoExamples/TokenBean", workingFolder = "AccountServiceTest/createUserWithCheckingAccount/TokenBean", requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	@Test
	public void createUserWithCheckingAccount() {

		// Given
		String user = "pascal";
		String password = "password";
		int amount = 1000;
		// prepare context
		// bankServices.deleteUser(user);
		// When
		Account account = bankServices.createUserWithCheckingAccount(user, password, amount);
		// Then
		assertNotNull(account);
		assertEquals("Le balance du compte n'est pas conforme", amount, account.getBalance().intValue());
	}

}
