package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.QuestionResult;
public class QuestionResultListHandler extends DefaultHandler {
	private List<QuestionResult> questionResultList = null;
	private QuestionResult questionResult;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (questionResult != null) { 
            String valueString = new String(ch, start, length); 
            if ("resultId".equals(tempString)) 
            	questionResult.setResultId(new Integer(valueString).intValue());
            else if ("studentObj".equals(tempString)) 
            	questionResult.setStudentObj(valueString); 
            else if ("teacherObj".equals(tempString)) 
            	questionResult.setTeacherObj(valueString); 
            else if ("answer".equals(tempString)) 
            	questionResult.setAnswer(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("QuestionResult".equals(localName)&&questionResult!=null){
			questionResultList.add(questionResult);
			questionResult = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		questionResultList = new ArrayList<QuestionResult>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("QuestionResult".equals(localName)) {
            questionResult = new QuestionResult(); 
        }
        tempString = localName; 
	}

	public List<QuestionResult> getQuestionResultList() {
		return this.questionResultList;
	}
}
