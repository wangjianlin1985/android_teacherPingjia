package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.TimeSetDAO;
import com.mobileserver.domain.TimeSet;

import org.json.JSONStringer;

public class TimeSetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造评价时间设置业务层对象*/
	private TimeSetDAO timeSetDAO = new TimeSetDAO();

	/*默认构造函数*/
	public TimeSetServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询评价时间设置的参数信息*/

			/*调用业务逻辑层执行评价时间设置查询*/
			List<TimeSet> timeSetList = timeSetDAO.QueryTimeSet();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<TimeSets>").append("\r\n");
			for (int i = 0; i < timeSetList.size(); i++) {
				sb.append("	<TimeSet>").append("\r\n")
				.append("		<timeId>")
				.append(timeSetList.get(i).getTimeId())
				.append("</timeId>").append("\r\n")
				.append("		<startDate>")
				.append(timeSetList.get(i).getStartDate())
				.append("</startDate>").append("\r\n")
				.append("		<endDate>")
				.append(timeSetList.get(i).getEndDate())
				.append("</endDate>").append("\r\n")
				.append("	</TimeSet>").append("\r\n");
			}
			sb.append("</TimeSets>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(TimeSet timeSet: timeSetList) {
				  stringer.object();
			  stringer.key("timeId").value(timeSet.getTimeId());
			  stringer.key("startDate").value(timeSet.getStartDate());
			  stringer.key("endDate").value(timeSet.getEndDate());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加评价时间设置：获取评价时间设置参数，参数保存到新建的评价时间设置对象 */ 
			TimeSet timeSet = new TimeSet();
			int timeId = Integer.parseInt(request.getParameter("timeId"));
			timeSet.setTimeId(timeId);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			timeSet.setStartDate(startDate);
			Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate"));
			timeSet.setEndDate(endDate);

			/* 调用业务层执行添加操作 */
			String result = timeSetDAO.AddTimeSet(timeSet);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除评价时间设置：获取评价时间设置的记录编号*/
			int timeId = Integer.parseInt(request.getParameter("timeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = timeSetDAO.DeleteTimeSet(timeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新评价时间设置之前先根据timeId查询某个评价时间设置*/
			int timeId = Integer.parseInt(request.getParameter("timeId"));
			TimeSet timeSet = timeSetDAO.GetTimeSet(timeId);

			// 客户端查询的评价时间设置对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("timeId").value(timeSet.getTimeId());
			  stringer.key("startDate").value(timeSet.getStartDate());
			  stringer.key("endDate").value(timeSet.getEndDate());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新评价时间设置：获取评价时间设置参数，参数保存到新建的评价时间设置对象 */ 
			TimeSet timeSet = new TimeSet();
			int timeId = Integer.parseInt(request.getParameter("timeId"));
			timeSet.setTimeId(timeId);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			timeSet.setStartDate(startDate);
			Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate"));
			timeSet.setEndDate(endDate);

			/* 调用业务层执行更新操作 */
			String result = timeSetDAO.UpdateTimeSet(timeSet);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
