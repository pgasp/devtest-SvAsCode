/**
 * 
 */
package com.ca.devtest.sv.devtools.protocol.builder;

import com.ca.devtest.sv.devtools.protocol.DataProtocolDefinition;

/**
 * @author gaspa03
 *
 */
public class DataProtocolBuilder extends ProtocolBuilder<DataProtocolDefinition> {

	public DataProtocolBuilder(String type) {
		super(type);
	}
	
	public  DataProtocolBuilder addParameter(String key, String value) {
		addKeyValue(key, value);

	return this;
}

	@Override
	protected DataProtocolDefinition doBuild() {
		return new  DataProtocolDefinition(typebuilder);
	}

	
	


	
	
}
