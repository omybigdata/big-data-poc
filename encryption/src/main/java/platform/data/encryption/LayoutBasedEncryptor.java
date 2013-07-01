package platform.data.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import platform.data.file.layout.CsvFileLayout;
import platform.data.file.layout.Field;
import platform.data.file.layout.FileLayout;
import platform.data.file.layout.FixedFileLayout;

public class LayoutBasedEncryptor {
	
	private FileLayout layout;
	private StringEncryptor encrypter;
	
	private static final Log LOG = LogFactory.getLog(LayoutBasedEncryptor.class);

	public  LayoutBasedEncryptor(FileLayout layout, String encryptionScheme, String encryptionKey) 
			throws NoSuchProviderException, EncryptionException, InvalidKeyException, InvalidKeySpecException{
		this.layout = layout;
		this.encrypter = new StringEncryptor(encryptionScheme,	encryptionKey);		
		this.encrypter.initEncryptMode();
	}
	
	public String encryptValue(String record) throws EncryptionException{
		Field [] fields = layout.getFields();
		String [] values = parse(record);
		
		//loop through all the values and dcrypt based on the flag in the layout 
		int pos=0;		
		for(Field f:fields){
			if (f.isUseField()){
				String rawValue = values[pos];
				if(rawValue!=null && !rawValue.trim().equals("")){
					values[pos] = this.encrypter.encrypt(rawValue);
				}
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
		}{
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
