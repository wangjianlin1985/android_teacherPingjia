package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.CheckResult;
import com.mobileserver.util.DB;

public class CheckResultDAO {

	public List<CheckResult> QueryCheckResult(String studentObj,String teacherObj,int itemObj,int resultObj) {
		List<CheckResult> checkResultList = new ArrayList<CheckResult>();
		DB db = new DB();
		String sql = "select * from CheckResult where 1=1";
		if (!studentObj.equals(""))
			sql += " and studentObj = '" + studentObj + "'";
		if (!teacherObj.equals(""))
			sql += " and teacherObj = '" + teacherObj + "'";
		if (itemObj != 0)
			sql += " and itemObj=" + itemObj;
		if (resultObj != 0)
			sql += " and resultObj=" + resultObj;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				CheckResult checkResult = new CheckResult();
				checkResult.setResultId(rs.getInt("resultId"));
				checkResult.setStudentObj(rs.getString("studentObj"));
				checkResult.setTeacherObj(rs.getString("teacherObj"));
				checkResult.setItemObj(rs.getInt("itemObj"));
				checkResult.setResultObj(rs.getInt("resultObj"));
				checkResultList.add(checkResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return checkResultList;
	}
	/* 传入考核结果对象，进行考核结果的添加业务 */
	public String AddCheckResult(CheckResult checkResult) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新考核结果 */
			String sqlString = "insert into CheckResult(studentObj,teacherObj,itemObj,resultObj) values (";
			sqlString += "'" + checkResult.getStudentObj() + "',";
			sqlString += "'" + checkResult.getTeacherObj() + "',";
			sqlString += checkResult.getItemObj() + ",";
			sqlString += checkResult.getResultObj() ;
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "考核结果添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "考核结果添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除考核结果 */
	public String DeleteCheckResult(int resultId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from CheckResult where resultId=" + resultId;
			db.executeUpdate(sqlString);
			result = "考核结果删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "考核结果删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到考核结果 */
	public CheckResult GetCheckResult(int resultId) {
		CheckResult checkResult = null;
		DB db = new DB();
		String sql = "select * from CheckResult where resultId=" + resultId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				checkResult = new CheckResult();
				checkResult.setResultId(rs.getInt("resultId"));
				checkResult.setStudentObj(rs.getString("studentObj"));
				checkResult.setTeacherObj(rs.getString("teacherObj"));
				checkResult.setItemObj(rs.getInt("itemObj"));
				checkResult.setResultObj(rs.getInt("resultObj"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return checkResult;
	}
	/* 更新考核结果 */
	public String UpdateCheckResult(CheckResult checkResult) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update CheckResult set ";
			sql += "studentObj='" + checkResult.getStudentObj() + "',";
			sql += "teacherObj='" + checkResult.getTeacherObj() + "',";
			sql += "itemObj=" + checkResult.getItemObj() + ",";
			sql += "resultObj=" + checkResult.getResultObj();
			sql += " where resultId=" + checkResult.getResultId();
			db.executeUpdate(sql);
			result = "考核结果更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "考核结果更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
