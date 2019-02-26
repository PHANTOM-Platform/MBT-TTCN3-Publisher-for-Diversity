package com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest;

import java.util.ArrayList;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructureElement;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractTestStep implements AbstractTestStructureElement{

	private String strName;
	private AbstractTestCase vTestCase;
	private ArrayList<AbstractTestStep> vTestSteps;
	private ArrayList<AbstractInstruction> vInstructions;
	
	public AbstractTestStep() {
		vTestCase = null;
		vTestSteps = new ArrayList<AbstractTestStep>();
		vInstructions = new ArrayList<AbstractInstruction>();
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

	public void SetTestCase(AbstractTestCase vTestCase) {
		this.vTestCase = vTestCase;
		for(AbstractTestStep ats : vTestSteps) {
			ats.SetTestCase(vTestCase);
		}
	}
	
	public AbstractTestCase GetTestCase() {
		return vTestCase;
	}
	
	public void AddTestStep(AbstractTestStep vTestStep) {
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

	public int GetNumberOfInstructions() {
		return(vInstructions.size());
	}
	
	public AbstractInstruction GetInstruction(int index) {

		if (index >= vInstructions.size())
		{
			return(null);
		}
		
		return (vInstructions.get(index));
	}
	
	public ArrayList<AbstractInstruction> GetInstructions() {
		return (vInstructions);
	}

	public void AddInstruction(AbstractInstruction vInstr) {
		vInstructions.add(vInstr);
	}

}
