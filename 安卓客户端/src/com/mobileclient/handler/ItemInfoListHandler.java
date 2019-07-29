package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.ItemInfo;
public class ItemInfoListHandler extends DefaultHandler {
	private List<ItemInfo> itemInfoList = null;
	private ItemInfo itemInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (itemInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("itemId".equals(tempString)) 
            	itemInfo.setItemId(new Integer(valueString).intValue());
            else if ("itemTitle".equals(tempString)) 
            	itemInfo.setItemTitle(valueString); 
            else if ("itemDesc".equals(tempString)) 
            	itemInfo.setItemDesc(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("ItemInfo".equals(localName)&&itemInfo!=null){
			itemInfoList.add(itemInfo);
			itemInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		itemInfoList = new ArrayList<ItemInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("ItemInfo".equals(localName)) {
            itemInfo = new ItemInfo(); 
        }
        tempString = localName; 
	}

	public List<ItemInfo> getItemInfoList() {
		return this.itemInfoList;
	}
}
