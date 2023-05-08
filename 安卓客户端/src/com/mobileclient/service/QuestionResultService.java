package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.QuestionResult;
import com.mobileclient.util.HttpUtil;

/*问卷结果管理业务逻辑层*/
public class QuestionResultService {
	/* 添加问卷结果 */
	public String AddQuestionResult(QuestionResult questionResult) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultId", questionResult.getResultId() + "");
		params.put("studentObj", questionResult.getStudentObj());
		params.put("teacherObj", questionResult.getTeacherObj());
		params.put("answer", questionResult.getAnswer());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QuestionResultServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询问卷结果 */
	public List<QuestionResult> QueryQuestionResult(QuestionResult queryConditionQuestionResult) throws Exception {
		String urlString = HttpUtil.BASE_URL + "QuestionResultServlet?action=query";
		if(queryConditionQuestionResult != null) {
			urlString += "&studentObj=" + URLEncoder.encode(queryConditionQuestionResult.getStudentObj(), "UTF-8") + "";
			urlString += "&teacherObj=" + URLEncoder.encode(queryConditionQuestionResult.getTeacherObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		QuestionResultListHandler questionResultListHander = new QuestionResultListHandler();
		xr.setContentHandler(questionResultListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<QuestionResult> questionResultList = questionResultListHander.getQuestionResultList();
		return questionResultList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<QuestionResult> questionResultList = new ArrayList<QuestionResult>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				QuestionResult questionResult = new QuestionResult();
				questionResult.setResultId(object.getInt("resultId"));
				questionResult.setStudentObj(object.getString("studentObj"));
				questionResult.setTeacherObj(object.getString("teacherObj"));
				questionResult.setAnswer(object.getString("answer"));
				questionResultList.add(questionResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return questionResultList;
	}

	/* 更新问卷结果 */
	public String UpdateQuestionResult(QuestionResult questionResult) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultId", questionResult.getResultId() + "");
		params.put("studentObj", questionResult.getStudentObj());
		params.put("teacherObj", questionResult.getTeacherObj());
		params.put("answer", questionResult.getAnswer());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QuestionResultServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除问卷结果 */
	public String DeleteQuestionResult(int resultId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultId", resultId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QuestionResultServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "问卷结果信息删除失败!";
		}
	}

	/* 根据记录编号获取问卷结果对象 */
	public QuestionResult GetQuestionResult(int resultId)  {
		List<QuestionResult> questionResultList = new ArrayList<QuestionResult>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("resultId", resultId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QuestionResultServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				QuestionResult questionResult = new QuestionResult();
				questionResult.setResultId(object.getInt("resultId"));
				questionResult.setStudentObj(object.getString("studentObj"));
				questionResult.setTeacherObj(object.getString("teacherObj"));
				questionResult.setAnswer(object.getString("answer"));
				questionResultList.add(questionResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = questionResultList.size();
		if(size>0) return questionResultList.get(0); 
		else return null; 
	}
}
