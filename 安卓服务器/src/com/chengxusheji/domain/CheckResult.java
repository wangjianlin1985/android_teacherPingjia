package com.chengxusheji.domain;

import java.sql.Timestamp;
public class CheckResult {
    /*��¼���*/
    private int resultId;
    public int getResultId() {
        return resultId;
    }
    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    /*���۵�ѧ��*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*��������ʦ*/
    private Teacher teacherObj;
    public Teacher getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*�����۵�ָ��*/
    private ItemInfo itemObj;
    public ItemInfo getItemObj() {
        return itemObj;
    }
    public void setItemObj(ItemInfo itemObj) {
        this.itemObj = itemObj;
    }

    /*���۽��*/
    private ResultItem resultObj;
    public ResultItem getResultObj() {
        return resultObj;
    }
    public void setResultObj(ResultItem resultObj) {
        this.resultObj = resultObj;
    }

}