package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.QuestionResultDAO;
import com.mobileserver.domain.QuestionResult;

import org.json.JSONStringer;

public class QuestionResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造问卷结果业务层对象*/
	private QuestionResultDAO questionResultDAO = new QuestionResultDAO();

	/*默认构造函数*/
	public QuestionResultServlet() {
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
			/*获取查询问卷结果的参数信息*/
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");
			String teacherObj = "";
			if (request.getParameter("teacherObj") != null)
				teacherObj = request.getParameter("teacherObj");

			/*调用业务逻辑层执行问卷结果查询*/
			List<QuestionResult> questionResultList = questionResultDAO.QueryQuestionResult(studentObj,teacherObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<QuestionResults>").append("\r\n");
			for (int i = 0; i < questionResultList.size(); i++) {
				sb.append("	<QuestionResult>").append("\r\n")
				.append("		<resultId>")
				.append(questionResultList.get(i).getResultId())
				.append("</resultId>").append("\r\n")
				.append("		<studentObj>")
				.append(questionResultList.get(i).getStudentObj())
				.append("</studentObj>").append("\r\n")
				.append("		<teacherObj>")
				.append(questionResultList.get(i).getTeacherObj())
				.append("</teacherObj>").append("\r\n")
				.append("		<answer>")
				.append(questionResultList.get(i).getAnswer())
				.append("</answer>").append("\r\n")
				.append("	</QuestionResult>").append("\r\n");
			}
			sb.append("</QuestionResults>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(QuestionResult questionResult: questionResultList) {
				  stringer.object();
			  stringer.key("resultId").value(questionResult.getResultId());
			  stringer.key("studentObj").value(questionResult.getStudentObj());
			  stringer.key("teacherObj").value(questionResult.getTeacherObj());
			  stringer.key("answer").value(questionResult.getAnswer());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加问卷结果：获取问卷结果参数，参数保存到新建的问卷结果对象 */ 
			QuestionResult questionResult = new QuestionResult();
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			questionResult.setResultId(resultId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setStudentObj(studentObj);
			String teacherObj = new String(request.getParameter("teacherObj").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setTeacherObj(teacherObj);
			String answer = new String(request.getParameter("answer").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setAnswer(answer);

			/* 调用业务层执行添加操作 */
			String result = questionResultDAO.AddQuestionResult(questionResult);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除问卷结果：获取问卷结果的记录编号*/
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			/*调用业务逻辑层执行删除操作*/
			String result = questionResultDAO.DeleteQuestionResult(resultId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新问卷结果之前先根据resultId查询某个问卷结果*/
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			QuestionResult questionResult = questionResultDAO.GetQuestionResult(resultId);

			// 客户端查询的问卷结果对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("resultId").value(questionResult.getResultId());
			  stringer.key("studentObj").value(questionResult.getStudentObj());
			  stringer.key("teacherObj").value(questionResult.getTeacherObj());
			  stringer.key("answer").value(questionResult.getAnswer());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新问卷结果：获取问卷结果参数，参数保存到新建的问卷结果对象 */ 
			QuestionResult questionResult = new QuestionResult();
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			questionResult.setResultId(resultId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setStudentObj(studentObj);
			String teacherObj = new String(request.getParameter("teacherObj").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setTeacherObj(teacherObj);
			String answer = new String(request.getParameter("answer").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setAnswer(answer);

			/* 调用业务层执行更新操作 */
			String result = questionResultDAO.UpdateQuestionResult(questionResult);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
