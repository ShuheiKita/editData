package editData;

public class BarData {
	private long values;
	private String attribute_name;
	private int attribute_id;
	private int count;
	
	
	public BarData(int id, int count, long value, String name){
		this.attribute_id = id;
		this.count = count;
		this.values = value;
		this.attribute_name = name;
	}
	
	
	
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("■□"+this.attribute_id+"\r\n");
		sb.append("  "+this.attribute_name+"\r\n");
		sb.append("  ("+this.count+")\r\n");
		sb.append("  "+this.values+"\r\n");
		
		return sb.toString();
	}
	
	public String toJson(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(" \"id\" : "+this.attribute_id+"\r\n");
		sb.append(",\"name\" : \""+this.attribute_name+"\"\r\n");
		sb.append(",\"count\" : "+this.count+"\r\n");
		sb.append(",\"values\" : "+this.values+"\r\n");
		sb.append("}\r\n");
		return sb.toString();
	}
	
	public long getValues() {
		return values;
	}
	public void setValues(long values) {
		this.values = values;
	}
	public String getAttribute_name() {
		return attribute_name;
	}
	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}
	public int getAttribute_id() {
		return attribute_id;
	}
	public void setAttribute_id(int attribute_id) {
		this.attribute_id = attribute_id;
	}
	public int getCount(){
		return this.count;
	}
	public void setCount(int count){
		this.count = count;
	}
}
