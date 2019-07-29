package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.QuestionResult;
import com.mobileserver.util.DB;

public class QuestionResultDAO {

	public List<QuestionResult> QueryQuestionResult(String studentObj,String teacherObj) {
		List<QuestionResult> questionResultList = new ArrayList<QuestionResult>();
		DB db = new DB();
		String sql = "select * from QuestionResult where 1=1";
		if (!studentObj.equals(""))
			sql += " and studentObj = '" + studentObj + "'";
		if (!teacherObj.equals(""))
			sql += " and teacherObj = '" + teacherObj + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				QuestionResult questionResult = new QuestionResult();
				questionResult.setResultId(rs.getInt("resultId"));
				questionResult.setStudentObj(rs.getString("studentObj"));
				questionResult.setTeacherObj(rs.getString("teacherObj"));
				questionResult.setAnswer(rs.getString("answer"));
				questionResultList.add(questionResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return questionResultList;
	}
	/* 传入问卷结果对象，进行问卷结果的添加业务 */
	public String AddQuestionResult(QuestionResult questionResult) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新问卷结果 */
			String sqlString = "insert into QuestionResult(studentObj,teacherObj,answer) values (";
			sqlString += "'" + questionResult.getStudentObj() + "',";
			sqlString += "'" + questionResult.getTeacherObj() + "',";
			sqlString += "'" + questionResult.getAnswer() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "问卷结果添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "问卷结果添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除问卷结果 */
	public String DeleteQuestionResult(int resultId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from QuestionResult where resultId=" + resultId;
			db.executeUpdate(sqlString);
			result = "问卷结果删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "问卷结果删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到问卷结果 */
	public QuestionResult GetQuestionResult(int resultId) {
		QuestionResult questionResult = null;
		DB db = new DB();
		String sql = "select * from QuestionResult where resultId=" + resultId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				questionResult = new QuestionResult();
				questionResult.setResultId(rs.getInt("resultId"));
				questionResult.setStudentObj(rs.getString("studentObj"));
				questionResult.setTeacherObj(rs.getString("teacherObj"));
				questionResult.setAnswer(rs.getString("answer"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return questionResult;
	}
	/* 更新问卷结果 */
	public String UpdateQuestionResult(QuestionResult questionResult) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update QuestionResult set ";
			sql += "studentObj='" + questionResult.getStudentObj() + "',";
			sql += "teacherObj='" + questionResult.getTeacherObj() + "',";
			sql += "answer='" + questionResult.getAnswer() + "'";
			sql += " where resultId=" + questionResult.getResultId();
			db.executeUpdate(sql);
			result = "问卷结果更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "问卷结果更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
