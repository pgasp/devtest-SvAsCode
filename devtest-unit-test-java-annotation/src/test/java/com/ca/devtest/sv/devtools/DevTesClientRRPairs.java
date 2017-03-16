package com.ca.devtest.sv.devtools;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;

import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.Protocol;
import com.ca.devtest.sv.devtools.annotation.ProtocolType;
import com.ca.devtest.sv.devtools.application.SoapClient;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

/**
 * @author gaspa03
 *
 */
@DevTestVirtualServer(deployServiceToVse = "VSE")
public class DevTesClientRRPairs {
	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();

	/**
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	
	
	
	
	@DevTestVirtualService(serviceName = "lisa", port = 9001, basePath = "/lisa", rrpairsFolder = "rrpairs/soap", requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	@DevTestVirtualService(serviceName = "lisa2", port = 9001, basePath = "/lisa", rrpairsFolder = "rrpairs/soap", requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })

	@Test
	public void createSoapService() throws IOException, URISyntaxException {
		int port = 9001;
		String path = "/lisa";
		/* Test */
		SoapClient soapclient = new SoapClient("localhost", String.valueOf(port));
		URL url = getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml");
		File requestFile = new File(getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml").toURI());
		String request = FileUtils.readFileToString(requestFile, "UTF-8");
		String response = soapclient.callService(path, request);

	}

}
