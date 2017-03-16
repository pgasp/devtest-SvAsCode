package com.ca.devtest.lisabank.demo.sv.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.lang.management.ManagementFactory;

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
import com.ca.devtest.lisabank.wsdl.User;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

import net.bytebuddy.agent.ByteBuddyAgent;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LisaBankClientApplication.class)
@DevTestVirtualServer(deployServiceToVse = "VSE")
public class UserServiceTest {
	static final Log logger=LogFactory.getLog(UserServiceTest.class);
	@Autowired
	private BankService bankServices;
	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();
	public static void loadAgent() {
	    logger.info("dynamically loading javaagent");
	    String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
	    int p = nameOfRunningVM.indexOf('@');
	    String pid = nameOfRunningVM.substring(0, p);
	   
	 
	    
	    try {
	    	 File  agentJar= new File("/Users/gaspa03/00-CA/00-Projects/00-Personal/devtestAgent/9.1.0/agentA/LisaAgent2.jar");
	    	 ByteBuddyAgent.attach(agentJar, pid, "url=tcp://localhost:2009,name=localhost_demo_lisa");
	     
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Test
	public void getListUser() {
		
		loadAgent();
		
		// Given

		// When
		User[] users = bankServices.getListUser();
		// Then
		printUsers(users);
		assertNotNull(users);
		assertEquals(9, users.length);
		
		User user=getUser("Admin", users);
		assertNotNull(user);
		
		assertEquals("Admin", user.getLname());

	}
	

	private void printUsers(User[] users) {
	for (User user : users) {
		logger.info(user.getFname() +" "+user.getLname() +" "+ user.getLogin());
	}
		
	}


	/**
	 * @param name
	 * @param users
	 * @return
	 */
	private User getUser(String name,User[] users ){
		
		User result= null;
		for (User user : users) {
			if(name.equals(user.getLname())){
				result=user;
			}
				
		}
		return result;
	}
}
