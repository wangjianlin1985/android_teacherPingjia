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
	/* 传入评价时间设置对象，进行评价时间设置的添加业务 */
	public String AddTimeSet(TimeSet timeSet) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新评价时间设置 */
			String sqlString = "insert into TimeSet(startDate,endDate) values (";
			sqlString += "'" + timeSet.getStartDate() + "',";
			sqlString += "'" + timeSet.getEndDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "评价时间设置添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "评价时间设置添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除评价时间设置 */
	public String DeleteTimeSet(int timeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from TimeSet where timeId=" + timeId;
			db.executeUpdate(sqlString);
			result = "评价时间设置删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "评价时间设置删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到评价时间设置 */
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
	/* 更新评价时间设置 */
	public String UpdateTimeSet(TimeSet timeSet) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update TimeSet set ";
			sql += "startDate='" + timeSet.getStartDate() + "',";
			sql += "endDate='" + timeSet.getEndDate() + "'";
			sql += " where timeId=" + timeSet.getTimeId();
			db.executeUpdate(sql);
			result = "评价时间设置更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "评价时间设置更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
