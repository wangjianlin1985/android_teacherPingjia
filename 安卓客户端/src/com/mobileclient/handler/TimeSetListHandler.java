package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.TimeSet;
public class TimeSetListHandler extends DefaultHandler {
	private List<TimeSet> timeSetList = null;
	private TimeSet timeSet;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (timeSet != null) { 
            String valueString = new String(ch, start, length); 
            if ("timeId".equals(tempString)) 
            	timeSet.setTimeId(new Integer(valueString).intValue());
            else if ("startDate".equals(tempString)) 
            	timeSet.setStartDate(Timestamp.valueOf(valueString));
            else if ("endDate".equals(tempString)) 
            	timeSet.setEndDate(Timestamp.valueOf(valueString));
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("TimeSet".equals(localName)&&timeSet!=null){
			timeSetList.add(timeSet);
			timeSet = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		timeSetList = new ArrayList<TimeSet>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("TimeSet".equals(localName)) {
            timeSet = new TimeSet(); 
        }
        tempString = localName; 
	}

	public List<TimeSet> getTimeSetList() {
		return this.timeSetList;
	}
}
