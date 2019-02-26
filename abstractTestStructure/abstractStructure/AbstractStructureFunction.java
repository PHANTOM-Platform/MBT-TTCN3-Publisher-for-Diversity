package com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure;

import java.util.ArrayList;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractVariable;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractType;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractStructureFunction extends AbstractStructure {

	private String strFunctionName;
	private AbstractStructure vReturnType;
	private ArrayList<AbstractVariable> vParameters;
	
	public AbstractStructureFunction() {
		vReturnType = null;
		vParameters = new ArrayList<AbstractVariable>();
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public AbstractStructure GetReturnType() {
		return(vReturnType);
	}
	
	public void SetReturnType(AbstractStructure vReturnType) {
		this.vReturnType = vReturnType;
	}

	public int GetNumberOfParameters() {
		return(vParameters.size());
	}
	
	public AbstractVariable GetParameter(int index) {

		if (index >= vParameters.size())
		{
			return(null);
		}
		
		return (vParameters.get(index));
	}
	
	public ArrayList<AbstractVariable> GetParameters() {
		return (vParameters);
	}

	public void AddParameter(AbstractVariable vParameter) {
		vParameters.add(vParameter);
	}
	
	public void AddParameters(ArrayList<AbstractVariable> vParameters) {
		vParameters.addAll(vParameters);
	}

	public String GetName() {
		return(strFunctionName);
	}
	
	public void SetName(String strFunctionName) {
		this.strFunctionName = strFunctionName;
	}

	@Override
	public AbstractType GetType() {
		// TODO Auto-generated method stub
		return null;
	}

}
