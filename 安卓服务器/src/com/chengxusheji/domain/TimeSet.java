package com.chengxusheji.domain;

import java.sql.Timestamp;
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
    private Timestamp startDate;
    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /*��������*/
    private Timestamp endDate;
    public Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

}