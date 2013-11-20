package dlsite;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import com.google.gson.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetData extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		String action = request.getParameter("action");
		System.out.println(action);
		if("get".equalsIgnoreCase(action)){
			Calendar from = Calendar.getInstance();
			Calendar to = Calendar.getInstance();
			try{
				long from_time = Long.valueOf( request.getParameter("from") );
				long to_time = Long.valueOf( request.getParameter("to") );
				from.setTimeInMillis(from_time);
				to.setTimeInMillis(to_time);
			}catch(Exception e){
				from.add(Calendar.YEAR, -1);
				to.add(Calendar.YEAR, -1);
			}
			
			Work[] works = ConnectDB.getWorks(from, to);
			System.out.println(Arrays.toString(works));
			
			System.out.println("END");
			
			response.setContentType("text/xml; charset=utf-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().write("<?xml version='1.0' encoding='UTF-8'?><tweets><tweet>"+works.length+"</tweet></tweets>");
			
		}else if("categorys".equalsIgnoreCase(action)){
			String[] categorys = getCategorys();
			String json = createJson(categorys);
			response.setContentType("text/json; charset=utf-8");
			response.setHeader("Cache-control", "no-cache");
			response.getWriter().write(json);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
	}
	
	/**
	 * 全カテゴリー検索.
	 * @return
	 */
	private String[] getCategorys(){
		String sql = "select * from category_table";
		return ConnectDB.getStrings(sql, "category");
	}
	
	/**
	 * 文字列のJSON化.
	 * @param ls
	 * @return
	 */
	private String createJson(String[] ls){
		Gson json = new Gson();
		return json.toJson(ls);
	}
}
