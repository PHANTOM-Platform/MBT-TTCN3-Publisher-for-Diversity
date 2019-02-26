package com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance;

import java.util.ArrayList;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractVariable;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractFunctionCall extends AbstractExpression {

	AbstractVariable vVariable;
	String vFunction;
	ArrayList<AbstractExpression> vExpressions;
	
	public AbstractFunctionCall(AbstractVariable vVariable, String vFunction, ArrayList<AbstractExpression> vExpressions) {
		this.vVariable = vVariable;
		this.vFunction = vFunction;
		this.vExpressions = vExpressions;
	}

	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public boolean OnVariable() {
		return(null != vVariable);
	}
	
	public String GetVariable() {
		return(vVariable.GetName());
	}
	
	public String GetFunctionName() {
		return vFunction;
	}
	
	public int GetNumberOfExpressions() {
		if(null == vExpressions)
		{
			return(0);
		}
		return(vExpressions.size());
	}
	
	public AbstractExpression GetExpression(int index) {

		if (index >= vExpressions.size())
		{
			return(null);
		}
		
		return (vExpressions.get(index));
	}
	
	public ArrayList<AbstractExpression> GetExpressions() {
		return (vExpressions);
	}
}
