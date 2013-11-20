package dlsite;

import java.sql.Date;
import java.util.Calendar;

public class Dl {
	private Date date;
	private int dl;

	public Dl(){

	}

	public Date getCal() {
		return date;
	}

	public void setCal(Calendar cal) {
		this.date = new Date( cal.getTimeInMillis() );
	}
	public void setDate(Date day){
		this.date = day;
	}

	public int getDl() {
		return dl;
	}

	public void setDl(int dl) {
		this.dl = dl;
	}


	public String toString(){
		String str = "";
		str += "\nDate: "+date;
		str += "\nDL; "+dl;
		return str;
	}
	
	public String toJSON(){
		return "["+this.date.getTime()+","+this.dl+"]";
	}
}
