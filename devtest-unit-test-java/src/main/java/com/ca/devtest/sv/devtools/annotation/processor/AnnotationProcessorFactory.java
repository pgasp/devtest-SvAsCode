package com.ca.devtest.sv.devtools.annotation.processor;

import java.lang.annotation.Annotation;

import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServiceFromVrs;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServices;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServicesFromVrs;

/**
 * @author gaspa03
 *
 */
public class AnnotationProcessorFactory {

	/**
	 * @param annotation
	 * @return
	 */
	public static MethodProcessorAnnotation getMetodProcessor(Annotation annotation) {
		
		MethodProcessorAnnotation processor=new NopMethodProcessor();
		if( annotation instanceof DevTestVirtualService)
			processor= new VirtualServiceAnnotationProcessor();
		if(annotation instanceof DevTestVirtualServices)
			processor= new DevTestVirtualServicesAnnotationProcessor();
		if( annotation instanceof DevTestVirtualServiceFromVrs)
			processor= new VirtualServiceFromVrsAnnotationProcessor(); 

		if( annotation instanceof DevTestVirtualServicesFromVrs)
			processor= new DevTestVirtualServicesFromVrsAnnotationProcessor(); 
					

		return processor;
	}

}
