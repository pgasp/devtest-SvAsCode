/**
 * 
 */
package com.ca.devtest.sv.devtools.services;

import java.io.File;
import java.io.IOException;

import com.ca.devtest.sv.devtools.VirtualServiceEnvironment;

/**
 * @author gaspa03
 *
 */
public final class VirtualService {
	private final String name;
	private final VirtualServiceEnvironment vse;
	private File packedVirtualService=null;
	
	public VirtualService( String name, VirtualServiceEnvironment vse) {
		super();
		if (name == null)
			throw new IllegalArgumentException("Service Name cannot be null");
		this.name = name;
		this.vse = vse;
		
	}


	/**
	 * @param packedVirtualService the packedVirtualService to set
	 */
	public final void setPackedVirtualService(File packedVirtualService) {
		this.packedVirtualService = packedVirtualService;
	}


	/**
	 * @return
	 */
	protected VirtualServiceEnvironment getVse() {
		return vse;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	public String getGroup() {
		return getVse().getGroup();
	}

	
	/**
	 * @return
	 */
	public final File getPackedVirtualService() {
		return packedVirtualService;
	}


	/**
	 * @throws IOException
	 */
	public void deploy() throws IOException {
		getVse().deployService(this);
		
	}
	/**
	 * @throws IOException
	 */
	public void unDeploy() throws IOException {
		
		getVse().unDeployService(this);
		
	}

	

}
