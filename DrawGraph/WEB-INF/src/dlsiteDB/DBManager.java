package dlsiteDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {
	public static Connection getConnection(){
		try{
			/*
			//プール
			InitialContext initCtx = new InitialContext();
			DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/tweetDB");
			Connection con = ds.getConnection();
*/
			com.mysql.jdbc.Driver dd = null;
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://", "", "");

			return con;
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
	}

	public static void main(String[] args) throws Exception{
		Connection con = getConnection();
		System.out.println("connect :"+con);
		con.close();
	}
}
