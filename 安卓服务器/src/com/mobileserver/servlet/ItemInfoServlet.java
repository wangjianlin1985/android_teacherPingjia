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

	/*构造评价指标业务层对象*/
	private ItemInfoDAO itemInfoDAO = new ItemInfoDAO();

	/*默认构造函数*/
	public ItemInfoServlet() {
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
			/*获取查询评价指标的参数信息*/
			String itemTitle = request.getParameter("itemTitle");
			itemTitle = itemTitle == null ? "" : new String(request.getParameter(
					"itemTitle").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行评价指标查询*/
			List<ItemInfo> itemInfoList = itemInfoDAO.QueryItemInfo(itemTitle);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加评价指标：获取评价指标参数，参数保存到新建的评价指标对象 */ 
			ItemInfo itemInfo = new ItemInfo();
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			itemInfo.setItemId(itemId);
			String itemTitle = new String(request.getParameter("itemTitle").getBytes("iso-8859-1"), "UTF-8");
			itemInfo.setItemTitle(itemTitle);
			String itemDesc = new String(request.getParameter("itemDesc").getBytes("iso-8859-1"), "UTF-8");
			itemInfo.setItemDesc(itemDesc);

			/* 调用业务层执行添加操作 */
			String result = itemInfoDAO.AddItemInfo(itemInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除评价指标：获取评价指标的记录编号*/
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			/*调用业务逻辑层执行删除操作*/
			String result = itemInfoDAO.DeleteItemInfo(itemId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新评价指标之前先根据itemId查询某个评价指标*/
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			ItemInfo itemInfo = itemInfoDAO.GetItemInfo(itemId);

			// 客户端查询的评价指标对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新评价指标：获取评价指标参数，参数保存到新建的评价指标对象 */ 
			ItemInfo itemInfo = new ItemInfo();
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			itemInfo.setItemId(itemId);
			String itemTitle = new String(request.getParameter("itemTitle").getBytes("iso-8859-1"), "UTF-8");
			itemInfo.setItemTitle(itemTitle);
			String itemDesc = new String(request.getParameter("itemDesc").getBytes("iso-8859-1"), "UTF-8");
			itemInfo.setItemDesc(itemDesc);

			/* 调用业务层执行更新操作 */
			String result = itemInfoDAO.UpdateItemInfo(itemInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
