package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ItemInfoDAO;
import com.mobileserver.domain.ItemInfo;

import org.json.JSONStringer;

public class ItemInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*��������ָ��ҵ������*/
	private ItemInfoDAO itemInfoDAO = new ItemInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public ItemInfoServlet() {
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
			/*��ȡ��ѯ����ָ��Ĳ�����Ϣ*/
			String itemTitle = request.getParameter("itemTitle");
			itemTitle = itemTitle == null ? "" : new String(request.getParameter(
					"itemTitle").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ������ָ���ѯ*/
			List<ItemInfo> itemInfoList = itemInfoDAO.QueryItemInfo(itemTitle);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<ItemInfos>").append("\r\n");
			for (int i = 0; i < itemInfoList.size(); i++) {
				sb.append("	<ItemInfo>").append("\r\n")
				.append("		<itemId>")
				.append(itemInfoList.get(i).getItemId())
				.append("</itemId>").append("\r\n")
				.append("		<itemTitle>")
				.append(itemInfoList.get(i).getItemTitle())
				.append("</itemTitle>").append("\r\n")
				.append("		<itemDesc>")
				.append(itemInfoList.get(i).getItemDesc())
				.append("</itemDesc>").append("\r\n")
				.append("	</ItemInfo>").append("\r\n");
			}
			sb.append("</ItemInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(ItemInfo itemInfo: itemInfoList) {
				  stringer.object();
			  stringer.key("itemId").value(itemInfo.getItemId());
			  stringer.key("itemTitle").value(itemInfo.getItemTitle());
			  stringer.key("itemDesc").value(itemInfo.getItemDesc());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �������ָ�꣺��ȡ����ָ��������������浽�½�������ָ����� */ 
			ItemInfo itemInfo = new ItemInfo();
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			itemInfo.setItemId(itemId);
			String itemTitle = new String(request.getParameter("itemTitle").getBytes("iso-8859-1"), "UTF-8");
			itemInfo.setItemTitle(itemTitle);
			String itemDesc = new String(request.getParameter("itemDesc").getBytes("iso-8859-1"), "UTF-8");
			itemInfo.setItemDesc(itemDesc);

			/* ����ҵ���ִ����Ӳ��� */
			String result = itemInfoDAO.AddItemInfo(itemInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ������ָ�꣺��ȡ����ָ��ļ�¼���*/
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = itemInfoDAO.DeleteItemInfo(itemId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*��������ָ��֮ǰ�ȸ���itemId��ѯĳ������ָ��*/
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			ItemInfo itemInfo = itemInfoDAO.GetItemInfo(itemId);

			// �ͻ��˲�ѯ������ָ����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("itemId").value(itemInfo.getItemId());
			  stringer.key("itemTitle").value(itemInfo.getItemTitle());
			  stringer.key("itemDesc").value(itemInfo.getItemDesc());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ��������ָ�꣺��ȡ����ָ��������������浽�½�������ָ����� */ 
			ItemInfo itemInfo = new ItemInfo();
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			itemInfo.setItemId(itemId);
			String itemTitle = new String(request.getParameter("itemTitle").getBytes("iso-8859-1"), "UTF-8");
			itemInfo.setItemTitle(itemTitle);
			String itemDesc = new String(request.getParameter("itemDesc").getBytes("iso-8859-1"), "UTF-8");
			itemInfo.setItemDesc(itemDesc);

			/* ����ҵ���ִ�и��²��� */
			String result = itemInfoDAO.UpdateItemInfo(itemInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
