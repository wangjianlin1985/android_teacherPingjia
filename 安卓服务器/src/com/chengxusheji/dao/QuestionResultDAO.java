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
import com.chengxusheji.domain.QuestionResult;

@Service @Transactional
public class QuestionResultDAO {

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
    public void AddQuestionResult(QuestionResult questionResult) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(questionResult);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<QuestionResult> QueryQuestionResultInfo(Student studentObj,Teacher teacherObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From QuestionResult questionResult where 1=1";
    	if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and questionResult.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
    	if(null != teacherObj && !teacherObj.getTeacherNumber().equals("")) hql += " and questionResult.teacherObj.teacherNumber='" + teacherObj.getTeacherNumber() + "'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List questionResultList = q.list();
    	return (ArrayList<QuestionResult>) questionResultList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<QuestionResult> QueryQuestionResultInfo(Student studentObj,Teacher teacherObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From QuestionResult questionResult where 1=1";
    	if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and questionResult.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
    	if(null != teacherObj && !teacherObj.getTeacherNumber().equals("")) hql += " and questionResult.teacherObj.teacherNumber='" + teacherObj.getTeacherNumber() + "'";
    	Query q = s.createQuery(hql);
    	List questionResultList = q.list();
    	return (ArrayList<QuestionResult>) questionResultList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<QuestionResult> QueryAllQuestionResultInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From QuestionResult";
        Query q = s.createQuery(hql);
        List questionResultList = q.list();
        return (ArrayList<QuestionResult>) questionResultList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Student studentObj,Teacher teacherObj) {
        Session s = factory.getCurrentSession();
        String hql = "From QuestionResult questionResult where 1=1";
        if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and questionResult.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
        if(null != teacherObj && !teacherObj.getTeacherNumber().equals("")) hql += " and questionResult.teacherObj.teacherNumber='" + teacherObj.getTeacherNumber() + "'";
        Query q = s.createQuery(hql);
        List questionResultList = q.list();
        recordNumber = questionResultList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public QuestionResult GetQuestionResultByResultId(int resultId) {
        Session s = factory.getCurrentSession();
        QuestionResult questionResult = (QuestionResult)s.get(QuestionResult.class, resultId);
        return questionResult;
    }

    /*更新QuestionResult信息*/
    public void UpdateQuestionResult(QuestionResult questionResult) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(questionResult);
    }

    /*删除QuestionResult信息*/
    public void DeleteQuestionResult (int resultId) throws Exception {
        Session s = factory.getCurrentSession();
        Object questionResult = s.load(QuestionResult.class, resultId);
        s.delete(questionResult);
    }

}
