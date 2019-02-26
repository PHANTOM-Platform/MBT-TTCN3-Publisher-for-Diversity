package com.eglobalmark.genericPublisher.abstractTestStructure.abstractType;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructureElement;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractTypeList extends AbstractType implements AbstractTestStructureElement {

	@Override
	public void accept(PublisherVisitor v) {
		// TODO Auto-generated method stub
		v.visit(this);
	}
}
