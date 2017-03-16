/**
 * 
 */
package com.ca.devtest.lisabank.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author gaspa03
 *
 */
@SpringBootApplication
@ComponentScan({"com.ca.devtest.lisabank","com.itko.examples.ejb3"})

public class LisaBankClientApplication {
	  public static void main(String[] args) {
	        SpringApplication.run(LisaBankClientApplication.class, args);
	    }
}
