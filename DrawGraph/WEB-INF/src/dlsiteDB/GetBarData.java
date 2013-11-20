package dlsiteDB;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import editData.BarData;
import editData.BarGraphData;

public class GetBarData {
	
	private static String CATEGORY_QUERY = "";
	/**
	 * 棒グラフデータ取得　属性、期間指定.
	 * @param type
	 * @param from
	 * @param to
	 * @return
	 */
	public static List<BarData> getBarData(int type, Calendar from, Calendar to){
		// 棒グラフデータ格納
		List<BarData> lst = new ArrayList<BarData>();
		
		try{
			Connection con = DBManager.getConnection();
			
			String sql = getQuery(type, from, to)+" ORDER BY(value) DESC";
			PreparedStatement smt = con.prepareStatement(sql);
			
			ResultSet result = smt.executeQuery();
			while(result.next()){
				int id = result.getInt("id");
				int count = result.getInt("count");
				long value = result.getLong("value");
				String name = result.getString("name");
				
				BarData bar = new BarData(id, count, value, name);
				lst.add(bar);
			}
			
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return lst;
	}
	
	/**
	 * 棒グラフ用のSELECT文生成.
	 * @param type 取得するデータの種類
	 * @return
	 */
	private static String getQuery(int type, Calendar from, Calendar to){
		StringBuilder sb = new StringBuilder();
		switch(type){
		case BarGraphData.CATEGORY:
			sb.append("select");
			sb.append(" fit.field_id as id ");
			sb.append(",fit.field_name as name");
			sb.append(",count(*) as count");
			sb.append(",SUM(wt.`dl` * wt.price) as value");
			sb.append(" from field_info_table as fit ");
			sb.append(" inner join field_table as ft ");
			sb.append(" on fit.field_id = ft.field_id ");
			sb.append(" inner join work_table as wt ");
			sb.append(" on ft.work_id = wt.work_id ");
			sb.append(" where fit.`type` = 1 ");
			if(from!=null){
				sb.append("and wt.day >= '"+getDay(from)+"' ");
			}
			if(to!=null){
				sb.append("and wt.day <= '"+getDay(to)+"' ");
			}
			sb.append("group by (fit.field_id) ");
			break;
		case BarGraphData.FORM:
			sb.append("select");
			sb.append(" fit.field_id as id ");
			sb.append(",fit.field_name as name");
			sb.append(",count(*) as count");
			sb.append(",SUM(wt.`dl` * wt.price) as value");
			sb.append(" from field_info_table as fit ");
			sb.append(" inner join field_table as ft ");
			sb.append(" on fit.field_id = ft.field_id ");
			sb.append(" inner join work_table as wt ");
			sb.append(" on ft.work_id = wt.work_id ");
			sb.append(" where fit.`type` = 2 ");
			if(from!=null){
				sb.append("and wt.day >= '"+getDay(from)+"' ");
			}
			if(to!=null){
				sb.append("and wt.day <= '"+getDay(to)+"' ");
			}
			sb.append("group by (fit.field_id) ");
			break;
		}
		
		return sb.toString();
	}
	
	
	private static String getDay(Calendar cal){
		StringBuilder sb = new StringBuilder();
		sb.append(cal.get(Calendar.YEAR));
		sb.append(String.format("%02d", cal.get(Calendar.MONTH)+1));
		sb.append(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
		return sb.toString();
	}
}
