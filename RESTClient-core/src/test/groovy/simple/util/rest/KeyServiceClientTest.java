/**
 * 
 */
package simple.util.rest;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author dsaha1
 *
 */
public class KeyServiceClientTest {

	/**
	 * Test method for {@link simple.util.rest.KeyServiceClient#getKey(java.lang.String)}.
	 */
	@Test
	public void testGetKey() {
		KeyServiceClient c = new KeyServiceClient("http://ushoflt319544:8080");
		ResourceKey rkey = c.getKey("pos_process_transaction");
		System.out.println("Encryption Key: " + rkey.getEncryptionKey() + "\nEncryption Scheme: " + rkey.getEncryptionScheme());		
	}
}
