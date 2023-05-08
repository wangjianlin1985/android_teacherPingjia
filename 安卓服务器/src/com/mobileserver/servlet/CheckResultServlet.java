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

	/*���쿼�˽��ҵ������*/
	private CheckResultDAO checkResultDAO = new CheckResultDAO();

	/*Ĭ�Ϲ��캯��*/
	public CheckResultServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ���˽���Ĳ�����Ϣ*/
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

			/*����ҵ���߼���ִ�п��˽����ѯ*/
			List<CheckResult> checkResultList = checkResultDAO.QueryCheckResult(studentObj,teacherObj,itemObj,resultObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӿ��˽������ȡ���˽���������������浽�½��Ŀ��˽������ */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = checkResultDAO.AddCheckResult(checkResult);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����˽������ȡ���˽���ļ�¼���*/
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = checkResultDAO.DeleteCheckResult(resultId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¿��˽��֮ǰ�ȸ���resultId��ѯĳ�����˽��*/
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			CheckResult checkResult = checkResultDAO.GetCheckResult(resultId);

			// �ͻ��˲�ѯ�Ŀ��˽�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¿��˽������ȡ���˽���������������浽�½��Ŀ��˽������ */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = checkResultDAO.UpdateCheckResult(checkResult);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
