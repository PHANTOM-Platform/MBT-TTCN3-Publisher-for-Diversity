package com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractType;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeBoolean;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractStructureBoolean extends AbstractStructure {

	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	@Override
	public AbstractType GetType() {
		// TODO Auto-generated method stub
		return new AbstractTypeBoolean();
	}
}
