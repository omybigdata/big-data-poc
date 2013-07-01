package simple.util.rest;

import static org.junit.Assert.*;
import org.junit.Test;
import simple.util.rest.SimpleRESTClient;

class SimpleRESTClientTest {

	@Test
	public void testGetData() {
		SimpleRESTClient restClient = new SimpleRESTClient("http://localhost:8080", false)
		//restClient.authenticate("admin", "admin")
		def resourceKey = restClient.getData("/KeyManager/rest/resource/ar", "text/xml")
		
		println(resourceKey.toString())
		
		println "Resource Name: ${resourceKey.resourceName}"
		println "Encryption Key: ${resourceKey.encryptionKey}"
	}

}
