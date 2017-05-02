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
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(DevTestVirtualServices.class)
public @interface DevTestVirtualService {
	String serviceName();
	int port() default -1;
	String basePath() default "/";
	Protocol transport() default @Protocol(ProtocolType.TPH_HTTP );
	Protocol[] requestDataProtocol() default{ } ;
	Protocol[] responseDataProtocol()default{ } ;
	Class preDeployClass() default Object.class;
	String workingFolder();
	Parameter[] parameters() default{};
}


