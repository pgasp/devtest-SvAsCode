package com.ca.devtest.sv.devtools.annotation.processor;

import java.lang.annotation.Annotation;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.exception.VirtualServiceProcessorException;
import com.ca.devtest.sv.devtools.services.VirtualService;

/**
 * @author gaspa03
 *
 */
public interface MethodProcessorAnnotation {

	/**
	 * @return
	 */
	VirtualService process(DevTestClient devTestClient, Annotation annotation) throws VirtualServiceProcessorException;

}
