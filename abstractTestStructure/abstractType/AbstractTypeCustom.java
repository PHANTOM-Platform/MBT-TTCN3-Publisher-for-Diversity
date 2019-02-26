package com.eglobalmark.genericPublisher.abstractTestStructure.abstractType;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructureElement;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractTypeCustom extends AbstractType implements AbstractTestStructureElement {

	private String strName;
	
	@Override
	public void accept(PublisherVisitor v) {
		// TODO Auto-generated method stub
		v.visit(this);
	}
	
	public void SetCustomName(String strName) {
		this.strName = strName;
	}

	public String GetCustomName() {
		return(strName);
	}
}
