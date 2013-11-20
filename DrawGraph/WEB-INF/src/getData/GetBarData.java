package getData;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import editData.BarGraphData;
import editData.GetBarGraphData;

public class GetBarData extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		//データ種類
		int type = BarGraphData.CATEGORY;
		String typeStr = request.getParameter("type");
		if(typeStr!=null){
			type = Integer.parseInt(typeStr);
		}
		
		//期間指定
		Calendar from = null;
		Calendar to =  null;
		String fromTime = request.getParameter("from");
		String toTime = request.getParameter("to");
		if(fromTime!=null){
			from = Calendar.getInstance();
			from.setTimeInMillis(Long.parseLong(fromTime));
		}
		if(toTime!=null){
			to = Calendar.getInstance();
			to.setTimeInMillis(Long.parseLong(toTime));
		}
		
		//データ取得
		String str = GetBarGraphData.getBarData(type, from, to);
		
		response.setContentType("text/json; charset=utf-8");
		response.setHeader("Cache-control", "no-cache");
		response.setHeader("Access-Control-Allow-Origin","*");
		response.getWriter().write(str);
	}
}
