package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ResultItem;
import com.mobileserver.util.DB;

public class ResultItemDAO {

	public List<ResultItem> QueryResultItem() {
		List<ResultItem> resultItemList = new ArrayList<ResultItem>();
		DB db = new DB();
		String sql = "select * from ResultItem where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ResultItem resultItem = new ResultItem();
				resultItem.setResultItemId(rs.getInt("resultItemId"));
				resultItem.setResultItemText(rs.getString("resultItemText"));
				resultItemList.add(resultItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return resultItemList;
	}
	/* 传入结果指标对象，进行结果指标的添加业务 */
	public String AddResultItem(ResultItem resultItem) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新结果指标 */
			String sqlString = "insert into ResultItem(resultItemText) values (";
			sqlString += "'" + resultItem.getResultItemText() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "结果指标添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "结果指标添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除结果指标 */
	public String DeleteResultItem(int resultItemId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ResultItem where resultItemId=" + resultItemId;
			db.executeUpdate(sqlString);
			result = "结果指标删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "结果指标删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到结果指标 */
	public ResultItem GetResultItem(int resultItemId) {
		ResultItem resultItem = null;
		DB db = new DB();
		String sql = "select * from ResultItem where resultItemId=" + resultItemId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				resultItem = new ResultItem();
				resultItem.setResultItemId(rs.getInt("resultItemId"));
				resultItem.setResultItemText(rs.getString("resultItemText"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return resultItem;
	}
	/* 更新结果指标 */
	public String UpdateResultItem(ResultItem resultItem) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ResultItem set ";
			sql += "resultItemText='" + resultItem.getResultItemText() + "'";
			sql += " where resultItemId=" + resultItem.getResultItemId();
			db.executeUpdate(sql);
			result = "结果指标更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "结果指标更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
