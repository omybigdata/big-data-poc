package platform.data.encryption;

/**
 * 
 *
 */
public class Encrypt 
{
    public static void main( String[] args )
    {
    	if (args.length < 2) {
    		uses();
    		System.exit(1);
    	}
    	
		String stringToEncrypt = args[0];
		int keyVersion = Integer.parseInt(args[1]);
		
		String encryptedString = StringEncryptor.encryptDES(stringToEncrypt, keyVersion);
		System.out.println(encryptedString);		
    }
    
    public static void uses(){
		System.out.println("Uses: \nplatform.data.encryption.Encrypt <StringToEncrypt> <keyVersion>");
	}

}
