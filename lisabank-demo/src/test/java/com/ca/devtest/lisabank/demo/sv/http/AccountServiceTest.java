/**
 * 
 */
package com.ca.devtest.lisabank.demo.sv.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ca.devtest.lisabank.demo.LisaBankClientApplication;
import com.ca.devtest.lisabank.demo.business.BankService;
import com.ca.devtest.lisabank.wsdl.Account;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.Protocol;
import com.ca.devtest.sv.devtools.annotation.ProtocolType;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

/**
 * @author gaspa03
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LisaBankClientApplication.class)
@DevTestVirtualServer(deployServiceToVse = "VSE")
public class AccountServiceTest {
	@Autowired
	private BankService bankServices;
	
	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();

	
	@DevTestVirtualService(serviceName = "EJB3AccountControlBean", port = 9080, basePath = "/itkoExamples/EJB3AccountControlBean", 
			rrpairsFolder = "AccountServiceTest/createUserWithCheckingAccount/EJB3AccountControlBean", requestDataProtocol = {@Protocol(ProtocolType.DPH_SOAP) })
			@DevTestVirtualService(serviceName = "EJB3UserControlBean", port = 9080, basePath = "/itkoExamples/EJB3UserControlBean",
			rrpairsFolder = "AccountServiceTest/createUserWithCheckingAccount/EJB3UserControlBean", requestDataProtocol = {@Protocol(ProtocolType.DPH_SOAP) })
			@DevTestVirtualService(serviceName = "TokenBean", port = 9080, basePath = "/itkoExamples/TokenBean",
			rrpairsFolder = "AccountServiceTest/createUserWithCheckingAccount/TokenBean", requestDataProtocol = {@Protocol(ProtocolType.DPH_SOAP) })
	
	@Test
	public void createUserWithCheckingAccount() {

		// Given
		String user = "pascal";
		String password = "password";
		int amount = 1000;
		// prepare context
		//bankServices.deleteUser(user);
		// When
		Account account = bankServices.createUserWithCheckingAccount(user, password, amount);
		// Then
		assertNotNull(account);
		assertEquals("Le balance du compte n'est pas conforme",amount, account.getBalance().intValue());
	}
}
