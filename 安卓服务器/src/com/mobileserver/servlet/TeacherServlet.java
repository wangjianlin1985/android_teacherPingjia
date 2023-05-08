package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.TeacherDAO;
import com.mobileserver.domain.Teacher;

import org.json.JSONStringer;

public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����ʦ��Ϣҵ������*/
	private TeacherDAO teacherDAO = new TeacherDAO();

	/*Ĭ�Ϲ��캯��*/
	public TeacherServlet() {
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
			/*��ȡ��ѯ��ʦ��Ϣ�Ĳ�����Ϣ*/
			String teacherNumber = request.getParameter("teacherNumber");
			teacherNumber = teacherNumber == null ? "" : new String(request.getParameter(
					"teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			String sex = request.getParameter("sex");
			sex = sex == null ? "" : new String(request.getParameter(
					"sex").getBytes("iso-8859-1"), "UTF-8");
			String courseName = request.getParameter("courseName");
			courseName = courseName == null ? "" : new String(request.getParameter(
					"courseName").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�н�ʦ��Ϣ��ѯ*/
			List<Teacher> teacherList = teacherDAO.QueryTeacher(teacherNumber,name,sex,courseName,telephone);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Teachers>").append("\r\n");
			for (int i = 0; i < teacherList.size(); i++) {
				sb.append("	<Teacher>").append("\r\n")
				.append("		<teacherNumber>")
				.append(teacherList.get(i).getTeacherNumber())
				.append("</teacherNumber>").append("\r\n")
				.append("		<password>")
				.append(teacherList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<name>")
				.append(teacherList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<sex>")
				.append(teacherList.get(i).getSex())
				.append("</sex>").append("\r\n")
				.append("		<age>")
				.append(teacherList.get(i).getAge())
				.append("</age>").append("\r\n")
				.append("		<courseName>")
				.append(teacherList.get(i).getCourseName())
				.append("</courseName>").append("\r\n")
				.append("		<telephone>")
				.append(teacherList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<email>")
				.append(teacherList.get(i).getEmail())
				.append("</email>").append("\r\n")
				.append("		<memo>")
				.append(teacherList.get(i).getMemo())
				.append("</memo>").append("\r\n")
				.append("	</Teacher>").append("\r\n");
			}
			sb.append("</Teachers>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Teacher teacher: teacherList) {
				  stringer.object();
			  stringer.key("teacherNumber").value(teacher.getTeacherNumber());
			  stringer.key("password").value(teacher.getPassword());
			  stringer.key("name").value(teacher.getName());
			  stringer.key("sex").value(teacher.getSex());
			  stringer.key("age").value(teacher.getAge());
			  stringer.key("courseName").value(teacher.getCourseName());
			  stringer.key("telephone").value(teacher.getTelephone());
			  stringer.key("email").value(teacher.getEmail());
			  stringer.key("memo").value(teacher.getMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӽ�ʦ��Ϣ����ȡ��ʦ��Ϣ�������������浽�½��Ľ�ʦ��Ϣ���� */ 
			Teacher teacher = new Teacher();
			String teacherNumber = new String(request.getParameter("teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherNumber(teacherNumber);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			teacher.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			teacher.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			teacher.setSex(sex);
			int age = Integer.parseInt(request.getParameter("age"));
			teacher.setAge(age);
			String courseName = new String(request.getParameter("courseName").getBytes("iso-8859-1"), "UTF-8");
			teacher.setCourseName(courseName);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			teacher.setEmail(email);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			teacher.setMemo(memo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = teacherDAO.AddTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ʦ��Ϣ����ȡ��ʦ��Ϣ�Ľ�ʦ�û���*/
			String teacherNumber = new String(request.getParameter("teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = teacherDAO.DeleteTeacher(teacherNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���½�ʦ��Ϣ֮ǰ�ȸ���teacherNumber��ѯĳ����ʦ��Ϣ*/
			String teacherNumber = new String(request.getParameter("teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			Teacher teacher = teacherDAO.GetTeacher(teacherNumber);

			// �ͻ��˲�ѯ�Ľ�ʦ��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("teacherNumber").value(teacher.getTeacherNumber());
			  stringer.key("password").value(teacher.getPassword());
			  stringer.key("name").value(teacher.getName());
			  stringer.key("sex").value(teacher.getSex());
			  stringer.key("age").value(teacher.getAge());
			  stringer.key("courseName").value(teacher.getCourseName());
			  stringer.key("telephone").value(teacher.getTelephone());
			  stringer.key("email").value(teacher.getEmail());
			  stringer.key("memo").value(teacher.getMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���½�ʦ��Ϣ����ȡ��ʦ��Ϣ�������������浽�½��Ľ�ʦ��Ϣ���� */ 
			Teacher teacher = new Teacher();
			String teacherNumber = new String(request.getParameter("teacherNumber").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherNumber(teacherNumber);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			teacher.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			teacher.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			teacher.setSex(sex);
			int age = Integer.parseInt(request.getParameter("age"));
			teacher.setAge(age);
			String courseName = new String(request.getParameter("courseName").getBytes("iso-8859-1"), "UTF-8");
			teacher.setCourseName(courseName);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			teacher.setEmail(email);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			teacher.setMemo(memo);

			/* ����ҵ���ִ�и��²��� */
			String result = teacherDAO.UpdateTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
