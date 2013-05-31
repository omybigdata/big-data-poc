package simple.util.rest;

import java.util.HashMap;

import simple.util.rest.SimpleRESTClient;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        SimpleRESTClient restClient = new SimpleRESTClient("http://localhost:8080", false);
		@SuppressWarnings("rawtypes")
		HashMap service = (HashMap) restClient.getData("/rest/resource/ar", "text/xml");
		
		System.out.println(service);
    }
}
