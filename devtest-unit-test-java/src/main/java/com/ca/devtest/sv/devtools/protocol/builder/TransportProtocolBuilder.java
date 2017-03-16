/**
 * 
 */
package com.ca.devtest.sv.devtools.protocol.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ca.devtest.sv.devtools.protocol.DataProtocolDefinition;
import com.ca.devtest.sv.devtools.protocol.TransportProtocolDefinition;
import com.ca.devtest.sv.devtools.type.DataProtocolType;

/**
 * @author gaspa03
 *
 */
public class TransportProtocolBuilder extends ProtocolBuilder<TransportProtocolDefinition> {

	private List<DataProtocolDefinition> requestDataProtocol = new ArrayList<DataProtocolDefinition>();
	private List<DataProtocolDefinition> responseDataProtocol = new ArrayList<DataProtocolDefinition>();

	public TransportProtocolBuilder(String type) {
		super(type);
	}
	

	public TransportProtocolBuilder addParameter(String key, String value) {
		addKeyValue(key, value);

		return this;
	}

	@Override
	protected TransportProtocolDefinition doBuild() {
		TransportProtocolDefinition tph = new TransportProtocolDefinition(typebuilder);
		tph.setRequestSide(requestDataProtocol);
		tph.setResponseSide(responseDataProtocol);
		return tph;

	}

	/**
	 * @param type
	 * @return
	 */
	public TransportProtocolBuilder addRequestDataProtocol(DataProtocolBuilder dataProtocolBuilder) {
		requestDataProtocol.add(dataProtocolBuilder.build());
		return this;
	}

	/**
	 * @param type
	 * @return
	 */
	public TransportProtocolBuilder addResponseDataProtocol(DataProtocolBuilder dphBuilder) {
		responseDataProtocol.add(dphBuilder.build());
		return this;
	}

}
