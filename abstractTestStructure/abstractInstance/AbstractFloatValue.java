package com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance;

import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractFloatValue extends AbstractExpression {

	private float fValue;
	
	public AbstractFloatValue(float fValue) {
		this.fValue = fValue;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public float GetValue() {
		return fValue;
	}
}
