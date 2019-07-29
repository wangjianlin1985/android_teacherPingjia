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
import com.chengxusheji.domain.ResultItem;

@Service @Transactional
public class ResultItemDAO {

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
    public void AddResultItem(ResultItem resultItem) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(resultItem);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ResultItem> QueryResultItemInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ResultItem resultItem where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List resultItemList = q.list();
    	return (ArrayList<ResultItem>) resultItemList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ResultItem> QueryResultItemInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ResultItem resultItem where 1=1";
    	Query q = s.createQuery(hql);
    	List resultItemList = q.list();
    	return (ArrayList<ResultItem>) resultItemList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ResultItem> QueryAllResultItemInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ResultItem";
        Query q = s.createQuery(hql);
        List resultItemList = q.list();
        return (ArrayList<ResultItem>) resultItemList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From ResultItem resultItem where 1=1";
        Query q = s.createQuery(hql);
        List resultItemList = q.list();
        recordNumber = resultItemList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ResultItem GetResultItemByResultItemId(int resultItemId) {
        Session s = factory.getCurrentSession();
        ResultItem resultItem = (ResultItem)s.get(ResultItem.class, resultItemId);
        return resultItem;
    }

    /*更新ResultItem信息*/
    public void UpdateResultItem(ResultItem resultItem) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(resultItem);
    }

    /*删除ResultItem信息*/
    public void DeleteResultItem (int resultItemId) throws Exception {
        Session s = factory.getCurrentSession();
        Object resultItem = s.load(ResultItem.class, resultItemId);
        s.delete(resultItem);
    }

}
