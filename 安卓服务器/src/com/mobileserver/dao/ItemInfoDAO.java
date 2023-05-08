package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ItemInfo;
import com.mobileserver.util.DB;

public class ItemInfoDAO {

	public List<ItemInfo> QueryItemInfo(String itemTitle) {
		List<ItemInfo> itemInfoList = new ArrayList<ItemInfo>();
		DB db = new DB();
		String sql = "select * from ItemInfo where 1=1";
		if (!itemTitle.equals(""))
			sql += " and itemTitle like '%" + itemTitle + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ItemInfo itemInfo = new ItemInfo();
				itemInfo.setItemId(rs.getInt("itemId"));
				itemInfo.setItemTitle(rs.getString("itemTitle"));
				itemInfo.setItemDesc(rs.getString("itemDesc"));
				itemInfoList.add(itemInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return itemInfoList;
	}
	/* ��������ָ����󣬽�������ָ������ҵ�� */
	public String AddItemInfo(ItemInfo itemInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в���������ָ�� */
			String sqlString = "insert into ItemInfo(itemTitle,itemDesc) values (";
			sqlString += "'" + itemInfo.getItemTitle() + "',";
			sqlString += "'" + itemInfo.getItemDesc() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "����ָ����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ָ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������ָ�� */
	public String DeleteItemInfo(int itemId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ItemInfo where itemId=" + itemId;
			db.executeUpdate(sqlString);
			result = "����ָ��ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ָ��ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ������ָ�� */
	public ItemInfo GetItemInfo(int itemId) {
		ItemInfo itemInfo = null;
		DB db = new DB();
		String sql = "select * from ItemInfo where itemId=" + itemId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				itemInfo = new ItemInfo();
				itemInfo.setItemId(rs.getInt("itemId"));
				itemInfo.setItemTitle(rs.getString("itemTitle"));
				itemInfo.setItemDesc(rs.getString("itemDesc"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return itemInfo;
	}
	/* ��������ָ�� */
	public String UpdateItemInfo(ItemInfo itemInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ItemInfo set ";
			sql += "itemTitle='" + itemInfo.getItemTitle() + "',";
			sql += "itemDesc='" + itemInfo.getItemDesc() + "'";
			sql += " where itemId=" + itemInfo.getItemId();
			db.executeUpdate(sql);
			result = "����ָ����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ָ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
