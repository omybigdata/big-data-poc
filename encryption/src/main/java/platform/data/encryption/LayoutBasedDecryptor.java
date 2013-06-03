package platform.data.encryption;


import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import platform.data.file.layout.*;

public class LayoutBasedDecryptor {
	
	private FileLayout layout;
	private Crypto encrypter; 
	
	public  LayoutBasedDecryptor(FileLayout layout, String encryptionScheme, String encryptionKey) 
			throws NoSuchProviderException, EncryptionException, InvalidKeyException, InvalidKeySpecException{
		this.layout = layout;
		this.encrypter = new StringEncryptor( encryptionScheme,	encryptionKey );		
		this.encrypter.initDecryptMode();
	}
	
	public String decryptValue(String encryptedRecord) throws EncryptionException{
		Field [] fields = layout.getFields();
		String [] values = parse(encryptedRecord);
		
		//loop through all the values and decrypt based on the flag in the layout 
		int pos=0;		
		for(Field f:fields){
			if (f.isUseField()){
				String encryptedValue = values[pos];
				values[pos] = this.encrypter.decrypt(encryptedValue);
			}
			pos++;
		}
		
		return unParse(values);
	}

	private String unParse(String[] values) {
		if(this.layout instanceof CsvFileLayout){
			return ((CsvFileLayout) layout).unParse(values);
		}else if(this.layout instanceof FixedFileLayout){
			return ((FixedFileLayout) layout).unParse(values);
		} else {
			throw new RuntimeException("Layout Not Supported yet.");
		}
	}		
	
	private String[] parse(String record){
		if(this.layout instanceof CsvFileLayout){
			return ((CsvFileLayout) layout).parse(record);
		}else if(this.layout instanceof FixedFileLayout){
			return ((FixedFileLayout) layout).parse(record);
		}else{
			throw new RuntimeException("Layout Not Supported yet.");
		}
	}

}
