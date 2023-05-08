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
import com.chengxusheji.domain.Student;
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.domain.ItemInfo;
import com.chengxusheji.domain.ResultItem;
import com.chengxusheji.domain.CheckResult;

@Service @Transactional
public class CheckResultDAO {

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
    public void AddCheckResult(CheckResult checkResult) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(checkResult);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CheckResult> QueryCheckResultInfo(Student studentObj,Teacher teacherObj,ItemInfo itemObj,ResultItem resultObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From CheckResult checkResult where 1=1";
    	if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and checkResult.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
    	if(null != teacherObj && !teacherObj.getTeacherNumber().equals("")) hql += " and checkResult.teacherObj.teacherNumber='" + teacherObj.getTeacherNumber() + "'";
    	if(null != itemObj && itemObj.getItemId()!=0) hql += " and checkResult.itemObj.itemId=" + itemObj.getItemId();
    	if(null != resultObj && resultObj.getResultItemId()!=0) hql += " and checkResult.resultObj.resultItemId=" + resultObj.getResultItemId();
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List checkResultList = q.list();
    	return (ArrayList<CheckResult>) checkResultList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CheckResult> QueryCheckResultInfo(Student studentObj,Teacher teacherObj,ItemInfo itemObj,ResultItem resultObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From CheckResult checkResult where 1=1";
    	if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and checkResult.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
    	if(null != teacherObj && !teacherObj.getTeacherNumber().equals("")) hql += " and checkResult.teacherObj.teacherNumber='" + teacherObj.getTeacherNumber() + "'";
    	if(null != itemObj && itemObj.getItemId()!=0) hql += " and checkResult.itemObj.itemId=" + itemObj.getItemId();
    	if(null != resultObj && resultObj.getResultItemId()!=0) hql += " and checkResult.resultObj.resultItemId=" + resultObj.getResultItemId();
    	Query q = s.createQuery(hql);
    	List checkResultList = q.list();
    	return (ArrayList<CheckResult>) checkResultList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CheckResult> QueryAllCheckResultInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From CheckResult";
        Query q = s.createQuery(hql);
        List checkResultList = q.list();
        return (ArrayList<CheckResult>) checkResultList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Student studentObj,Teacher teacherObj,ItemInfo itemObj,ResultItem resultObj) {
        Session s = factory.getCurrentSession();
        String hql = "From CheckResult checkResult where 1=1";
        if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and checkResult.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
        if(null != teacherObj && !teacherObj.getTeacherNumber().equals("")) hql += " and checkResult.teacherObj.teacherNumber='" + teacherObj.getTeacherNumber() + "'";
        if(null != itemObj && itemObj.getItemId()!=0) hql += " and checkResult.itemObj.itemId=" + itemObj.getItemId();
        if(null != resultObj && resultObj.getResultItemId()!=0) hql += " and checkResult.resultObj.resultItemId=" + resultObj.getResultItemId();
        Query q = s.createQuery(hql);
        List checkResultList = q.list();
        recordNumber = checkResultList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public CheckResult GetCheckResultByResultId(int resultId) {
        Session s = factory.getCurrentSession();
        CheckResult checkResult = (CheckResult)s.get(CheckResult.class, resultId);
        return checkResult;
    }

    /*更新CheckResult信息*/
    public void UpdateCheckResult(CheckResult checkResult) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(checkResult);
    }

    /*删除CheckResult信息*/
    public void DeleteCheckResult (int resultId) throws Exception {
        Session s = factory.getCurrentSession();
        Object checkResult = s.load(CheckResult.class, resultId);
        s.delete(checkResult);
    }

}
