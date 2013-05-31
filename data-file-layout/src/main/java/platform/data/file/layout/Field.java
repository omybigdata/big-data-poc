package platform.data.file.layout;

public class Field {
	protected String fieldName;
	protected int byteLength;
	protected boolean useField;

	public Field(String fieldName, int byteLength, boolean useField) {
		this.fieldName=fieldName;
		this.byteLength=byteLength;
		this.useField=useField;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getByteLength() {
		return byteLength;
	}

	public boolean isUseField() {
		return useField;
	}

	public void setUseField(boolean useField) {
		this.useField = useField;
	}

	public void setByteLength(int byteLength) {
		this.byteLength = byteLength;
	}

}