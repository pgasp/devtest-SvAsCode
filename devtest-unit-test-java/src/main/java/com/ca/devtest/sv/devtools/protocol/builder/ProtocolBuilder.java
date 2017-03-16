/**
 * 
 */
package com.ca.devtest.sv.devtools.protocol.builder;

import java.util.HashMap;
import java.util.Map;

import com.ca.devtest.sv.devtools.protocol.BaseProtocol;

/**
 * @author gaspa03
 *
 */
public abstract class ProtocolBuilder<T extends BaseProtocol> {

	protected final String typebuilder ;
	protected Map<String, String> parameters= new HashMap<String, String>();
	
	public ProtocolBuilder( String type) {
		this.typebuilder=type;
	}

	
	 public  void  addKeyValue(String key, String value) {
		parameters.put(key, value);
		
	}

	
	/**
	 * @return
	 */
	public T  build(){
		
		Object  baseProtocol = doBuild();
		((T)baseProtocol).getParameters().putAll(parameters);
		return (T)baseProtocol;
	}


	

	/**
	 * @return
	 */
	protected  abstract <T> T doBuild() ;

	
}
