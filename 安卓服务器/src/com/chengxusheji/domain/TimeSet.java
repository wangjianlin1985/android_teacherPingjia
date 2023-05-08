package com.chengxusheji.domain;

import java.sql.Timestamp;
public class TimeSet {
    /*记录编号*/
    private int timeId;
    public int getTimeId() {
        return timeId;
    }
    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    /*开始日期*/
    private Timestamp startDate;
    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /*结束日期*/
    private Timestamp endDate;
    public Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

}