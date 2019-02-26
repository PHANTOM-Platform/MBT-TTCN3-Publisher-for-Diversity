package com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructureElement;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructure;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractVariable implements AbstractTestStructureElement {

	private AbstractStructure vType;
	private String vName;
	
	public AbstractVariable() {
		vType = null;
		vName = null;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}
	
	public AbstractStructure GetAbstractStructure() {
		return(vType);
	}

	public void SetAbstractStructure(AbstractStructure vType) {
		this.vType = vType;
	}
	
	public String GetName() {
		return(vName);
	}

	public void SetName(String vName) {
		this.vName = vName;
	}
}
