package com.mobileserver.domain;

public class TimeSet {
    /*��¼���*/
    private int timeId;
    public int getTimeId() {
        return timeId;
    }
    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    /*��ʼ����*/
    private java.sql.Timestamp startDate;
    public java.sql.Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(java.sql.Timestamp startDate) {
        this.startDate = startDate;
    }

    /*��������*/
    private java.sql.Timestamp endDate;
    public java.sql.Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(java.sql.Timestamp endDate) {
        this.endDate = endDate;
    }

}