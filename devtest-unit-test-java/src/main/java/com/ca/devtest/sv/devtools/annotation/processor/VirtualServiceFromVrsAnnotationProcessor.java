/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation.processor;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServiceFromVrs;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.exception.VirtualServiceProcessorException;
import com.ca.devtest.sv.devtools.protocol.builder.ParamatrizedBuilder;
import com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolFromVrsBuilder;
import com.ca.devtest.sv.devtools.services.VirtualService;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceBuilder;

/**
 * @author gaspa03
 *
 */
public class VirtualServiceFromVrsAnnotationProcessor implements MethodProcessorAnnotation {

	/* (non-Javadoc)
	 * @see com.ca.devtest.sv.devtools.annotation.processor.MethodProcessorAnnotation#process(com.ca.devtest.sv.devtools.DevTestClient, java.lang.annotation.Annotation)
	 */
	@Override
	public VirtualService process(DevTestClient devTestClient, Annotation annotation)
			throws VirtualServiceProcessorException {
		return buildVirtualService(devTestClient,(DevTestVirtualServiceFromVrs)annotation);
	}
	
	/**
	 * @param devTestClient
	 * @param virtualService
	 * @return
	 * @throws VirtualServiceProcessorException
	 */
	private VirtualService buildVirtualService(DevTestClient devTestClient, DevTestVirtualServiceFromVrs virtualService)
			throws VirtualServiceProcessorException {
		try {
			URL url = getClass().getClassLoader().getResource(virtualService.workingFolder());
			File workingFolder = new File(url.toURI());
			
			VirtualServiceBuilder<?> virtualServiceBuilder = devTestClient.fromRRPairs(virtualService.serviceName(),workingFolder);
			File vrsFile= new File(workingFolder,virtualService.vrsConfig().value());
			// build Transport Protocol
			TransportProtocolFromVrsBuilder transportBuilder = new TransportProtocolFromVrsBuilder(vrsFile);
				Parameter[] transportParam = virtualService.vrsConfig().parameters();
				addParamsToBuilder(transportBuilder, transportParam);
				// add Transport Protocol
				virtualServiceBuilder.over(transportBuilder.build());

			return virtualServiceBuilder.build();
		} catch (Exception error) {
			throw new VirtualServiceProcessorException("Error during ", error);
		}

	}
	/**
	 * @param builder
	 * @param parameters
	 */
	private void addParamsToBuilder(ParamatrizedBuilder builder, Parameter[] parameters) {

		for (Parameter parameter : parameters) {
			builder.addKeyValue(parameter.name(), parameter.value());
		}

	}
}
