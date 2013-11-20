package dlsite;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ConnectDB {
	
	public static Work[] getWorks(Calendar from, Calendar to){
		List<Work> works = new ArrayList<Work>();
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return null;
			
			int year_from = from.get(Calendar.YEAR);
			int year_to = to.get(Calendar.YEAR);
			String from_table = "";
			boolean flag = false;
			while(year_from<=year_to){
				if(flag){
					from_table+=",";
				}else{
					flag = true;
				}
				from_table += "work_table_"+year_from+" ";
				year_from++;
			}
			
			String sql = "select * from "+from_table+" where ?<=day and day<=?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setDate(1, new Date(from.getTimeInMillis()));
			stm.setDate(2, new Date(to.getTimeInMillis()));
			ResultSet result = stm.executeQuery();
			while(result.next()){
				Work work = new Work();
				work.setId( result.getInt("work_id") );
				work.setName( result.getString("work_name"));
				work.setPrice( result.getInt("price"));
				work.setExp_long(result.getString("exp_l"));
				work.setExp_short(result.getString("exp_s"));
				work.setAdult(result.getBoolean("adult"));
				work.setDate(result.getDate("day"));
				work.setUrl(result.getString("work_url"));
				
				int circle_id = result.getInt("circle_id");
				int num = result.getInt("dl");
				Dl dl = new Dl();
				dl.setCal(Calendar.getInstance());
				dl.setDl(num);
				work.setDl(new Dl[]{dl});
				
				
				works.add(work);
			}
			
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return works.toArray(new Work[0]);
		
	}
	
	
	
	/**
	 * 文字列検索関数.
	 * @param sql SQL文.
	 * @param col カラム名.
	 * @return 検索文字列(String[]) エラー時:null.
	 */
	public static String[] getStrings(String sql, String col){
		List<String> list = new ArrayList<String>();
		try{
			Connection con = DBManager.getConnection();
			Statement smt = con.createStatement();
			ResultSet result = smt.executeQuery(sql);
			while(result.next()){
				list.add(result.getString(col));
			}
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}
		return list.toArray(new String[0]);
		
	}
}
