package simple.util.rest;

import static org.junit.Assert.*;
import org.junit.Test;
import simple.util.rest.SimpleRESTClient;

class SimpleRESTClientTest {

	@Test
	public void testGetData() {
		SimpleRESTClient restClient = new SimpleRESTClient("http://localhost:8080", false)
		restClient.authenticate("admin", "admin")
		def data = restClient.getData("/KeyManager/RKey/list", null)
		
		println(data.toString())
		
		data.each(){ it ->
				println "resourceName = ${it.name}"
				String name = it.name
				assertTrue name != null && !name.trim().equals("")
				
				println "Description = ${it.description}"
				String description = it.description
				assertTrue description != null && !description.trim().equals("")
		}
	}

}
