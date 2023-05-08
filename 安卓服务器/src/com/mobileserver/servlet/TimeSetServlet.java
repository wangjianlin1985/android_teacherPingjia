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

	/*��������ʱ������ҵ������*/
	private TimeSetDAO timeSetDAO = new TimeSetDAO();

	/*Ĭ�Ϲ��캯��*/
	public TimeSetServlet() {
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
			/*��ȡ��ѯ����ʱ�����õĲ�����Ϣ*/

			/*����ҵ���߼���ִ������ʱ�����ò�ѯ*/
			List<TimeSet> timeSetList = timeSetDAO.QueryTimeSet();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �������ʱ�����ã���ȡ����ʱ�����ò������������浽�½�������ʱ�����ö��� */ 
			TimeSet timeSet = new TimeSet();
			int timeId = Integer.parseInt(request.getParameter("timeId"));
			timeSet.setTimeId(timeId);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			timeSet.setStartDate(startDate);
			Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate"));
			timeSet.setEndDate(endDate);

			/* ����ҵ���ִ����Ӳ��� */
			String result = timeSetDAO.AddTimeSet(timeSet);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ������ʱ�����ã���ȡ����ʱ�����õļ�¼���*/
			int timeId = Integer.parseInt(request.getParameter("timeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = timeSetDAO.DeleteTimeSet(timeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*��������ʱ������֮ǰ�ȸ���timeId��ѯĳ������ʱ������*/
			int timeId = Integer.parseInt(request.getParameter("timeId"));
			TimeSet timeSet = timeSetDAO.GetTimeSet(timeId);

			// �ͻ��˲�ѯ������ʱ�����ö��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ��������ʱ�����ã���ȡ����ʱ�����ò������������浽�½�������ʱ�����ö��� */ 
			TimeSet timeSet = new TimeSet();
			int timeId = Integer.parseInt(request.getParameter("timeId"));
			timeSet.setTimeId(timeId);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			timeSet.setStartDate(startDate);
			Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate"));
			timeSet.setEndDate(endDate);

			/* ����ҵ���ִ�и��²��� */
			String result = timeSetDAO.UpdateTimeSet(timeSet);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
