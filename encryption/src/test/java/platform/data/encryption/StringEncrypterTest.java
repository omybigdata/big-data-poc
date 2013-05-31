package platform.data.encryption;

import platform.data.encryption.Crypto;
import platform.data.encryption.StringEncryptor;
import junit.framework.TestCase;

public class StringEncrypterTest extends TestCase
{

	public void testEncryptionKeyIsDefined()
	{
		assertEquals(	Crypto.DEFAULT_ENCRYPTION_KEY,
						"This is a fairly long phrase used to encrypt" );
	}

	public void testThrowsErrorOnInvalidKeySpec() 
			throws Exception {
		String encryptionScheme = "asdf";
		String encryptionKey = "123456789012345678901234567890";

		try	{
			@SuppressWarnings("unused")
			Crypto encrypter = new StringEncryptor( encryptionScheme,	encryptionKey );			
		} catch (IllegalArgumentException e) {
			assertEquals( "Encryption scheme not supported: asdf", e.getMessage() );
		}
	}

	public void testEncryptsUsingDesEde() 
			throws Exception {
		String stringToEncrypt = "test";
		String encryptionKey = "123456789012345678901234567890";
		String encryptionScheme = Crypto.DESEDE_ENCRYPTION_SCHEME;

		Crypto encrypter = new StringEncryptor( encryptionScheme, encryptionKey );
		encrypter.initEncryptMode();
		String encryptedString = encrypter.encrypt( stringToEncrypt );

		assertEquals( "Ni2Bih3nCUU=", encryptedString );
	}

	public void testEncryptsUsingDes() throws Exception	{
		String stringToEncrypt = "test";
		String encryptionKey = "123456789012345678901234567890";
		String encryptionScheme = Crypto.DES_ENCRYPTION_SCHEME;

		Crypto encrypter = new StringEncryptor( encryptionScheme, encryptionKey );
		encrypter.initEncryptMode();
		String encryptedString = encrypter.encrypt( stringToEncrypt );

		assertEquals( "oEtoaxGK9ns=", encryptedString );
	}

	public void testEncryptionKeyCanContainLetters() 
			throws Exception {
		String string = "test";
		String encryptionKey = "ASDF asdf 1234 8983 jklasdf J2Jaf8";
		String encryptionScheme = Crypto.DESEDE_ENCRYPTION_SCHEME;

		Crypto encrypter = new StringEncryptor( encryptionScheme, encryptionKey );
		encrypter.initEncryptMode();
		String encryptedString = encrypter.encrypt( string );
		assertEquals( "Q+UyPrxdge0=", encryptedString );
	}

	public void testDecryptsUsingDesEde() throws Exception {
		String string = "Ni2Bih3nCUU=";
		String encryptionKey = "123456789012345678901234567890";
		String encryptionScheme = Crypto.DESEDE_ENCRYPTION_SCHEME;

		Crypto encrypter = new StringEncryptor( encryptionScheme, encryptionKey );
		encrypter.initDecryptMode();
		String decryptedString = encrypter.decrypt( string );

		assertEquals( "test", decryptedString );
	}

	public void testDecryptsUsingDes() throws Exception	{
		String string = "oEtoaxGK9ns=";
		String encryptionKey = "123456789012345678901234567890";
		String encryptionScheme = Crypto.DES_ENCRYPTION_SCHEME;

		Crypto encrypter = new StringEncryptor( encryptionScheme, encryptionKey );
		encrypter.initDecryptMode();
		String decryptedString = encrypter.decrypt( string );

		assertEquals( "test", decryptedString );
	}

	public void testCantInstantiateWithNullEncryptionKey() throws Exception
	{
		try
		{
			String encryptionScheme = Crypto.DESEDE_ENCRYPTION_SCHEME;
			String encryptionKey = null;

			@SuppressWarnings("unused")
			Crypto encrypter = new StringEncryptor( encryptionScheme,	encryptionKey );
		}
		catch (IllegalArgumentException e)
		{
			assertEquals( "encryption key was null", e.getMessage() );
		}

	}

	public void testCantInstantiateWithEmptyEncryptionKey() throws Exception
	{
		try
		{
			String encryptionScheme = Crypto.DESEDE_ENCRYPTION_SCHEME;
			String encryptionKey = "";

			@SuppressWarnings("unused")
			Crypto encrypter = new StringEncryptor( encryptionScheme,
					encryptionKey );
		}
		catch (IllegalArgumentException e)
		{
			assertEquals( "encryption key was less than 24 characters", e.getMessage() );
		}

	}

	public void testCantDecryptWithNullString() throws Exception
	{
		try
		{
			String encryptionScheme = Crypto.DESEDE_ENCRYPTION_SCHEME;
			String encryptionKey = "123456789012345678901234";

			Crypto encrypter = new StringEncryptor( encryptionScheme,
					encryptionKey );
			encrypter.decrypt( null );
		}
		catch (IllegalArgumentException e)
		{
			assertEquals( "encrypted string was null or empty", e.getMessage() );
		}

	}

	public void testCantDecryptWithEmptyString() throws Exception
	{
		try
		{
			String encryptionScheme = Crypto.DESEDE_ENCRYPTION_SCHEME;
			String encryptionKey = "123456789012345678901234";

			Crypto encrypter = new StringEncryptor( encryptionScheme,
					encryptionKey );
			encrypter.decrypt( "" );
		}
		catch (IllegalArgumentException e)
		{
			assertEquals( "encrypted string was null or empty", e.getMessage() );
		}

	}

	public void testCantEncryptWithNullString() throws Exception
	{
		try
		{
			String encryptionScheme = Crypto.DESEDE_ENCRYPTION_SCHEME;
			String encryptionKey = "123456789012345678901234";

			Crypto encrypter = new StringEncryptor( encryptionScheme,
					encryptionKey );
			encrypter.encrypt( null );
		}
		catch (IllegalArgumentException e)
		{
			assertEquals( "unencrypted string was null or empty", e.getMessage() );
		}

	}

	public void testCantEncryptWithEmptyString() throws Exception
	{
		try
		{
			String encryptionScheme = Crypto.DESEDE_ENCRYPTION_SCHEME;
			String encryptionKey = "123456789012345678901234";

			Crypto encrypter = new StringEncryptor( encryptionScheme,
					encryptionKey );
			encrypter.encrypt( "" );
		}
		catch (IllegalArgumentException e)
		{
			assertEquals( "unencrypted string was null or empty", e.getMessage() );
		}

	}
	
	public void testEncryptDes() throws Exception {
		assert  StringEncryptor.encryptDES("test", "123456789012345678901234567890").equals("Ni2Bih3nCUU=");
	}
	
	public void testDencryptDes() throws Exception {
		assert  StringEncryptor.decryptDES("Ni2Bih3nCUU=", "123456789012345678901234567890").equals("test");
	}
}