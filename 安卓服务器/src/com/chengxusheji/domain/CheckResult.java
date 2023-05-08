package com.chengxusheji.domain;

import java.sql.Timestamp;
public class CheckResult {
    /*记录编号*/
    private int resultId;
    public int getResultId() {
        return resultId;
    }
    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    /*评价的学生*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*被评价老师*/
    private Teacher teacherObj;
    public Teacher getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*被评价的指标*/
    private ItemInfo itemObj;
    public ItemInfo getItemObj() {
        return itemObj;
    }
    public void setItemObj(ItemInfo itemObj) {
        this.itemObj = itemObj;
    }

    /*评价结果*/
    private ResultItem resultObj;
    public ResultItem getResultObj() {
        return resultObj;
    }
    public void setResultObj(ResultItem resultObj) {
        this.resultObj = resultObj;
    }

}