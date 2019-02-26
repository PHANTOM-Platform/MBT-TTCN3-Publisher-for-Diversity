package com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance;

import java.util.ArrayList;

import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractArrayValue extends AbstractExpression {

	private ArrayList<AbstractExpression> aExprs;
	
	public AbstractArrayValue(ArrayList<AbstractExpression> aExprs) {
		this.aExprs = aExprs;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public int GetNumberOfValues() {
		return aExprs.size();
	}

	public AbstractExpression GetValue(int index) {

		if (index >= aExprs.size())
		{
			return(null);
		}
		
		return (aExprs.get(index));
	}
	public ArrayList<AbstractExpression> GetValues() {
		return aExprs;
	}
}
