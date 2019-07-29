package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.CheckResult;
import com.mobileclient.util.HttpUtil;

/*考核结果管理业务逻辑层*/
public class CheckResultService {
	/* 添加考核结果 */
	public String AddCheckResult(CheckResult checkResult) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultId", checkResult.getResultId() + "");
		params.put("studentObj", checkResult.getStudentObj());
		params.put("teacherObj", checkResult.getTeacherObj());
		params.put("itemObj", checkResult.getItemObj() + "");
		params.put("resultObj", checkResult.getResultObj() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CheckResultServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询考核结果 */
	public List<CheckResult> QueryCheckResult(CheckResult queryConditionCheckResult) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CheckResultServlet?action=query";
		if(queryConditionCheckResult != null) {
			urlString += "&studentObj=" + URLEncoder.encode(queryConditionCheckResult.getStudentObj(), "UTF-8") + "";
			urlString += "&teacherObj=" + URLEncoder.encode(queryConditionCheckResult.getTeacherObj(), "UTF-8") + "";
			urlString += "&itemObj=" + queryConditionCheckResult.getItemObj();
			urlString += "&resultObj=" + queryConditionCheckResult.getResultObj();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		CheckResultListHandler checkResultListHander = new CheckResultListHandler();
		xr.setContentHandler(checkResultListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<CheckResult> checkResultList = checkResultListHander.getCheckResultList();
		return checkResultList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<CheckResult> checkResultList = new ArrayList<CheckResult>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				CheckResult checkResult = new CheckResult();
				checkResult.setResultId(object.getInt("resultId"));
				checkResult.setStudentObj(object.getString("studentObj"));
				checkResult.setTeacherObj(object.getString("teacherObj"));
				checkResult.setItemObj(object.getInt("itemObj"));
				checkResult.setResultObj(object.getInt("resultObj"));
				checkResultList.add(checkResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkResultList;
	}

	/* 更新考核结果 */
	public String UpdateCheckResult(CheckResult checkResult) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultId", checkResult.getResultId() + "");
		params.put("studentObj", checkResult.getStudentObj());
		params.put("teacherObj", checkResult.getTeacherObj());
		params.put("itemObj", checkResult.getItemObj() + "");
		params.put("resultObj", checkResult.getResultObj() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CheckResultServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除考核结果 */
	public String DeleteCheckResult(int resultId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultId", resultId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CheckResultServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "考核结果信息删除失败!";
		}
	}

	/* 根据记录编号获取考核结果对象 */
	public CheckResult GetCheckResult(int resultId)  {
		List<CheckResult> checkResultList = new ArrayList<CheckResult>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultId", resultId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CheckResultServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				CheckResult checkResult = new CheckResult();
				checkResult.setResultId(object.getInt("resultId"));
				checkResult.setStudentObj(object.getString("studentObj"));
				checkResult.setTeacherObj(object.getString("teacherObj"));
				checkResult.setItemObj(object.getInt("itemObj"));
				checkResult.setResultObj(object.getInt("resultObj"));
				checkResultList.add(checkResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = checkResultList.size();
		if(size>0) return checkResultList.get(0); 
		else return null; 
	}
}
