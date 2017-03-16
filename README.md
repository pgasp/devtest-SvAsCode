## CA SV As Code

This project provides simple **Java annotations** that can be used  in your Junit Test to deploy Virtual Services before starting your test. The scope of annotations are test methods.
This java annotation helps to embed your Virtual Services in your source code. This approach makes your application ready for continous integration testing by removing system and data constraints. **Your tests become more reliable, repeatable and automated**.
With this approach, using virtual services from your Continuous intergration plateform becomes native. 

## Projects description
- **devtest-unit-test-java** : *Source code of the java api wrapped on **CA Devtest Rest API** to build and deploy virtual services*
- **devtest-unit-test-java-annotation** : *Source code of java annotations and Junit Rules used to parse our annotations*
- **lisabank-demo** : *Demo project using with junit test using annotation to deploy virtual services*

## Installation

## Code Example

	
	@DevTestVirtualServer(registryHost="localhost" , deployServiceToVse = "VSE")

	public class UserServiceTest {
		static final Log logger=LogFactory.getLog(UserServiceTest.class);
		
		@Rule
		public VirtualServicesRule rules = new VirtualServicesRule();
	
		@DevTestVirtualService(serviceName = "UserServiceTest-EJB3UserControlBean", 
			port = 9080, basePath = "/itkoExamples/EJB3UserControlBean",
			rrpairsFolder = "UserServiceTest/getListUser/EJB3UserControlBean", 
			requestDataProtocol = {@Protocol(ProtocolType.DPH_SOAP) })
		@Test
		public void getListUser() {
		
		User[] users = bankServices.getListUser();
		// Then
		printUsers(users);
		assertNotNull(users);
		assertEquals(9, users.length);
		
		User user=getUser("Admin", users);
		assertNotNull(user);
		
		assertEquals("Admin", user.getLname());

	}
	

## Motivation
These java annotations will use CA DevTest Rest API to build and deploy your virtual services from request/response pairs. 
TODO

## Installation

TODO


## Contributors

- Pascal Gasp *Sr Architect Devops @ CA Technologies*
- Vincent Mazot *Sr Consultant Devops @ CA Technologies*
- Olivier Laplace  *Sr Presales Devops @ CA Technologies*

## License

TODO
