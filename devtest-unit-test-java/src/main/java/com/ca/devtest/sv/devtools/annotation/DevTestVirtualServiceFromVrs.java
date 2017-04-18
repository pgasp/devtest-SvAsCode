/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gaspa03
 * Define Virtual service using VRS fragment file
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(DevTestVirtualServicesFromVrs.class)
public @interface DevTestVirtualServiceFromVrs {
	String serviceName();
	String workingFolder();
	Config vrsConfig();
	
	
}


