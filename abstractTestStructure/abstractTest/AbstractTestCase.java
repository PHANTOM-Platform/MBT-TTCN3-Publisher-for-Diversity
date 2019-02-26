package com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest;

import java.util.ArrayList;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructureElement;
import com.eglobalmark.genericPublisher.abstractTestStructure.requirements.AbstractRequirement;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractTestCase implements AbstractTestStructureElement{

	private String strName;
	private AbstractTestSuite vTestSuite;
	private ArrayList<AbstractTestStep> vTestSteps;
	private ArrayList<AbstractRequirement> vRequirements;
	
	public AbstractTestCase() {
		strName = null;
		vTestSuite = null;
		vTestSteps = new ArrayList<AbstractTestStep>();
		vRequirements = new ArrayList<AbstractRequirement>();
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

	public void SetTestSuite(AbstractTestSuite vSuite) {
		vTestSuite = vSuite;
	}

	public AbstractTestSuite GetTestSuite() {
		return vTestSuite;
	}
	
	public void AddTestStep(AbstractTestStep vTestStep) {
		vTestStep.SetTestCase(this);
		vTestSteps.add(vTestStep);
	}

	public int GetNumberOfTestSteps() {
		return(vTestSteps.size());
	}
	
	public AbstractTestStep GetTestStep(int index) {
		
		if (index >= vTestSteps.size())
		{
			return(null);
		}
		
		return (vTestSteps.get(index));
	}
	
	public ArrayList<AbstractTestStep> GetTestSteps() {
		return (vTestSteps);
	}

	public void CoverRequirement(AbstractRequirement vRequirement) {
		vRequirements.add(vRequirement);
	}

	public int GetNumberOfRequirements() {
		return(vRequirements.size());
	}
	
	public AbstractRequirement GetCoveredRequirement(int index) {
		
		if (index >= vRequirements.size())
		{
			return(null);
		}
		
		return(vRequirements.get(index));
	}
	
	public ArrayList<AbstractRequirement> GetCoveredRequirements() {
		return(vRequirements);
	}
}
