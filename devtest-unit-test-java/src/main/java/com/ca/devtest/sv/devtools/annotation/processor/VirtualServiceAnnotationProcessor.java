/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation.processor;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.annotation.Protocol;
import com.ca.devtest.sv.devtools.exception.VirtualServiceProcessorException;
import com.ca.devtest.sv.devtools.protocol.builder.DataProtocolBuilder;
import com.ca.devtest.sv.devtools.protocol.builder.ParamatrizedBuilder;
import com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolBuilderImpl;
import com.ca.devtest.sv.devtools.services.VirtualService;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceBuilder;
import com.ca.devtest.sv.devtools.utils.Utility;

/**
 * @author gaspa03
 *
 */
public class VirtualServiceAnnotationProcessor implements MethodProcessorAnnotation {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ca.devtest.sv.devtools.processor.MethodProcessorAnnotation#process()
	 */
	@Override
	public List<VirtualService> process(DevTestClient devTestClient, Annotation annotation) throws VirtualServiceProcessorException{
		 List<VirtualService>  result=new ArrayList<VirtualService>(1);
		 result.add(buildVirtualService(devTestClient, (DevTestVirtualService) annotation));
		return result;
	}

	private VirtualService buildVirtualService(DevTestClient devTestClient, DevTestVirtualService virtualService)
			throws VirtualServiceProcessorException {
		
		
		try {
			URL url = getClass().getClassLoader().getResource(virtualService.workingFolder());
			File rrPairsFolder = new File(url.toURI());
			VirtualServiceBuilder<?> virtualServiceBuilder = devTestClient.fromRRPairs(virtualService.serviceName(),
					rrPairsFolder);
			if (-1 != virtualService.port()) {
				virtualServiceBuilder.overHttp(virtualService.port(), virtualService.basePath());
			} else {
				// build Transport Protocol
				TransportProtocolBuilderImpl transportBuilder = new TransportProtocolBuilderImpl(
						virtualService.transport().value());
				Parameter[] transportParam = virtualService.transport().parameters();
				addParamsToBuilder(transportBuilder, transportParam);

				// add Transport Protocol
				virtualServiceBuilder.over(transportBuilder.build());
			}

			// build List of RequestDataProtocol
			Protocol[] requestDataProtocol = virtualService.requestDataProtocol();
			DataProtocolBuilder requestDataProtocolBuilder = null;
			// add request Data Protocol
			for (Protocol protocol : requestDataProtocol) {
				requestDataProtocolBuilder = new DataProtocolBuilder(protocol.value());
				addParamsToBuilder(requestDataProtocolBuilder, protocol.parameters());
				virtualServiceBuilder.addRequestDataProtocol(requestDataProtocolBuilder.build());
			}

			// build List of RespondDataProtocol
			Protocol[] responseDataProtocol = virtualService.responseDataProtocol();
			DataProtocolBuilder responseDataProtocolBuilder = null;
			// add respond Data Protocol
			for (Protocol protocol : responseDataProtocol) {
				responseDataProtocolBuilder = new DataProtocolBuilder(protocol.value());
				addParamsToBuilder(responseDataProtocolBuilder, protocol.parameters());
				virtualServiceBuilder.addRespondDataProtocol(responseDataProtocolBuilder.build());
			}
			// handle Parameters 
			Utility.addParamsToBuilder(virtualServiceBuilder, virtualService.parameters());

			return  virtualServiceBuilder.build();
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
