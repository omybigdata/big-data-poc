package platform.data.file.layout;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;


public class FixedLengthByteRecordPaser {
	private FixedFileLayout fileLayout;
	private String charEncoding = null;  //"Cp037"
	//private BinaryCodec binCodec = new BinaryCodec();
	
	/**
	 * @param layoutFile
	 * @throws InvalidPropertiesFormatException
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws InvalidFieldLayoutException
	 */
	public FixedLengthByteRecordPaser(String layoutFile) 
		throws InvalidPropertiesFormatException, NumberFormatException, 
				IOException, InvalidFieldLayoutException{
		fileLayout = new FixedFileLayout(layoutFile);
	}
	
	/**
	 * @param layoutFile
	 * @throws InvalidPropertiesFormatException
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws InvalidFieldLayoutException
	 */
	public FixedLengthByteRecordPaser(String layoutFile, String charEncoding) 
		throws InvalidPropertiesFormatException, NumberFormatException, 
				IOException, InvalidFieldLayoutException{
		this(layoutFile);
		this.charEncoding = charEncoding;
	}

	/**
	 * @param buf - byte array of entire record
	 * @return Map<String,String> of <key:fieldName, value:fieldValue>
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String, String> getParseRecordMap(byte[] buf) throws UnsupportedEncodingException{
		Map<String, String> hm = new HashMap<String, String>();
		for(Field field: fileLayout.getFields()){
			FixedLengthField f;
			if(field instanceof FixedLengthField) {
				f = (FixedLengthField) field;			
				if(!f.isUseField()) continue;			
				
				String value = (
					charEncoding != null ? 	
							new String(buf, f.getStartBytePos(), f.getByteLength(), charEncoding) 
							: new String(buf, f.getStartBytePos(), f.getByteLength()) 
					);	
				
				hm.put(field.getFieldName(), value);
			}else{
				throw new RuntimeException("field must be of type: FixedLengthField");
			}
		}
		return hm;
	}

	public FixedFileLayout getFileLayout() {
		return fileLayout;
	}
	
}



