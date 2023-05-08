package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.ItemInfo;

@Service @Transactional
public class ItemInfoDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddItemInfo(ItemInfo itemInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(itemInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ItemInfo> QueryItemInfoInfo(String itemTitle,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ItemInfo itemInfo where 1=1";
    	if(!itemTitle.equals("")) hql = hql + " and itemInfo.itemTitle like '%" + itemTitle + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List itemInfoList = q.list();
    	return (ArrayList<ItemInfo>) itemInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ItemInfo> QueryItemInfoInfo(String itemTitle) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ItemInfo itemInfo where 1=1";
    	if(!itemTitle.equals("")) hql = hql + " and itemInfo.itemTitle like '%" + itemTitle + "%'";
    	Query q = s.createQuery(hql);
    	List itemInfoList = q.list();
    	return (ArrayList<ItemInfo>) itemInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ItemInfo> QueryAllItemInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ItemInfo";
        Query q = s.createQuery(hql);
        List itemInfoList = q.list();
        return (ArrayList<ItemInfo>) itemInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String itemTitle) {
        Session s = factory.getCurrentSession();
        String hql = "From ItemInfo itemInfo where 1=1";
        if(!itemTitle.equals("")) hql = hql + " and itemInfo.itemTitle like '%" + itemTitle + "%'";
        Query q = s.createQuery(hql);
        List itemInfoList = q.list();
        recordNumber = itemInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ItemInfo GetItemInfoByItemId(int itemId) {
        Session s = factory.getCurrentSession();
        ItemInfo itemInfo = (ItemInfo)s.get(ItemInfo.class, itemId);
        return itemInfo;
    }

    /*更新ItemInfo信息*/
    public void UpdateItemInfo(ItemInfo itemInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(itemInfo);
    }

    /*删除ItemInfo信息*/
    public void DeleteItemInfo (int itemId) throws Exception {
        Session s = factory.getCurrentSession();
        Object itemInfo = s.load(ItemInfo.class, itemId);
        s.delete(itemInfo);
    }

}
