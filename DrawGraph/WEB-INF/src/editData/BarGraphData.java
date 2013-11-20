package editData;

import java.util.List;

/**
 * 棒グラフ用データ格納クラス.
 * @author tukushimbo
 *
 */
public class BarGraphData {
	
	public static final int CIRCLE = 1;
	public static final int CATEGORY = 2;
	public static final int FORM = 3;
	public static final int WORK = 4;
	
	// 棒グラフデータ格納
	private List<BarData> datas;
	// 縦軸最大値
	private long MAX_VALUE;
	// 横軸の表示件数最大値
	private int MAX_VIEW;
	
	
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("----棒グラフ　データ----------\r\n");
		sb.append("MAX :: "+this.MAX_VALUE+"\r\n");
		sb.append("COUNT :: "+this.datas.size()+"\r\n");
		for(BarData bar : datas){
			sb.append(bar.toString());
		}
		
		return sb.toString();
	}
	
	public String toJson(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\r\n");
		sb.append(" \"max\" : "+this.MAX_VALUE+"\r\n");
		sb.append(",\"count\" : "+this.datas.size()+"\r\n");
		sb.append(",\"datas\" : [\r\n");
		for(int i=0; i<this.datas.size(); i++){
			sb.append(datas.get(i).toJson());
			if(i!=datas.size()-1){
				sb.append(",");
			}
		}
		sb.append("]\r\n}\r\n");
		return sb.toString();
	}
	
	public List<BarData> getDatas() {
		return datas;
	}
	public void setDatas(List<BarData> datas) {
		this.datas = datas;
	}
	public long getMAX_VALUE() {
		return MAX_VALUE;
	}
	public void setMAX_VALUE(long mAX_VALUE) {
		MAX_VALUE = mAX_VALUE;
	}
	public int getMAX_VIEW() {
		return MAX_VIEW;
	}
	public void setMAX_VIEW(int mAX_VIEW) {
		MAX_VIEW = mAX_VIEW;
	}
}
