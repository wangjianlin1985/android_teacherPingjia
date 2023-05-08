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

	/*������ָ��ҵ������*/
	private ResultItemDAO resultItemDAO = new ResultItemDAO();

	/*Ĭ�Ϲ��캯��*/
	public ResultItemServlet() {
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
			/*��ȡ��ѯ���ָ��Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�н��ָ���ѯ*/
			List<ResultItem> resultItemList = resultItemDAO.QueryResultItem();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӽ��ָ�꣺��ȡ���ָ��������������浽�½��Ľ��ָ����� */ 
			ResultItem resultItem = new ResultItem();
			int resultItemId = Integer.parseInt(request.getParameter("resultItemId"));
			resultItem.setResultItemId(resultItemId);
			String resultItemText = new String(request.getParameter("resultItemText").getBytes("iso-8859-1"), "UTF-8");
			resultItem.setResultItemText(resultItemText);

			/* ����ҵ���ִ����Ӳ��� */
			String result = resultItemDAO.AddResultItem(resultItem);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����ָ�꣺��ȡ���ָ��ļ�¼���*/
			int resultItemId = Integer.parseInt(request.getParameter("resultItemId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = resultItemDAO.DeleteResultItem(resultItemId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���½��ָ��֮ǰ�ȸ���resultItemId��ѯĳ�����ָ��*/
			int resultItemId = Integer.parseInt(request.getParameter("resultItemId"));
			ResultItem resultItem = resultItemDAO.GetResultItem(resultItemId);

			// �ͻ��˲�ѯ�Ľ��ָ����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���½��ָ�꣺��ȡ���ָ��������������浽�½��Ľ��ָ����� */ 
			ResultItem resultItem = new ResultItem();
			int resultItemId = Integer.parseInt(request.getParameter("resultItemId"));
			resultItem.setResultItemId(resultItemId);
			String resultItemText = new String(request.getParameter("resultItemText").getBytes("iso-8859-1"), "UTF-8");
			resultItem.setResultItemText(resultItemText);

			/* ����ҵ���ִ�и��²��� */
			String result = resultItemDAO.UpdateResultItem(resultItem);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
