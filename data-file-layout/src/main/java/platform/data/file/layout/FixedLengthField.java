package platform.data.file.layout;

public class FixedLengthField extends Field{
	private int startBytePos;
	public FixedLengthField(String fieldName, int startPos, int length, boolean useField){
		super(fieldName, length, useField);
		this.startBytePos=startPos;
	}

	public int getStartBytePos() {
		return startBytePos;
	}

	public void setStartBytePos(int startBytePos) {
		this.startBytePos = startBytePos;
	}
	
}
