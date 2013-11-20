package editData;
import java.util.Calendar;
import java.util.List;
import dlsiteDB.GetBarData;


public class GetBarGraphData {
	
	
	public static String getBarData(int type, Calendar from, Calendar to){
		
		BarGraphData barGraph = new BarGraphData();
		
		//グラフ情報セット
		List<BarData> bars = GetBarData.getBarData(type, from, to);
		barGraph.setDatas(bars);
		barGraph.setMAX_VALUE(getMax(bars));
		
		return barGraph.toJson();
	}
	
	private static long getMax(List<BarData> lst){
		long max = 0;
		for(BarData bar : lst){
			if(max < bar.getValues()){
				max = bar.getValues();
			}
		}
		return max;
	}
}
