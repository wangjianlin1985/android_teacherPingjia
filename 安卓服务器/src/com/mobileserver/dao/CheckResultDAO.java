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
	/* ���뿼�˽�����󣬽��п��˽�������ҵ�� */
	public String AddCheckResult(CheckResult checkResult) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����¿��˽�� */
			String sqlString = "insert into CheckResult(studentObj,teacherObj,itemObj,resultObj) values (";
			sqlString += "'" + checkResult.getStudentObj() + "',";
			sqlString += "'" + checkResult.getTeacherObj() + "',";
			sqlString += checkResult.getItemObj() + ",";
			sqlString += checkResult.getResultObj() ;
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���˽����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���˽�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�����˽�� */
	public String DeleteCheckResult(int resultId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from CheckResult where resultId=" + resultId;
			db.executeUpdate(sqlString);
			result = "���˽��ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���˽��ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ�����˽�� */
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
	/* ���¿��˽�� */
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
			result = "���˽�����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���˽������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
