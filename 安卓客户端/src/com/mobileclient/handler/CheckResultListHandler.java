package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.CheckResult;
public class CheckResultListHandler extends DefaultHandler {
	private List<CheckResult> checkResultList = null;
	private CheckResult checkResult;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (checkResult != null) { 
            String valueString = new String(ch, start, length); 
            if ("resultId".equals(tempString)) 
            	checkResult.setResultId(new Integer(valueString).intValue());
            else if ("studentObj".equals(tempString)) 
            	checkResult.setStudentObj(valueString); 
            else if ("teacherObj".equals(tempString)) 
            	checkResult.setTeacherObj(valueString); 
            else if ("itemObj".equals(tempString)) 
            	checkResult.setItemObj(new Integer(valueString).intValue());
            else if ("resultObj".equals(tempString)) 
            	checkResult.setResultObj(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("CheckResult".equals(localName)&&checkResult!=null){
			checkResultList.add(checkResult);
			checkResult = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		checkResultList = new ArrayList<CheckResult>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("CheckResult".equals(localName)) {
            checkResult = new CheckResult(); 
        }
        tempString = localName; 
	}

	public List<CheckResult> getCheckResultList() {
		return this.checkResultList;
	}
}
