package platform.data.file.layout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

/**
 * @author dsaha1
 *
 */
public class FixedFileLayout extends FileLayout {
	private int recordByteLength;
	
	public FixedFileLayout(String layoutFile) 
		throws InvalidPropertiesFormatException, NumberFormatException, IOException, InvalidFieldLayoutException {		
		property = getProperty(layoutFile);		
		fields = new Field[property.size()];		
		List<FixedLengthField> sortedList = new ArrayList<FixedLengthField>(property.size());
		recordByteLength=0;
		
		int fieldStartPos=0;
		int fieldWidth=0;		
		boolean useField = true;
				
		for(Object o: property.keySet()){
			String fieldName = (String)o;
			String posValueStr = property.getProperty(fieldName);
			String[] posValues = posValueStr.split(",");
			
			if(posValues != null && posValues.length==3){
				fieldStartPos=Integer.parseInt(posValues[0]);
				fieldWidth=Integer.parseInt(posValues[1]);
				useField = "Y".equalsIgnoreCase(posValues[2]);
				recordByteLength+=fieldWidth;
				sortedList.add(new FixedLengthField(fieldName, fieldStartPos, fieldWidth, useField) );
			}else{
				throw new InvalidFieldLayoutException("Fields layout is not defined properly");
			}
		}	
		//sort the field list to make sure it is in the order as it appears in the data file				
		Collections.sort(sortedList, 
				new Comparator<FixedLengthField>(){
					public int compare(FixedLengthField f1, FixedLengthField f2){
						return f1.getStartBytePos() - f2.getStartBytePos();
					}				
				}
		);
		
		int index = 0;
		for(FixedLengthField f:sortedList ){
			this.fields[index++] = f;
		}
		
	}
	
	public String[] parse(String record){
		String [] recordValues = new String [fields.length];
		int index = 0;
		for(Field field: fields){
			FixedLengthField f;
			if(field instanceof FixedLengthField) {
				f = (FixedLengthField) field;			
				String value =  record.substring(f.getStartBytePos(), f.getStartBytePos()+f.getByteLength());
				recordValues[index++] = value.trim();
			}else{
				throw new RuntimeException("field must be of type: FixedLengthField");
			}
		}
		return recordValues;
	}
	
	public String unParse(String [] recordValues){
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for(Field field: fields){
			//String.format("%-10s", "foo") gives "foo       "
			String value = String.format("%-" + field.getByteLength() + "s",  recordValues[index++]);
			sb.append(value);
		}		
		return sb.toString();		
	}
	
	/**
	 * @return byte length of fixed width record
	 */
	public int getRecordByteLength() {
		return recordByteLength;
	}  
}
