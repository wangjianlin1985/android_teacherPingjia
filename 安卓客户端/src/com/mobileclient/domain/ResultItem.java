package com.mobileclient.domain;

import java.io.Serializable;

public class ResultItem implements Serializable {
    /*记录编号*/
    private int resultItemId;
    public int getResultItemId() {
        return resultItemId;
    }
    public void setResultItemId(int resultItemId) {
        this.resultItemId = resultItemId;
    }

    /*结果描述*/
    private String resultItemText;
    public String getResultItemText() {
        return resultItemText;
    }
    public void setResultItemText(String resultItemText) {
        this.resultItemText = resultItemText;
    }

}