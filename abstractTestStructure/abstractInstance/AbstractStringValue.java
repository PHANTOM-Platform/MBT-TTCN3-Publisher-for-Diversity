package com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance;

import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractStringValue extends AbstractExpression {

	private String strValue;
	
	public AbstractStringValue(String strValue) {
		this.strValue = strValue;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public String GetValue() {
		return strValue;
	}
}
