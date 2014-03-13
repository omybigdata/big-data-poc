package platform.data.file.layout;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

public abstract class FileLayout {

	/**
	 * defines simple layout definition for any data file
	 */
	protected Properties property;
	protected Field[] fields;

	public FileLayout() {
		super();
	}
	
	/**
	 * @param layoutFile - location of the layout xml file 
	 * @throws InvalidPropertiesFormatException
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws InvalidFieldLayoutException
	 */
	public FileLayout(String layoutFile) 
			throws InvalidPropertiesFormatException, NumberFormatException, IOException, InvalidFieldLayoutException {			
			property = getProperty(layoutFile);
			fields = new Field[property.size()];
			int fieldWidth=0;
			boolean useField = true;				
			
			for(Object o: property.keySet()){
				String fieldName = (String)o;
				String posValueStr = property.getProperty(fieldName);
				String[] posValues = posValueStr.split(",");
				int index;
				
				if(posValues != null && posValues.length==3){
					index = Integer.parseInt(posValues[0]);
					fieldWidth=Integer.parseInt(posValues[1]);
					useField = "Y".equalsIgnoreCase(posValues[2]);
					fields[index] = new Field( fieldName, fieldWidth, useField);
				}else{
					throw new InvalidFieldLayoutException("Fields layout is not defined properly");
				}
			}
		}

	/**
	 * @return list of Fixed Length Fields defined by the layout
	 */
	public Field[] getFields() {
		return fields;
	}

	protected Properties getProperty(String fileName)
			throws InvalidPropertiesFormatException, IOException {   
			    Properties prop = new Properties();  
			    FileInputStream fis = new FileInputStream(fileName);  
			    prop.loadFromXML(fis);  
			    return prop;  
	}
	
	abstract public String[] parse(String record);
	
	abstract public String unParse(String [] recordValues);
	
	public Map<String,String> parsedMap(String record){
					
		Map<String, String> hm = new HashMap<String, String>();
		
		String [] fieldValues = parse(record);
		Field [] fieldNames = getFields();
		
		for(int i=0; i<fieldNames.length; i++){			
				hm.put(fieldNames[i].getFieldName(), fieldValues[i]);	
		}		
		return hm;
	}

}