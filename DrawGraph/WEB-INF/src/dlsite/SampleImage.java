package dlsite;

public class SampleImage {
	private String main;
	private String thunb;
	private String[] samples;

	public SampleImage(){

	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getThunb() {
		return thunb;
	}

	public void setThunb(String thunb) {
		this.thunb = thunb;
	}

	public String[] getSamples() {
		return samples;
	}

	public void setSamples(String[] samples) {
		this.samples = samples;
	}


	public String toString(){
		String str = "";
		str += "\nmain: "+this.main;
		str += "\nthunb: "+this.thunb;
		if(this.samples!=null)
		for(String img: this.samples){
			str += "\nsample: "+img;
		}
		return str;
	}
	
	public String toStringSamples(){
		StringBuffer str = new StringBuffer();
		str.append("[");
		for(int i=0; i<this.samples.length; i++){
			if(i!=0){
				str.append(",");
			}
			str.append("\""+this.samples[i]+"\"");
		}
		str.append("]");
		return str.toString();
	}
}
