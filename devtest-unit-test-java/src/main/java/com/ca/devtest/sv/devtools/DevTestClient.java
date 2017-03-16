/**
 * 
 */
package com.ca.devtest.sv.devtools;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ca.devtest.sv.devtools.protocol.TransportProtocolDefinition;
import com.ca.devtest.sv.devtools.services.VirtualService;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceBuilder;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceRRPairsBuilder;

/**
 * @author gaspa03
 *
 */
public class DevTestClient {

	private VirtualServiceEnvironment vse;
	private Map<String, VirtualService> virtualServices;
	private String serviceName;
	private File rrPairsFolder;
	private TransportProtocolDefinition transportProtocol;
	
	private VirtualServiceBuilder virtualServiceBuilder= null;
	
	
	// private File zipFile

	/**
	 * @param registryHostName
	 * @param name
	 * @param userName
	 * @param password
	 * @param group
	 */
	public DevTestClient(String registryHostName, String name, String userName, String password, String group) {
		this.vse = new VirtualServiceEnvironment(registryHostName, name,userName,password, group);
		this.virtualServices = new HashMap<String, VirtualService>();
	}

	
	/**
	 * @param serviceName
	 * @param rrPairsFolder
	 * @return
	 * @throws IOException
	 */
	public VirtualServiceBuilder fromRRPairs(String serviceName, File rrPairsFolder) throws IOException {
		
		return new VirtualServiceRRPairsBuilder(serviceName, getVse(), rrPairsFolder);
	}
	
	

	protected VirtualServiceEnvironment getVse() {
		return vse;
	}

	protected void setVse(VirtualServiceEnvironment vse) {
		this.vse = vse;
	}

	protected Map<String, VirtualService> getVirtualServices() {
		return virtualServices;
	}

	protected void setVirtualServices(Map<String, VirtualService> virtualServices) {
		this.virtualServices = virtualServices;
	}
}
