package com.ca.devtest.sv.devtools;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;

import com.ca.devtest.sv.devtools.services.VirtualService;

public class VirtualServiceEnvironment {

	private String name;
	private String registryHostName;
	private final String group;
	private final String userName;
	private final String password;
	
	protected static final String CREATE_VS_URI = "http://%s:1505/api/Dcm/VSEs/%s/actions/createService";
	protected static final String DELETE_VS_URI = "http://%s:1505/api/Dcm/VSEs/%s/%s";

	public VirtualServiceEnvironment(String registryHostName,String name, String userName, String password,String group) {
		super();
		if (name == null)
			throw new IllegalArgumentException(
					"Service Environment Name cannot be null");
		this.name = name;
		this.group = group;
		this.registryHostName=registryHostName;
		this.userName=userName;
		this.password=password;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}
	protected String getRegistryHostName() {
		return registryHostName;
	}
	
	/**
	 * @param service
	 * @throws IOException
	 */
	public void deployService( VirtualService service) throws IOException {
		
		HttpClient httpClient = HttpClients.createDefault();
		String urlPost=String.format(CREATE_VS_URI, getRegistryHostName(), getName());
		System.out.println("");
		HttpPost post = new HttpPost(urlPost);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		FileBody contentBody = new FileBody(service.getPackedVirtualService(), ContentType.APPLICATION_JSON);
		builder.addPart("file", contentBody);
		post.setEntity(builder.build());
		Base64 b64 = new Base64();
		post.setHeader("Authorization",
				String.format("Basic %s", new String(b64.encodeBase64(new String(userName+":"+password).getBytes()))));

	
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
				throw new HttpResponseException(response.getStatusLine().getStatusCode(),
						"VS creation did not complete normally");
			}
			service.getPackedVirtualService().deleteOnExit();
	}
	
	/**
	 * @param service
	 * @throws IOException
	 */
	public void unDeployService(VirtualService service) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpDelete delete = new HttpDelete(String.format(DELETE_VS_URI, getRegistryHostName(),
				getName(), service.getName()));
		Base64 b64 = new Base64();
		delete.setHeader("Authorization",
				String.format("Basic %s", new String(b64.encodeBase64(new String(userName+":"+password).getBytes()))));

	
			HttpResponse response = httpClient.execute(delete);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT) {
				throw new HttpResponseException(response.getStatusLine().getStatusCode(),
						"VS delete did not complete normally");
			}
		
		
	}

	
}
