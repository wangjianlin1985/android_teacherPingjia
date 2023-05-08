package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ItemInfo;
import com.mobileclient.util.HttpUtil;

/*评价指标管理业务逻辑层*/
public class ItemInfoService {
	/* 添加评价指标 */
	public String AddItemInfo(ItemInfo itemInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemInfo.getItemId() + "");
		params.put("itemTitle", itemInfo.getItemTitle());
		params.put("itemDesc", itemInfo.getItemDesc());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ItemInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询评价指标 */
	public List<ItemInfo> QueryItemInfo(ItemInfo queryConditionItemInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ItemInfoServlet?action=query";
		if(queryConditionItemInfo != null) {
			urlString += "&itemTitle=" + URLEncoder.encode(queryConditionItemInfo.getItemTitle(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ItemInfoListHandler itemInfoListHander = new ItemInfoListHandler();
		xr.setContentHandler(itemInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ItemInfo> itemInfoList = itemInfoListHander.getItemInfoList();
		return itemInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<ItemInfo> itemInfoList = new ArrayList<ItemInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ItemInfo itemInfo = new ItemInfo();
				itemInfo.setItemId(object.getInt("itemId"));
				itemInfo.setItemTitle(object.getString("itemTitle"));
				itemInfo.setItemDesc(object.getString("itemDesc"));
				itemInfoList.add(itemInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemInfoList;
	}

	/* 更新评价指标 */
	public String UpdateItemInfo(ItemInfo itemInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemInfo.getItemId() + "");
		params.put("itemTitle", itemInfo.getItemTitle());
		params.put("itemDesc", itemInfo.getItemDesc());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ItemInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除评价指标 */
	public String DeleteItemInfo(int itemId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ItemInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "评价指标信息删除失败!";
		}
	}

	/* 根据记录编号获取评价指标对象 */
	public ItemInfo GetItemInfo(int itemId)  {
		List<ItemInfo> itemInfoList = new ArrayList<ItemInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ItemInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ItemInfo itemInfo = new ItemInfo();
				itemInfo.setItemId(object.getInt("itemId"));
				itemInfo.setItemTitle(object.getString("itemTitle"));
				itemInfo.setItemDesc(object.getString("itemDesc"));
				itemInfoList.add(itemInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = itemInfoList.size();
		if(size>0) return itemInfoList.get(0); 
		else return null; 
	}
}
