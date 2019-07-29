package com.mobileserver.domain;

public class ItemInfo {
    /*记录编号*/
    private int itemId;
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /*指标标题*/
    private String itemTitle;
    public String getItemTitle() {
        return itemTitle;
    }
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    /*指标描述*/
    private String itemDesc;
    public String getItemDesc() {
        return itemDesc;
    }
    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

}