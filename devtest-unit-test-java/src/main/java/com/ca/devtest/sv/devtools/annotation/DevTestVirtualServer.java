/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gaspa03
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DevTestVirtualServer {

	/**
	 * @return registry serveur name by default localhost
	 */
	String registryHost() default "localhost";
	/**
	 * @return vse name by default VSE
	 */
	String deployServiceToVse() default "VSE";
	
	/**
	 * @return devtest user by default svpower
	 */
	String login() default "svpower";
	/**
	 * @return devtest passord by default svpower
	 */
	String password() default "svpower";
	String groupName() default "";

}

