package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ResultItemDAO;
import com.mobileserver.domain.ResultItem;

import org.json.JSONStringer;

public class ResultItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造结果指标业务层对象*/
	private ResultItemDAO resultItemDAO = new ResultItemDAO();

	/*默认构造函数*/
	public ResultItemServlet() {
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
			/*获取查询结果指标的参数信息*/

			/*调用业务逻辑层执行结果指标查询*/
			List<ResultItem> resultItemList = resultItemDAO.QueryResultItem();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<ResultItems>").append("\r\n");
			for (int i = 0; i < resultItemList.size(); i++) {
				sb.append("	<ResultItem>").append("\r\n")
				.append("		<resultItemId>")
				.append(resultItemList.get(i).getResultItemId())
				.append("</resultItemId>").append("\r\n")
				.append("		<resultItemText>")
				.append(resultItemList.get(i).getResultItemText())
				.append("</resultItemText>").append("\r\n")
				.append("	</ResultItem>").append("\r\n");
			}
			sb.append("</ResultItems>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(ResultItem resultItem: resultItemList) {
				  stringer.object();
			  stringer.key("resultItemId").value(resultItem.getResultItemId());
			  stringer.key("resultItemText").value(resultItem.getResultItemText());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加结果指标：获取结果指标参数，参数保存到新建的结果指标对象 */ 
			ResultItem resultItem = new ResultItem();
			int resultItemId = Integer.parseInt(request.getParameter("resultItemId"));
			resultItem.setResultItemId(resultItemId);
			String resultItemText = new String(request.getParameter("resultItemText").getBytes("iso-8859-1"), "UTF-8");
			resultItem.setResultItemText(resultItemText);

			/* 调用业务层执行添加操作 */
			String result = resultItemDAO.AddResultItem(resultItem);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除结果指标：获取结果指标的记录编号*/
			int resultItemId = Integer.parseInt(request.getParameter("resultItemId"));
			/*调用业务逻辑层执行删除操作*/
			String result = resultItemDAO.DeleteResultItem(resultItemId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新结果指标之前先根据resultItemId查询某个结果指标*/
			int resultItemId = Integer.parseInt(request.getParameter("resultItemId"));
			ResultItem resultItem = resultItemDAO.GetResultItem(resultItemId);

			// 客户端查询的结果指标对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("resultItemId").value(resultItem.getResultItemId());
			  stringer.key("resultItemText").value(resultItem.getResultItemText());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新结果指标：获取结果指标参数，参数保存到新建的结果指标对象 */ 
			ResultItem resultItem = new ResultItem();
			int resultItemId = Integer.parseInt(request.getParameter("resultItemId"));
			resultItem.setResultItemId(resultItemId);
			String resultItemText = new String(request.getParameter("resultItemText").getBytes("iso-8859-1"), "UTF-8");
			resultItem.setResultItemText(resultItemText);

			/* 调用业务层执行更新操作 */
			String result = resultItemDAO.UpdateResultItem(resultItem);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
