package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ResultItem;
import com.mobileclient.util.HttpUtil;

/*结果指标管理业务逻辑层*/
public class ResultItemService {
	/* 添加结果指标 */
	public String AddResultItem(ResultItem resultItem) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultItemId", resultItem.getResultItemId() + "");
		params.put("resultItemText", resultItem.getResultItemText());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ResultItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询结果指标 */
	public List<ResultItem> QueryResultItem(ResultItem queryConditionResultItem) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ResultItemServlet?action=query";
		if(queryConditionResultItem != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ResultItemListHandler resultItemListHander = new ResultItemListHandler();
		xr.setContentHandler(resultItemListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ResultItem> resultItemList = resultItemListHander.getResultItemList();
		return resultItemList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<ResultItem> resultItemList = new ArrayList<ResultItem>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ResultItem resultItem = new ResultItem();
				resultItem.setResultItemId(object.getInt("resultItemId"));
				resultItem.setResultItemText(object.getString("resultItemText"));
				resultItemList.add(resultItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultItemList;
	}

	/* 更新结果指标 */
	public String UpdateResultItem(ResultItem resultItem) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultItemId", resultItem.getResultItemId() + "");
		params.put("resultItemText", resultItem.getResultItemText());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ResultItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除结果指标 */
	public String DeleteResultItem(int resultItemId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultItemId", resultItemId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ResultItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "结果指标信息删除失败!";
		}
	}

	/* 根据记录编号获取结果指标对象 */
	public ResultItem GetResultItem(int resultItemId)  {
		List<ResultItem> resultItemList = new ArrayList<ResultItem>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultItemId", resultItemId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ResultItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ResultItem resultItem = new ResultItem();
				resultItem.setResultItemId(object.getInt("resultItemId"));
				resultItem.setResultItemText(object.getString("resultItemText"));
				resultItemList.add(resultItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = resultItemList.size();
		if(size>0) return resultItemList.get(0); 
		else return null; 
	}
}
