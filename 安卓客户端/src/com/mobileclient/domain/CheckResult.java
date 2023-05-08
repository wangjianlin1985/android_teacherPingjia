package com.mobileclient.domain;

import java.io.Serializable;

public class CheckResult implements Serializable {
    /*记录编号*/
    private int resultId;
    public int getResultId() {
        return resultId;
    }
    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    /*评价的学生*/
    private String studentObj;
    public String getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(String studentObj) {
        this.studentObj = studentObj;
    }

    /*被评价老师*/
    private String teacherObj;
    public String getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(String teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*被评价的指标*/
    private int itemObj;
    public int getItemObj() {
        return itemObj;
    }
    public void setItemObj(int itemObj) {
        this.itemObj = itemObj;
    }

    /*评价结果*/
    private int resultObj;
    public int getResultObj() {
        return resultObj;
    }
    public void setResultObj(int resultObj) {
        this.resultObj = resultObj;
    }

}