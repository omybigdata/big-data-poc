package platform.data.encryption;

import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;

/**
 * Common library API for Text Encryption. 
 * This library can be used with in any wrapper to execute 
 * encryption logic in different environment.   
 * @author Deba Saha
 */
public interface Crypto {

	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	public static final String DES_ENCRYPTION_SCHEME = "DES";
	public static final String DES_CBC_PKCS5Padding_SCHEME = "DES/CBC/PKCS5Padding";
	public static final String AES_CBC_PKCS5Padding_SCHEME = "AES/CBC/PKCS5Padding";
	
	public static final String DEFAULT_ENCRYPTION_KEY = "This is a fairly long phrase used to encrypt";
		
	/**
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * initializes encryption scheme, key etc. 
	 * Should be called once if same key and scheme to be utilize for all encryption
	 */
	public void initEncryptMode() 
			throws InvalidKeySpecException,	InvalidKeyException;

	/**
	 * @param unencryptedString
	 * @return encrypted string
	 * @throws EncryptionException
	 * encrypts a string based on pre-initialized schema and key by init()
	 */
	public String encrypt(String unencryptedString) 
			throws EncryptionException;
	
	/**
	 * @param inputStrLength - length of original unencrypted string
	 * @return estimated size of output encrypted String
	 */
	public int estimateEncryptedStringSize(int inputStrLength);
	
	/**
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * initializes decryption scheme, key etc. 
	 * Should be called once if same key and scheme to be utilize for all decryption
	 */
	public void initDecryptMode() 
			throws InvalidKeySpecException, InvalidKeyException;

	/**
	 * @param encryptedString
	 * @return decrypted string
	 * @throws EncryptionException
	 * decrypts an encrypted string
	 */
	public String decrypt(String encryptedString)
			throws EncryptionException;
	
	/**
	 * @param inputStrLength - length of encrypted string
	 * @return estimated size of the output decrypted String
	 */
	public int estimateDecryptedStringSize(int inputStrLength);

}