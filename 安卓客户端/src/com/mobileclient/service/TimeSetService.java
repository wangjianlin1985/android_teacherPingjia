package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.TimeSet;
import com.mobileclient.util.HttpUtil;

/*评价时间设置管理业务逻辑层*/
public class TimeSetService {
	/* 添加评价时间设置 */
	public String AddTimeSet(TimeSet timeSet) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("timeId", timeSet.getTimeId() + "");
		params.put("startDate", timeSet.getStartDate().toString());
		params.put("endDate", timeSet.getEndDate().toString());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSetServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询评价时间设置 */
	public List<TimeSet> QueryTimeSet(TimeSet queryConditionTimeSet) throws Exception {
		String urlString = HttpUtil.BASE_URL + "TimeSetServlet?action=query";
		if(queryConditionTimeSet != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		TimeSetListHandler timeSetListHander = new TimeSetListHandler();
		xr.setContentHandler(timeSetListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<TimeSet> timeSetList = timeSetListHander.getTimeSetList();
		return timeSetList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<TimeSet> timeSetList = new ArrayList<TimeSet>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				TimeSet timeSet = new TimeSet();
				timeSet.setTimeId(object.getInt("timeId"));
				timeSet.setStartDate(Timestamp.valueOf(object.getString("startDate")));
				timeSet.setEndDate(Timestamp.valueOf(object.getString("endDate")));
				timeSetList.add(timeSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeSetList;
	}

	/* 更新评价时间设置 */
	public String UpdateTimeSet(TimeSet timeSet) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("timeId", timeSet.getTimeId() + "");
		params.put("startDate", timeSet.getStartDate().toString());
		params.put("endDate", timeSet.getEndDate().toString());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSetServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除评价时间设置 */
	public String DeleteTimeSet(int timeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("timeId", timeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSetServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "评价时间设置信息删除失败!";
		}
	}

	/* 根据记录编号获取评价时间设置对象 */
	public TimeSet GetTimeSet(int timeId)  {
		List<TimeSet> timeSetList = new ArrayList<TimeSet>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("timeId", timeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSetServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				TimeSet timeSet = new TimeSet();
				timeSet.setTimeId(object.getInt("timeId"));
				timeSet.setStartDate(Timestamp.valueOf(object.getString("startDate")));
				timeSet.setEndDate(Timestamp.valueOf(object.getString("endDate")));
				timeSetList.add(timeSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = timeSetList.size();
		if(size>0) return timeSetList.get(0); 
		else return null; 
	}
}
