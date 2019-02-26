package com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractType;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractStructureQueue extends AbstractStructure {

	private AbstractStructure vType;
	
	AbstractStructureQueue() {
		super();
		vType = null;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}
	
	public AbstractStructure GetStructure() {
		return(vType);
	}
	
	public void SetType(AbstractStructure vType) {
		this.vType = vType;
	}

	@Override
	public AbstractType GetType() {
		// TODO Auto-generated method stub
		return null;
	}
}
