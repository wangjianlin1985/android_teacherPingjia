package com.mobileserver.domain;

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
    private String studentObj;
    public String getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(String studentObj) {
        this.studentObj = studentObj;
    }

    /*��������ʦ*/
    private String teacherObj;
    public String getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(String teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*�����۵�ָ��*/
    private int itemObj;
    public int getItemObj() {
        return itemObj;
    }
    public void setItemObj(int itemObj) {
        this.itemObj = itemObj;
    }

    /*���۽��*/
    private int resultObj;
    public int getResultObj() {
        return resultObj;
    }
    public void setResultObj(int resultObj) {
        this.resultObj = resultObj;
    }

}