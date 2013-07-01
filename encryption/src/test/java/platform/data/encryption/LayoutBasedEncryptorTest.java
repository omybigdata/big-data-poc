/**
 * 
 */
package platform.data.encryption;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.InvalidPropertiesFormatException;

import org.junit.Test;

import platform.data.file.layout.CsvFileLayout;
import platform.data.file.layout.FixedFileLayout;
import platform.data.file.layout.InvalidFieldLayoutException;

/**
 * @author dsaha1
 *
 */
public class LayoutBasedEncryptorTest {
	FixedFileLayout ffl;
	CsvFileLayout cfl;
	String encryptionScheme;
	String encryptionKey;
	
	private void init() throws NumberFormatException, InvalidPropertiesFormatException, IOException, InvalidFieldLayoutException{
		ffl = new FixedFileLayout("src/test/resources/SampleFixedLayout.xml");
		cfl = new CsvFileLayout("src/test/resources/SampleDelimitedLayout.xml", ",");
		encryptionKey = "123456789012345678901234567890";
		encryptionScheme = Crypto.DESEDE_ENCRYPTION_SCHEME;
	}

	/**
	 * Test method for {@link platform.data.encryption.LayoutBasedEncryptor#encryptValue(java.lang.String)}.
	 * @throws EncryptionException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchProviderException 
	 * @throws InvalidKeyException 
	 * @throws InvalidFieldLayoutException 
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 * @throws NumberFormatException 
	 */
	@Test
	public void testFixedFileLayoutEncryptDecryption() throws InvalidKeyException, NoSuchProviderException, InvalidKeySpecException, EncryptionException, NumberFormatException, InvalidPropertiesFormatException, IOException, InvalidFieldLayoutException {
		System.out.println("Testing FixedFileLayout based encryption/decryption");
		System.out.println("=================================================");
		init();
		String record = "1000000001DEBA SAHA                2519 Honeysuckle ln                                                                                 Rolling Meadows                                                                                     IL60008     " ;
		LayoutBasedEncryptor e = new LayoutBasedEncryptor(this.ffl, this.encryptionScheme, this.encryptionKey);
		LayoutBasedDecryptor d = new LayoutBasedDecryptor(this.ffl, this.encryptionScheme, this.encryptionKey);
		String cryptRecord = e.encryptValue(record);
		System.out.println("Raw Record:\n " + record);
		System.out.println("Encrypted Record:\n "+cryptRecord);
		
		String recoverdRecord = d.decryptValue(cryptRecord);
		System.out.println("Recovered record:\n "+recoverdRecord);
		
		assertEquals(record, recoverdRecord);
	}
	
	/**
	 * Test method for {@link platform.data.encryption.LayoutBasedEncryptor#encryptValue(java.lang.String)}.
	 * @throws EncryptionException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchProviderException 
	 * @throws InvalidKeyException 
	 * @throws InvalidFieldLayoutException 
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 * @throws NumberFormatException 
	 */
	@Test
	public void testCsvFileLayoutEncryptDecryption() throws InvalidKeyException, NoSuchProviderException, InvalidKeySpecException, EncryptionException, NumberFormatException, InvalidPropertiesFormatException, IOException, InvalidFieldLayoutException {
		System.out.println("Testing CsvFileLayout based encryption/decryption");
		System.out.println("=================================================");
		init();
		String record = "100000001,DEBA SAHA,2519 Honeysuckle ln,Rolling Meadows,IL,60008" ;
		LayoutBasedEncryptor encryptor = new LayoutBasedEncryptor(this.cfl, this.encryptionScheme, this.encryptionKey);
		LayoutBasedDecryptor decryptor = new LayoutBasedDecryptor(this.cfl, this.encryptionScheme, this.encryptionKey);
		String cryptRecord = encryptor.encryptValue(record);
		System.out.println("Raw Record:\n " + record);
		System.out.println("Encrypted Record:\n "+cryptRecord);
		
		String recoverdRecord = decryptor.decryptValue(cryptRecord);
		System.out.println("Recovered record:\n "+recoverdRecord);
		
		assertEquals(record, recoverdRecord);
		
		record = "Bell|Franks|Ap #339-4744 Et Rd.|Kapiti|NI||10001|Proin.velit@maurisrhoncusid.ca|149752|0|1|4";
		cfl = new CsvFileLayout("src/test/resources/poc_data_layout.xml", "|");
		
		encryptor = new LayoutBasedEncryptor(this.cfl, this.encryptionScheme, this.encryptionKey);
		
		decryptor = new LayoutBasedDecryptor(this.cfl, this.encryptionScheme, this.encryptionKey);
		
		cryptRecord = encryptor.encryptValue(record);
		
		System.out.println("Raw Record:\n " + record);
		System.out.println("Encrypted Record:\n "+cryptRecord);
		
		recoverdRecord = decryptor.decryptValue(cryptRecord);
		System.out.println("Recovered record:\n "+recoverdRecord);
		
				
	}
	
	

}
