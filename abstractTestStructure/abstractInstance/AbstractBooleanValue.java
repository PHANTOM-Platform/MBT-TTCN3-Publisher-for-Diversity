package com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance;

import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractBooleanValue extends AbstractExpression {

	private boolean bValue;
	
	public AbstractBooleanValue(boolean bValue) {
		this.bValue = bValue;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public boolean GetValue() {
		return bValue;
	}
}
