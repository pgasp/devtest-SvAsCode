/**
 * 
 */
package com.ca.devtest.sv.devtools.utils;

import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.protocol.builder.ParamatrizedBuilder;

/**
 * @author gaspa03
 *
 */
public class Utility {

	
	/**
	 * @param builder
	 * @param parameters
	 */
	public static void addParamsToBuilder(ParamatrizedBuilder builder, Parameter[] parameters) {

		for (Parameter parameter : parameters) {
			builder.addKeyValue(parameter.name(), parameter.value());
		}

	}
}
