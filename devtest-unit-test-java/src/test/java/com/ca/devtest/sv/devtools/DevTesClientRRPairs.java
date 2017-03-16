package com.ca.devtest.sv.devtools;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.ca.devtest.sv.devtools.application.DoItClient;
import com.ca.devtest.sv.devtools.application.SoapClient;
import com.ca.devtest.sv.devtools.protocol.builder.DataProtocolBuilder;
import com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolBuilder;
import com.ca.devtest.sv.devtools.services.VirtualService;
import com.ca.devtest.sv.devtools.type.DataProtocolType;
import com.ca.devtest.sv.devtools.type.TransportProtocolType;

public class DevTesClientRRPairs {

	DevTestClient devTestClient = new DevTestClient("localhost", "VSE", "svpower", "svpower", "DevTesClientRRPairs");
	DoItClient clientDoIt = new DoItClient("localhost", "9001");

	@Test
	public void createService() {

		try {
			URL url = getClass().getClassLoader().getResource("rrpairs/doit");
			File rrPairsFolder = new File(url.toURI());
			VirtualService service = devTestClient.fromRRPairs("DoItSample", rrPairsFolder)
					.overHttp(9001, "/cgi-bin/GatewayJavaDoIt.cgi")
					.addRequestDataProtocol(new DataProtocolBuilder(DataProtocolType.DOIT.getType()).build())
					.addRespondDataProtocol(new DataProtocolBuilder(DataProtocolType.DOIT.getType()).build()).build();
			service.deploy();

			String result = clientDoIt.callDoItService();
			System.out.println(result);
			service.unDeploy();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void createCobol() {

		try {
			URL url = getClass().getClassLoader().getResource("rrpairs/cobol");
			File rrPairsFolder = new File(url.toURI());
			TransportProtocolBuilder transportProtocol = new TransportProtocolBuilder(
					TransportProtocolType.CTG.getType())
						.addParameter("sslClientAuthTypeUnparsed", "none")
						.addParameter("enablePingUnparsed", "false")
						.addParameter("needSeparateBindUnparsed", "false")
						.addParameter("useSslToClientUnparsed", "false")
						.addParameter("targetPortUnparsed", "1234")
						.addParameter("treatAllAsStatelessUnparsed", "false")
						.addParameter("hostHeaderPassThroughUnparsed", "false")
						.addParameter("hasSecurityUnparsed", "false")
						.addParameter("targetHostUnparsed", "localhost")
						.addParameter("useGatewayUnparsed", "true")
						.addParameter("allAreStatelessUnparsed", "true")
						.addParameter("doApplyStructureUnparsed", "false")
						.addParameter("useSslToServerUnparsed", "false")
						.addParameter("versionUnparsed", "7.2.0")
						.addParameter("chainedHttpProxyBypassUnparsed", "127.0.0.1")
						.addParameter("jvmTextUnparsed", "LISA CTG Server")
						.addParameter("chainedHttpProxyActiveUnparsed", "false")
						.addParameter("chainedHttpProxyActiveUnparsed", "false")
						.addParameter("listenPortUnparsed", "2006")
						.addParameter("desensitizeUnparsed", "false")
						.addParameter("chainedHttpsProxyActiveUnparsed", "false")
						.addParameter("duptxnsUnparsed", "false");

			DataProtocolBuilder dataHandler = new DataProtocolBuilder(DataProtocolType.COPY_BOOK_RRPAIRS.getType())
					.addParameter("cb2XmlColumnStartUnparsed", "6")
					.addParameter("validateFieldLengthsUnparsed", "false")
					.addParameter("requestSideUnparsed", "true")
					.addParameter("cb2XmlColumnEndUnparsed", "72")
					.addParameter("copybookCacheTTLUnparsed", "-1")
					.addParameter("encodingUnparsed", "UTF-8")
					.addParameter("fileDefinitionMapPathUnparsed", "{{LISA_RELATIVE_PROJ_ROOT}}/mapping.xml")
					.addParameter("copybookFileDefinitionFolderUnparsed", "{{LISA_RELATIVE_PROJ_ROOT}}")
					.addParameter("xmlElementsToRequestArgsUnparsed", "true");

			VirtualService service = devTestClient.fromRRPairs("DoItSample", rrPairsFolder)
					.over(transportProtocol.build()).addRequestDataProtocol(dataHandler.build())
					.addRespondDataProtocol(dataHandler.build()).build();
			service.deploy();

			String result = clientDoIt.callDoItService();
			System.out.println(result);
			service.unDeploy();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void createSoapService() throws IOException, URISyntaxException {
		int port = 9001;
		String path = "/lisa";

		/* Create Virtual Service */
		URL url = getClass().getClassLoader().getResource("rrpairs/soap");
		File rrPairsFolder = new File(url.toURI());
		VirtualService service = devTestClient.fromRRPairs("lisaBankTU", rrPairsFolder).overHttp(port, path)
				.addRequestDataProtocol(new DataProtocolBuilder(DataProtocolType.SOAP.getType()).build()).build();
		/* Deploy Virtual Service */
		service.deploy();

		/* Test */
		SoapClient soapclient = new SoapClient("localhost", String.valueOf(port));
		url = getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml");
		File requestFile = new File(getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml").toURI());
		String request = FileUtils.readFileToString(requestFile, "UTF-8");
		String response = soapclient.callService(path, request);

		/* Deploy Virtual Service */
		service.unDeploy();

	}

}
