package platform.data.encryption;

/**
 * 
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String stringToEncrypt  = "test@@@@@@@@@@@@@@####################test@@@@@@@@@@@@@@#################################################################################################################################################################################################################################################################################################################################################################################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$test@@@@@@@@@@@@@@#################################################################################################################################################################################################################################################################################################################################################################################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$test@@@@@@@@@@@@@@#################################################################################################################################################################################################################################################################################################################################################################################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$test@@@@@@@@@@@@@@#################################################################################################################################################################################################################################################################################################################################################################################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$test@@@@@@@@@@@@@@#################################################################################################################################################################################################################################################################################################################################################################################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$test@@@@@@@@@@@@@@#################################################################################################################################################################################################################################################################################################################################################################################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$#############################################################################################################################################################################################################################################################################################################################################################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$test@@@@@@@@@@@@@@#################################################################################################################################################################################################################################################################################################################################################################################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@test@@@@@@@@@@@@@@#################################################################################################################################################################################################################################################################################################################################################################################################@@@@@@@@@@@test@@@@@@@@@@@@@@#################################################################################################################################################################################################################################################################################################################################################################################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$";
		String encryptionKey    = "123456789012345678901234567890ABBCDEFGHIJKLMNOP";
		String encryptionScheme = "DESede"; //"DES/CBC/PKCS5Padding";
		
		String encryptedString="";
		String decryptedString="";

		try {
			Crypto encrypter = new StringEncryptor( encryptionScheme, encryptionKey );
			
			encrypter.initEncryptMode();		
			
			/*System.out.println("input size 100 and OutputSize:" + encrypter.getOutputSize(100));
			System.out.println("input size 118 and OutputSize:" + encrypter.getOutputSize(118));
			System.out.println("input size 150 and OutputSize:" + encrypter.getOutputSize(150));
			System.out.println("input size 300 and OutputSize:" + encrypter.getOutputSize(300));*/
			
			System.out.println("input size: " + stringToEncrypt.length() + 
					" and OutputSize:" + encrypter.estimateEncryptedStringSize(stringToEncrypt.length()));
			
			encryptedString = encrypter.encrypt( stringToEncrypt );
			System.out.println("Encrypted String: " + encryptedString);
			System.out.println("Encrypted String length: " + encryptedString.length());
			
			int diff = encryptedString.length() - encrypter.estimateEncryptedStringSize(stringToEncrypt.length());			
			System.out.println("diff size: " + diff);
			
			encrypter.initDecryptMode();
			decryptedString = encrypter.decrypt( encryptedString );
			System.out.println("Dencrypted String: " + decryptedString);			
			
			/*System.out.println("input size" + stringToEncrypt.length() + 
					" and OutputSize:" + encrypter.getOutputSize(stringToEncrypt.length()));*/
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		} 
		
    }

}
