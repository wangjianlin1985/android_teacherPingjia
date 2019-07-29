package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.CheckResultDAO;
import com.mobileserver.domain.CheckResult;

import org.json.JSONStringer;

public class CheckResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造考核结果业务层对象*/
	private CheckResultDAO checkResultDAO = new CheckResultDAO();

	/*默认构造函数*/
	public CheckResultServlet() {
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
			/*获取查询考核结果的参数信息*/
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");
			String teacherObj = "";
			if (request.getParameter("teacherObj") != null)
				teacherObj = request.getParameter("teacherObj");
			int itemObj = 0;
			if (request.getParameter("itemObj") != null)
				itemObj = Integer.parseInt(request.getParameter("itemObj"));
			int resultObj = 0;
			if (request.getParameter("resultObj") != null)
				resultObj = Integer.parseInt(request.getParameter("resultObj"));

			/*调用业务逻辑层执行考核结果查询*/
			List<CheckResult> checkResultList = checkResultDAO.QueryCheckResult(studentObj,teacherObj,itemObj,resultObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<CheckResults>").append("\r\n");
			for (int i = 0; i < checkResultList.size(); i++) {
				sb.append("	<CheckResult>").append("\r\n")
				.append("		<resultId>")
				.append(checkResultList.get(i).getResultId())
				.append("</resultId>").append("\r\n")
				.append("		<studentObj>")
				.append(checkResultList.get(i).getStudentObj())
				.append("</studentObj>").append("\r\n")
				.append("		<teacherObj>")
				.append(checkResultList.get(i).getTeacherObj())
				.append("</teacherObj>").append("\r\n")
				.append("		<itemObj>")
				.append(checkResultList.get(i).getItemObj())
				.append("</itemObj>").append("\r\n")
				.append("		<resultObj>")
				.append(checkResultList.get(i).getResultObj())
				.append("</resultObj>").append("\r\n")
				.append("	</CheckResult>").append("\r\n");
			}
			sb.append("</CheckResults>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(CheckResult checkResult: checkResultList) {
				  stringer.object();
			  stringer.key("resultId").value(checkResult.getResultId());
			  stringer.key("studentObj").value(checkResult.getStudentObj());
			  stringer.key("teacherObj").value(checkResult.getTeacherObj());
			  stringer.key("itemObj").value(checkResult.getItemObj());
			  stringer.key("resultObj").value(checkResult.getResultObj());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加考核结果：获取考核结果参数，参数保存到新建的考核结果对象 */ 
			CheckResult checkResult = new CheckResult();
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			checkResult.setResultId(resultId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			checkResult.setStudentObj(studentObj);
			String teacherObj = new String(request.getParameter("teacherObj").getBytes("iso-8859-1"), "UTF-8");
			checkResult.setTeacherObj(teacherObj);
			int itemObj = Integer.parseInt(request.getParameter("itemObj"));
			checkResult.setItemObj(itemObj);
			int resultObj = Integer.parseInt(request.getParameter("resultObj"));
			checkResult.setResultObj(resultObj);

			/* 调用业务层执行添加操作 */
			String result = checkResultDAO.AddCheckResult(checkResult);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除考核结果：获取考核结果的记录编号*/
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			/*调用业务逻辑层执行删除操作*/
			String result = checkResultDAO.DeleteCheckResult(resultId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新考核结果之前先根据resultId查询某个考核结果*/
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			CheckResult checkResult = checkResultDAO.GetCheckResult(resultId);

			// 客户端查询的考核结果对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("resultId").value(checkResult.getResultId());
			  stringer.key("studentObj").value(checkResult.getStudentObj());
			  stringer.key("teacherObj").value(checkResult.getTeacherObj());
			  stringer.key("itemObj").value(checkResult.getItemObj());
			  stringer.key("resultObj").value(checkResult.getResultObj());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新考核结果：获取考核结果参数，参数保存到新建的考核结果对象 */ 
			CheckResult checkResult = new CheckResult();
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			checkResult.setResultId(resultId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			checkResult.setStudentObj(studentObj);
			String teacherObj = new String(request.getParameter("teacherObj").getBytes("iso-8859-1"), "UTF-8");
			checkResult.setTeacherObj(teacherObj);
			int itemObj = Integer.parseInt(request.getParameter("itemObj"));
			checkResult.setItemObj(itemObj);
			int resultObj = Integer.parseInt(request.getParameter("resultObj"));
			checkResult.setResultObj(resultObj);

			/* 调用业务层执行更新操作 */
			String result = checkResultDAO.UpdateCheckResult(checkResult);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
