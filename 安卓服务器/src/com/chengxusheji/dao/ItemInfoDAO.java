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
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
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
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ItemInfo GetItemInfoByItemId(int itemId) {
        Session s = factory.getCurrentSession();
        ItemInfo itemInfo = (ItemInfo)s.get(ItemInfo.class, itemId);
        return itemInfo;
    }

    /*����ItemInfo��Ϣ*/
    public void UpdateItemInfo(ItemInfo itemInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(itemInfo);
    }

    /*ɾ��ItemInfo��Ϣ*/
    public void DeleteItemInfo (int itemId) throws Exception {
        Session s = factory.getCurrentSession();
        Object itemInfo = s.load(ItemInfo.class, itemId);
        s.delete(itemInfo);
    }

}
