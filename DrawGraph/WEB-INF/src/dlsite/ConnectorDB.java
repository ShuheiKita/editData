package dlsite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConnectorDB {
	/*
	 * create table  circle_table (circle_id int auto_increment not null, circle_name varchar(128), circle_site varchar(256), primary key(circle_id));
	 */

	private static final int IMG_MAIN  = 1;
	private static final int IMG_THUNB = 2;
	private static final int IMG_SAMPLE = 3;
	private static final int CATEGORY = 4;
	private static final int FORM = 5;

	/**
	 * サークルが登録済みか調べる.
	 * @param circle
	 * @return 登録済みの場合ID　　ない場合-1
	 */
	public static int isCircleId(Work work){
		int id = -1;
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return -1;
			String sql = "select circle_id from circle_table where circle_name=?";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setString(1, work.getCircle().getName());
			ResultSet result = smt.executeQuery();
			if(result.next())
				id = result.getInt("circle_id");
			else{	//サークルが登録されていない場合
				if( insertCircle(work) ){
					id = isCircleId(work);
					work.getCircle().setId(id);
					//dl_table_(circle_id)　作成
					createDlTable(work);
				}
			}
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}
	public static boolean updateCircle(Work work){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "update circle_table set circle_site=? where circle_id=?";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setString(1, work.getCircle().getSite_url());
			smt.setInt(2, work.getCircle().getId());
			int row = smt.executeUpdate();
			con.close();
			if(row==0) return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 作品が登録済みか調べる.
	 * @param work
	 * @return 登録済みの場合ID　　ない場合-1
	 */
	public static int isWorkId(Work work){
		int id = -1;
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return -1;
			String sql = "select work_id from work_table_"+work.getDateYear()+" where circle_id=? and work_name=?";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setInt(1, work.getCircle().getId());
			smt.setString(2, work.getName());
			ResultSet result = smt.executeQuery();
			if(result.next())
				id = result.getInt("work_id");
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}


	public static boolean isWorkTable(String year){
		boolean flag = false;
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "select count(*) from work_table_"+year+"";
			Statement smt = con.createStatement();
			smt.executeQuery(sql);
			con.close();
		}catch(Exception e){
			//e.printStackTrace();
			//テーブルの作成
			return createWorkTable(year);
		}
		return true;
	}

	public static boolean isDlTable(Work work){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "select count(*) from dl_table_"+work.getCircle().getId()+"";
			Statement smt = con.createStatement();
			ResultSet result = smt.executeQuery(sql);
			if(!result.next()){
				//テーブルの作成
				con.close();
				return createDlTable(work);
			}
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	public static Dl[] getDl(Work work){
		List<Dl> list = new ArrayList<Dl>();
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return null;
			String sql = "select *from dl_table_"+work.getCircle().getId()+" where work_id=? order by day desc";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setInt(1, work.getId());
			ResultSet result = smt.executeQuery();
			while(result.next()){
				Dl dl = new Dl();
				dl.setDate(result.getDate("day"));
				dl.setDl(result.getInt("dl"));
				list.add(dl);
			}
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(list.size()==0) return null;
		return list.toArray(new Dl[0]);
	}

	private static boolean createDlTable(Work work){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "create table dl_table_"+work.getCircle().getId()+" (" +
					"work_id int  not null, " +
					"dl int, " +
					"day date" +
					")";
			Statement smt = con.createStatement();
			smt.executeUpdate(sql);
			con.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean insertDl(Work work, Calendar cal){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "insert into dl_table_"+work.getCircle().getId()+" (work_id, dl, day) values (?,?,?)";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setInt(1, work.getId());
			smt.setInt(2, work.getDl()[0].getDl());
			smt.setDate(3, new Date(cal.getTimeInMillis()));
			int row = smt.executeUpdate();
			con.close();
			if(row==0) return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean updateDl(Work work, Calendar cal){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "update dl_table_"+work.getCircle().getId()+" set dl=? where work_id="+work.getId()+" and day="+new Date(cal.getTimeInMillis())+" ";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setInt(1, work.getDl()[0].getDl());
			int row = smt.executeUpdate();
			con.close();
			if(row==0) return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean updateDl_work(Work work){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "update work_table_"+work.getDateYear()+" set dl=? where work_id="+work.getId()+" ";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setInt(1, work.getDl()[0].getDl());
			int row = smt.executeUpdate();
			con.close();
			if(row==0) return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean isDlQuery(Work work, Calendar cal){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "select * from dl_table_"+work.getCircle().getId()+" where work_id="+work.getId()+" and day='"+new Date(cal.getTimeInMillis())+"'";
			Statement smt = con.createStatement();
			ResultSet result = smt.executeQuery(sql);
			if(result.next()){
				con.close();
				return true;
			}
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}


	public static boolean insertCircle(Work work){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "insert into circle_table (circle_name, circle_site) values (?, ?)";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setString(1, work.getCircle().getName());
			smt.setString(2, work.getCircle().getSite_url());
			int row = smt.executeUpdate();
			con.close();
			if(row==0) return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}


	private static boolean createWorkTable(String year){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "create table work_table_"+year+" (" +
					"work_id int auto_increment not null," +
					"work_name varchar(128)," +
					"circle_id int," +
					"dl int," +
					"price int, " +
					"exp_s varchar(256)," +
					"exp_l varchar(8192)," +
					"work_url varchar(256)," +
					"adult boolean," +
					"day date," +
					"primary key(work_id) " +
					")";
			Statement smt = con.createStatement();
			smt.executeUpdate(sql);
			con.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean insertWork(Work work){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "insert into work_table_"+work.getDateYear()+" (work_name, circle_id, dl, price, exp_s, exp_l, work_url, adult, day) values (?,?,?,?,?,?,?,?,?)";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setString(1, work.getName());
			smt.setInt(2, work.getCircle().getId());
			smt.setInt(3, work.getDl()[0].getDl());
			smt.setInt(4, work.getPrice());
			smt.setString(5, work.getExp_short());
			smt.setString(6, work.getExp_long());
			smt.setString(7, work.getUrl());
			smt.setBoolean(8, work.getAdult());
			smt.setDate(9, work.getDate());
			int row = smt.executeUpdate();
			con.close();
			if(row==0) return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}


	public static boolean isFieldTable(Work work){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "select * from field_table_"+work.getCircle().getId()+"";
			Statement smt = con.createStatement();
			ResultSet result = smt.executeQuery(sql);
			if(result.next()){
				con.close();
				return true;
			}
			con.close();
		}catch(Exception e){
			return createFieldTable(work);
		}
		return false;
	}
	public static boolean createFieldTable(Work work){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "create table field_table_"+work.getCircle().getId()+" (" +
					"work_id int not null," +
					"type int, " +
					"value varchar(256)" +
					")";
			Statement smt = con.createStatement();
			smt.executeUpdate(sql);
			con.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;

	}
	public static boolean insertFields(Work work){
		if(!insertField(IMG_MAIN, work.getImages().getMain(), work)) return false;
		if(!insertField(IMG_THUNB, work.getImages().getThunb(), work)) return false;
		String[] list = work.getImages().getSamples();
		if(list != null)
		for(String str : list){
			if(!insertField(IMG_SAMPLE, str, work)) return false;
		}
		list = work.getCategory();
		if(list != null)
		for(String str : list){
			if(!insertField(CATEGORY, str, work)) return false;
		}
		list = work.getForm();
		if(list != null)
		for(String str : list){
			if(!insertField(FORM, str, work)) return false;
		}
		return true;
	}
	private static boolean insertField(int type, String value, Work work){
		try{
			Connection con = DBManager.getConnection();
			if(con==null) return false;
			String sql = "insert into field_table_"+work.getCircle().getId()+" (work_id,type,value) values (?,?,?)";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setInt(1, work.getId());
			smt.setInt(2, type);
			smt.setString(3, value);
			int row = smt.executeUpdate();
			con.close();
			if(row==0) return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
