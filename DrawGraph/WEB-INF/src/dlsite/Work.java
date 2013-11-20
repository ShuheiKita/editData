package dlsite;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;

public class Work {
	private int id;
	private String name;
	private Circle circle;
	private Dl[] dl;
	private int price;
	private SampleImage images;

	private boolean adult;

	private String[] category;
	private String[] form;

	private String url;
	private String exp_long;
	private String exp_short;

	private Date date;


	public Work(){
		circle = new Circle();
		images = new SampleImage();
	}



	public String getExp_long() {
		return exp_long;
	}

	public void setExp_long(String exp_long) {
		this.exp_long = exp_long;
	}

	public String getExp_short() {
		return exp_short;
	}

	public void setExp_short(String exp_short) {
		this.exp_short = exp_short;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public Dl[] getDl() {
		return dl;
	}

	public void setDl(Dl[] dl) {
		this.dl = dl;
	}

	public SampleImage getImages() {
		return images;
	}

	public void setImages(SampleImage images) {
		this.images = images;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return this.url;
	}
	public void setDate(Date date){
		this.date = date;
	}
	public void setDate(Calendar cal){
		this.date = new Date( cal.getTimeInMillis() );
	}
	public Date getDate(){
		return date;
	}
	public String[] getCategory() {
		return category;
	}
	public void setCategory(String[] category) {
		this.category = category;
	}
	public String[] getForm() {
		return form;
	}
	public void setForm(String[] form) {
		this.form = form;
	}
	public boolean getAdult(){
		return this.adult;
	}
	public void setAdult(boolean adult){
		this.adult = adult;
	}
	public void setPrice(int price){
		this.price = price;
	}
	public int getPrice(){
		return this.price;
	}

	public String getDateYear(){
		return this.date.toString().split("-")[0];
	}


	public boolean isPerfect(){
		if(this.name==null) return false;
		if(this.dl==null) return false;
		if(this.circle==null)return false;
		if(this.circle.getName()==null) return false;
		if(this.exp_long==null) return false;
		if(this.exp_short==null) return false;

		return true;
	}

	public String toString(){
		String str = "";
		str += "\nid: "+this.id;
		str += "\nname: "+this.name;
		str += "\nprice: "+this.price;
		str += "\nURL: "+this.url;
		if(this.adult)
			str += "\n成人向け";
		else
			str += "\n一般向け";
		str += this.circle.toString();
		str += this.images.toString();
		str += this.dl[0].toString();
		str += "\nexp: "+this.exp_short;
		str += "\nexp: "+this.exp_long;
		str += "\nCategory: "+Arrays.toString(this.category);
		str += "\nForm: "+Arrays.toString(this.form);
		return str;
	}
	
	public String getJSON(){
		StringBuffer str = new StringBuffer();
		str.append("{");
		str.append("\"work_id\":"+this.id+",");
		str.append("\"work_name\":\""+this.name+"\",");
		str.append("\"work_price\":"+this.price+",");
		str.append("\"work_url\":\""+this.url+"\",");
		str.append("\"work_adult\":"+this.adult+",");
		str.append("\"work_circle\":\""+this.circle.toString()+"\",");
		str.append("\"work_main\":\""+this.images.getMain()+"\",");
		str.append("\"work_thunb\":\""+this.images.getThunb()+"\",");
		str.append("\"work_sample\":\""+this.images.toStringSamples()+"\",");
		str.append("\"work_dl\":[");
		for(int i=0; i<this.dl.length; i++){
			if(i!=0){
				str.append(",");
			}
			str.append(this.dl[i].toJSON());
		}
		str.append("],");
		str.append("\"work_exp_short\":\""+this.exp_short+"\",");
		str.append("\"work_exp_long\":\""+this.exp_long+"\",");
		str.append("\"work_category\":[");
		for(int i=0; i<this.category.length; i++){
			if(i!=0){
				str.append(",");
			}
			str.append("\""+this.category[i]+"\"");
		}
		str.append("],");
		str.append("\"work_form\":[");
		for(int i=0; i<this.form.length; i++){
			if(i!=0){
				str.append(",");
			}
			str.append("\""+this.form[i]+"\"");
		}
		str.append("]");
		str.append("}");
		return str.toString();
	}
}
