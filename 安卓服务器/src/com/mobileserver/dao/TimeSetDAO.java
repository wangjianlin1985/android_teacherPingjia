package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.TimeSet;
import com.mobileserver.util.DB;

public class TimeSetDAO {

	public List<TimeSet> QueryTimeSet() {
		List<TimeSet> timeSetList = new ArrayList<TimeSet>();
		DB db = new DB();
		String sql = "select * from TimeSet where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				TimeSet timeSet = new TimeSet();
				timeSet.setTimeId(rs.getInt("timeId"));
				timeSet.setStartDate(rs.getTimestamp("startDate"));
				timeSet.setEndDate(rs.getTimestamp("endDate"));
				timeSetList.add(timeSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return timeSetList;
	}
	/* ��������ʱ�����ö��󣬽�������ʱ�����õ����ҵ�� */
	public String AddTimeSet(TimeSet timeSet) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в���������ʱ������ */
			String sqlString = "insert into TimeSet(startDate,endDate) values (";
			sqlString += "'" + timeSet.getStartDate() + "',";
			sqlString += "'" + timeSet.getEndDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "����ʱ��������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ʱ���������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������ʱ������ */
	public String DeleteTimeSet(int timeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from TimeSet where timeId=" + timeId;
			db.executeUpdate(sqlString);
			result = "����ʱ������ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ʱ������ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ������ʱ������ */
	public TimeSet GetTimeSet(int timeId) {
		TimeSet timeSet = null;
		DB db = new DB();
		String sql = "select * from TimeSet where timeId=" + timeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				timeSet = new TimeSet();
				timeSet.setTimeId(rs.getInt("timeId"));
				timeSet.setStartDate(rs.getTimestamp("startDate"));
				timeSet.setEndDate(rs.getTimestamp("endDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return timeSet;
	}
	/* ��������ʱ������ */
	public String UpdateTimeSet(TimeSet timeSet) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update TimeSet set ";
			sql += "startDate='" + timeSet.getStartDate() + "',";
			sql += "endDate='" + timeSet.getEndDate() + "'";
			sql += " where timeId=" + timeSet.getTimeId();
			db.executeUpdate(sql);
			result = "����ʱ�����ø��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ʱ�����ø���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
