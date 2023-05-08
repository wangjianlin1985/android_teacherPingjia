package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.ResultItem;
public class ResultItemListHandler extends DefaultHandler {
	private List<ResultItem> resultItemList = null;
	private ResultItem resultItem;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (resultItem != null) { 
            String valueString = new String(ch, start, length); 
            if ("resultItemId".equals(tempString)) 
            	resultItem.setResultItemId(new Integer(valueString).intValue());
            else if ("resultItemText".equals(tempString)) 
            	resultItem.setResultItemText(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("ResultItem".equals(localName)&&resultItem!=null){
			resultItemList.add(resultItem);
			resultItem = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		resultItemList = new ArrayList<ResultItem>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("ResultItem".equals(localName)) {
            resultItem = new ResultItem(); 
        }
        tempString = localName; 
	}

	public List<ResultItem> getResultItemList() {
		return this.resultItemList;
	}
}
