package com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest;

import java.util.ArrayList;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructureElement;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractTestSuite implements AbstractTestStructureElement {

	private String strName;
	private ArrayList<AbstractTestCase> vTestCases;
	
	public AbstractTestSuite() {
		strName = null;
		vTestCases = new ArrayList<AbstractTestCase>();
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public void SetName(String vName) {
		strName = vName;
	}

	public String GetName() {
		return strName;
	}

	public void AddTestCase(AbstractTestCase vTestCase) {
		vTestCase.SetTestSuite(this);
		vTestCases.add(vTestCase);
	}

	public int GetNumberOfTestCases() {
		return(vTestCases.size());
	}
	
	public AbstractTestCase GetTestCase(int index) {

		if (index >= vTestCases.size())
		{
			return(null);
		}
		
		return (vTestCases.get(index));
	}
	
	public ArrayList<AbstractTestCase> GetTestCases() {
		return (vTestCases);
	}

}
