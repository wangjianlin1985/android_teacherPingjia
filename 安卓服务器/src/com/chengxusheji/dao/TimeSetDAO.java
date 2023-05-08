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
import com.chengxusheji.domain.TimeSet;

@Service @Transactional
public class TimeSetDAO {

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
    public void AddTimeSet(TimeSet timeSet) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(timeSet);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TimeSet> QueryTimeSetInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From TimeSet timeSet where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List timeSetList = q.list();
    	return (ArrayList<TimeSet>) timeSetList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TimeSet> QueryTimeSetInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From TimeSet timeSet where 1=1";
    	Query q = s.createQuery(hql);
    	List timeSetList = q.list();
    	return (ArrayList<TimeSet>) timeSetList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TimeSet> QueryAllTimeSetInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From TimeSet";
        Query q = s.createQuery(hql);
        List timeSetList = q.list();
        return (ArrayList<TimeSet>) timeSetList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From TimeSet timeSet where 1=1";
        Query q = s.createQuery(hql);
        List timeSetList = q.list();
        recordNumber = timeSetList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public TimeSet GetTimeSetByTimeId(int timeId) {
        Session s = factory.getCurrentSession();
        TimeSet timeSet = (TimeSet)s.get(TimeSet.class, timeId);
        return timeSet;
    }

    /*����TimeSet��Ϣ*/
    public void UpdateTimeSet(TimeSet timeSet) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(timeSet);
    }

    /*ɾ��TimeSet��Ϣ*/
    public void DeleteTimeSet (int timeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object timeSet = s.load(TimeSet.class, timeId);
        s.delete(timeSet);
    }

}
