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
	/* ������ָ����󣬽��н��ָ������ҵ�� */
	public String AddResultItem(ResultItem resultItem) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����½��ָ�� */
			String sqlString = "insert into ResultItem(resultItemText) values (";
			sqlString += "'" + resultItem.getResultItemText() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���ָ����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ָ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�����ָ�� */
	public String DeleteResultItem(int resultItemId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ResultItem where resultItemId=" + resultItemId;
			db.executeUpdate(sqlString);
			result = "���ָ��ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ָ��ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ�����ָ�� */
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
	/* ���½��ָ�� */
	public String UpdateResultItem(ResultItem resultItem) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ResultItem set ";
			sql += "resultItemText='" + resultItem.getResultItemText() + "'";
			sql += " where resultItemId=" + resultItem.getResultItemId();
			db.executeUpdate(sql);
			result = "���ָ����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ָ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
