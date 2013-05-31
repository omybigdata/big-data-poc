package platform.data.encryption;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Common library API for String Encryption. This library can be used
 * with in any wrapper to execute encryption logic in different environment.
 * 
 * @author Deba Saha
 */

public class StringEncryptor implements Crypto {
	private KeySpec keySpec;
	private SecretKeyFactory keyFactory;
	private Cipher cipher;

	private static final String UNICODE_FORMAT = "UTF8";

	/**
	 * @param encryptionScheme
	 * @throws EncryptionException
	 * @throws NoSuchProviderException
	 */
	public StringEncryptor(String encryptionScheme) throws EncryptionException,
			NoSuchProviderException {
		this(encryptionScheme, DEFAULT_ENCRYPTION_KEY);
	}

	/**
	 * @param encryptionScheme
	 * @param encryptionKey
	 * @throws EncryptionException
	 * @throws NoSuchProviderException
	 */
	public StringEncryptor(String encryptionScheme, String encryptionKey)
			throws EncryptionException, NoSuchProviderException {

		if (encryptionKey == null)
			throw new IllegalArgumentException("encryption key was null");

		if (encryptionKey.trim().length() < 24)
			throw new IllegalArgumentException(
					"encryption key was less than 24 characters");

		try {
			byte[] keyAsBytes = encryptionKey.getBytes(UNICODE_FORMAT);

			if (encryptionScheme.equals(DESEDE_ENCRYPTION_SCHEME)) {
				keySpec = new DESedeKeySpec(keyAsBytes);
			} else if (encryptionScheme.equals(DES_ENCRYPTION_SCHEME)) {
				keySpec = new DESKeySpec(keyAsBytes);
			} else {
				throw new IllegalArgumentException(
						"Encryption scheme not supported: " + encryptionScheme);
			}

			// keyFactory = SecretKeyFactory.getInstance( encryptionScheme,
			// "SunJCE");
			keyFactory = SecretKeyFactory.getInstance(encryptionScheme);
			cipher = Cipher.getInstance(encryptionScheme);

		} catch (InvalidKeyException e) {
			throw new EncryptionException(e);
		} catch (UnsupportedEncodingException e) {
			throw new EncryptionException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptionException(e);
		} catch (NoSuchPaddingException e) {
			throw new EncryptionException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see platform.data.encryption.Crypto#initEncryptMode()
	 */
	@Override
	public void initEncryptMode() throws InvalidKeySpecException,
			InvalidKeyException {
		SecretKey key = keyFactory.generateSecret(keySpec);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see platform.data.encryption.Crypto#initDecryptMod()
	 */
	@Override
	public void initDecryptMode() throws InvalidKeySpecException,
			InvalidKeyException {
		SecretKey key = keyFactory.generateSecret(keySpec);
		cipher.init(Cipher.DECRYPT_MODE, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see platform.data.encryption.Crypto#encrypt(java.lang.String)
	 */
	@Override
	public String encrypt(String unencryptedString) throws EncryptionException {
		if (unencryptedString == null || unencryptedString.trim().length() == 0) {
			throw new IllegalArgumentException(
					"unencrypted string was null or empty");
		}

		try {
			byte[] cleartext = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] ciphertext = cipher.doFinal(cleartext);
			return Base64.encodeBase64String(ciphertext);
		} catch (Exception e) {
			throw new EncryptionException(e);
		}
	}

	/**
	 * @param unencryptedString
	 * @param encryptionKey
	 * @return encrypted string
	 */
	public static String encryptDES(String unencryptedString,
			String encryptionKey) {
		String encryptionScheme = "DESede"; // "DES/CBC/PKCS5Padding";
		try {
			Crypto encrypter = new StringEncryptor(encryptionScheme,
					encryptionKey);
			encrypter.initEncryptMode();
			return encrypter.encrypt(unencryptedString);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * Not super secure convenience method to encrypt and decrypt
	 * @param encryptedString
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String encryptDES(String encryptedString, int keyVersion) {
		try {
			InputStream is = StringEncryptor.class.getClassLoader()
					.getSystemResourceAsStream("encryption.properties");
			Properties p = new Properties();
			p.load(is);
			String privateKey = p.getProperty("simple_private_key_"+keyVersion);
			return encryptDES(encryptedString, privateKey);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param encryptedString
	 * @param dencryptionKey
	 * @return decrypted string
	 */
	public static String decryptDES(String encryptedString, String decryptionKey) {
		String encryptionScheme = "DESede"; // "DES/CBC/PKCS5Padding";
		try {
			Crypto encrypter = new StringEncryptor(encryptionScheme,
					decryptionKey);
			encrypter.initDecryptMode();
			return encrypter.decrypt(encryptedString);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String decryptDES(String encryptedString, int keyVersion) {
		try {
			InputStream is = StringEncryptor.class.getResourceAsStream("/encryption.properties");
			Properties p = new Properties();
			p.load(is);
			String privateKey = p.getProperty("simple_private_key_"+keyVersion);
			return decryptDES(encryptedString, privateKey);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see platform.data.encryption.Crypto#decrypt(java.lang.String)
	 */
	@Override
	public String decrypt(String encryptedString) throws EncryptionException {

		if (encryptedString == null || encryptedString.trim().length() <= 0) {
			throw new IllegalArgumentException(
					"encrypted string was null or empty");
		}

		try {
			byte[] cleartext = Base64.decodeBase64(encryptedString);
			byte[] ciphertext = cipher.doFinal(cleartext);
			return bytes2String(ciphertext);
		} catch (Exception e) {
			throw new EncryptionException(e);
		}
	}

	/**
	 * @param bytes
	 * @return
	 */
	private static String bytes2String(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			stringBuffer.append((char) bytes[i]);
		}
		return stringBuffer.toString();
	}

	/* (non-Javadoc)
	 * @see platform.data.encryption.Crypto#getOutputSize()
	 */
	@Override
	public int estimateEncryptedStringSize(int inputStrLength) {
		int s = cipher.getBlockSize();		 
		int roundedInputStrLength = inputStrLength + s - (inputStrLength % s);
		return ( roundedInputStrLength * 4 ) / 3 + 8 ;
	}

	/* (non-Javadoc)
	 * @see platform.data.encryption.Crypto#estimateDecryptedStringSize(int)
	 */
	@Override
	public int estimateDecryptedStringSize(int inputStrLength) {
		int s = cipher.getBlockSize();
		int roundedInputStrLength = inputStrLength + s - (inputStrLength % s);
		return ( roundedInputStrLength * 3 ) / 4;
	}

}