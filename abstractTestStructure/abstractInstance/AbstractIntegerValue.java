package com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance;

import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractIntegerValue extends AbstractExpression {

	private int iValue;
	
	public AbstractIntegerValue(int iValue) {
		this.iValue = iValue;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public int GetValue() {
		return iValue;
	}
}
