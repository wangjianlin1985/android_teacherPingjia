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

	/*�����ʾ���ҵ������*/
	private QuestionResultDAO questionResultDAO = new QuestionResultDAO();

	/*Ĭ�Ϲ��캯��*/
	public QuestionResultServlet() {
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
			/*��ȡ��ѯ�ʾ����Ĳ�����Ϣ*/
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");
			String teacherObj = "";
			if (request.getParameter("teacherObj") != null)
				teacherObj = request.getParameter("teacherObj");

			/*����ҵ���߼���ִ���ʾ�����ѯ*/
			List<QuestionResult> questionResultList = questionResultDAO.QueryQuestionResult(studentObj,teacherObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ����ʾ�������ȡ�ʾ����������������浽�½����ʾ������� */ 
			QuestionResult questionResult = new QuestionResult();
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			questionResult.setResultId(resultId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setStudentObj(studentObj);
			String teacherObj = new String(request.getParameter("teacherObj").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setTeacherObj(teacherObj);
			String answer = new String(request.getParameter("answer").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setAnswer(answer);

			/* ����ҵ���ִ����Ӳ��� */
			String result = questionResultDAO.AddQuestionResult(questionResult);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���ʾ�������ȡ�ʾ����ļ�¼���*/
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = questionResultDAO.DeleteQuestionResult(resultId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�����ʾ���֮ǰ�ȸ���resultId��ѯĳ���ʾ���*/
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			QuestionResult questionResult = questionResultDAO.GetQuestionResult(resultId);

			// �ͻ��˲�ѯ���ʾ������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �����ʾ�������ȡ�ʾ����������������浽�½����ʾ������� */ 
			QuestionResult questionResult = new QuestionResult();
			int resultId = Integer.parseInt(request.getParameter("resultId"));
			questionResult.setResultId(resultId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setStudentObj(studentObj);
			String teacherObj = new String(request.getParameter("teacherObj").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setTeacherObj(teacherObj);
			String answer = new String(request.getParameter("answer").getBytes("iso-8859-1"), "UTF-8");
			questionResult.setAnswer(answer);

			/* ����ҵ���ִ�и��²��� */
			String result = questionResultDAO.UpdateQuestionResult(questionResult);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
