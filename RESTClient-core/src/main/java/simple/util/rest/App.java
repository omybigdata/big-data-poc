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
    	KeyServiceClient c = new KeyServiceClient("http://ushoflt319544:8080");
    	if(args!=null && args.length > 0){
    		ResourceKey rkey = c.getKey(args[0]);
    		System.out.println("Encryption Key: " + rkey.getEncryptionKey() + "\nEncryption Scheme: " + rkey.getEncryptionScheme());
    	}else{
    		System.out.println("pass resource name");
    	}
    }
}
