package com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureEnum;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractEnumValue extends AbstractExpression {

	private String fValue;
	private AbstractStructureEnum eEnum;
	
	public AbstractEnumValue(AbstractStructureEnum eEnum, String fValue) {
		this.eEnum = eEnum;
		this.fValue = fValue;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public String GetValue() {
		return fValue;
	}
	
	public AbstractStructureEnum GetEnumType() {
		return eEnum;
	}
}
