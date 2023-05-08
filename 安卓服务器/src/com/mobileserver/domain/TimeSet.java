package com.mobileserver.domain;

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
    private java.sql.Timestamp startDate;
    public java.sql.Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(java.sql.Timestamp startDate) {
        this.startDate = startDate;
    }

    /*结束日期*/
    private java.sql.Timestamp endDate;
    public java.sql.Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(java.sql.Timestamp endDate) {
        this.endDate = endDate;
    }

}