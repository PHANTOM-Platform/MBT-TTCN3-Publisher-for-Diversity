package com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractType;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeString;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractStructureString extends AbstractStructure {

	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	@Override
	public AbstractType GetType() {
		// TODO Auto-generated method stub
		return new AbstractTypeString();
	}
}
