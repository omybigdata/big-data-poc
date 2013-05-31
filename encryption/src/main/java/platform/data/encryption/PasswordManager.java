package platform.data.encryption;

public class PasswordManager {
	
	public static void main(String[] args) throws Exception
	{
		try{
			String password = args[0];
			int keyVersion = Integer.parseInt(args[1]);
			String encryptedPassword = StringEncryptor.decryptDES(password, keyVersion);
			System.out.println(encryptedPassword);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("USAGE: java platform.data.encryption.PasswordManager <clear_text_password> <key_version>");
		}
	}	
}
